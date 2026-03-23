package com.rakesh.bms.Service;


import com.rakesh.bms.Dto.*;
import com.rakesh.bms.Exception.ResourceNotFoundException;
import com.rakesh.bms.Exception.SeatUnavailableException;
import com.rakesh.bms.Model.*;
import com.rakesh.bms.Repository.BookingRepository;
import com.rakesh.bms.Repository.ShowRepository;
import com.rakesh.bms.Repository.ShowSeatRepository;
import com.rakesh.bms.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BookingService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ShowRepository showRepository;

    @Autowired
    private ShowSeatRepository showSeatRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Transactional
    public BookingDto createBooking(BookingRequestDto bookingRequest) {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User Not Found"));

        Show show = showRepository.findById(bookingRequest.getShowId())
                .orElseThrow(() -> new ResourceNotFoundException("Show Not Found"));

        List<ShowSeat> selectedSeats = showSeatRepository.findAllById(bookingRequest.getSeatIds());

        for (ShowSeat seat : selectedSeats) {
            if (!"AVAILABLE".equals(seat.getStatus())) {
                throw new SeatUnavailableException("Seat " + seat.getSeat().getSeatNumber() + " is not available");
            }
            seat.setStatus("Lock for Few Min");
        }

        showSeatRepository.saveAll(selectedSeats);

        Double totalamount = selectedSeats.stream().mapToDouble(ShowSeat::getPrice).sum();

        Payment payment = new Payment();
        payment.setAmount(totalamount);
        payment.setTransactionId(UUID.randomUUID().toString());
        payment.setPaymentTime(LocalDateTime.now());
        payment.setStatus("BOOKED");
        payment.setPaymentMethod(bookingRequest.getPaymentMethod());

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setShow(show);
        booking.setTime(LocalDateTime.now());
        booking.setStatus("CONFIRMED");
        booking.setTotalamount(totalamount);
        booking.setBookingNumber(UUID.randomUUID().toString());
        booking.setPayment(payment);

        Booking saveBooking = bookingRepository.save(booking);

        selectedSeats.forEach(seat -> {
            seat.setStatus("BOOKED");
            seat.setBooking(saveBooking);
        });

        showSeatRepository.saveAll(selectedSeats);
        return mapToBookingDto(saveBooking, selectedSeats);
    }

    public BookingDto getBookingById(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking Not Found"));

        List<ShowSeat> seats = showSeatRepository.findAll()
                .stream()
                .filter(seat -> seat.getBooking() != null && seat.getBooking().getId().equals(booking.getId()))
                .collect(Collectors.toList());

        return mapToBookingDto(booking, seats);
    }

    public BookingDto getBookingByNumber(String bookingNumber) {
        Booking booking = bookingRepository.findByBookingNumber(bookingNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Booking Not Found"));

        List<ShowSeat> seats = showSeatRepository.findAll()
                .stream()
                .filter(seat -> seat.getBooking() != null && seat.getBooking().getId().equals(booking.getId()))
                .collect(Collectors.toList());

        return mapToBookingDto(booking, seats);
    }

    public List<BookingDto> getMyBookings() {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();


        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User Not Found"));


        List<Booking> bookings = bookingRepository.findByUserId(user.getId());


        return bookings.stream()
                .map(booking -> {
                    List<ShowSeat> seats = showSeatRepository.findAll()
                            .stream()
                            .filter(seat -> seat.getBooking() != null &&
                                    seat.getBooking().getId().equals(booking.getId()))
                            .collect(Collectors.toList());

                    return mapToBookingDto(booking, seats);
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public BookingDto cancelBooking(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking Not found"));

        booking.setStatus("CANCELLED");

        List<ShowSeat> seats = showSeatRepository.findAll()
                .stream()
                .filter(seat -> seat.getBooking() != null && seat.getBooking().getId().equals(booking.getId()))
                .collect(Collectors.toList());

        seats.forEach(seat -> {
            seat.setStatus("AVAILABLE");
            seat.setBooking(null);
        });

        if (booking.getPayment() != null) {
            booking.getPayment().setStatus("REFUNDED");
        }

        Booking updateBooking = bookingRepository.save(booking);
        showSeatRepository.saveAll(seats);
        return mapToBookingDto(updateBooking, seats);
    }

    private BookingDto mapToBookingDto(Booking booking, List<ShowSeat> seats) {

        BookingDto bookingDto = new BookingDto();
        bookingDto.setId(booking.getId());
        bookingDto.setBookingNumber(booking.getBookingNumber());
        bookingDto.setBookingTime(booking.getTime());
        bookingDto.setStatus(booking.getStatus());
        bookingDto.setTotalAmount(booking.getTotalamount());

        UserDto userDto = new UserDto();
        userDto.setId(booking.getUser().getId());
        userDto.setName(booking.getUser().getName());
        userDto.setEmail(booking.getUser().getEmail());
        userDto.setPhoneNumber(booking.getUser().getPhoneNumber());
        bookingDto.setUser(userDto);

        ShowDto showDto = new ShowDto();
        showDto.setId(booking.getShow().getId());
        showDto.setStartTime(booking.getShow().getStartTime());
        showDto.setEndTime(booking.getShow().getEndTime());

        MovieDto movieDto = new MovieDto();
        movieDto.setId(booking.getShow().getMovie().getId());
        movieDto.setTitle(booking.getShow().getMovie().getTitle());
        movieDto.setDescription(booking.getShow().getMovie().getDescription());
        movieDto.setLanguage(booking.getShow().getMovie().getLanguage());
        movieDto.setGenre(booking.getShow().getMovie().getGenre());
        movieDto.setDurationMins(booking.getShow().getMovie().getDurationMins());

        int mins = booking.getShow().getMovie().getDurationMins();
        int hours = mins / 60;
        int remainingMins = mins % 60;
        String formatted = hours + " hr " + remainingMins + " mins";
        movieDto.setDurationformatted(formatted);

        movieDto.setReleaseDate(booking.getShow().getMovie().getReleaseDate());
        movieDto.setPosterUrl(booking.getShow().getMovie().getPosterUrl());

        showDto.setMovie(movieDto);


        ScreenDto screenDto = new ScreenDto();
        screenDto.setId(booking.getShow().getScreen().getId());
        screenDto.setName(booking.getShow().getScreen().getName());
        screenDto.setTotalSeats(booking.getShow().getScreen().getTotalSeats());

        TheaterDto theaterDto = new TheaterDto();
        theaterDto.setId(booking.getShow().getScreen().getTheater().getId());
        theaterDto.setName(booking.getShow().getScreen().getTheater().getName());
        theaterDto.setAddress(booking.getShow().getScreen().getTheater().getAddress());
        theaterDto.setCity(booking.getShow().getScreen().getTheater().getCity());
        theaterDto.setTotalScreens(booking.getShow().getScreen().getTheater().getTotalScreens());

        screenDto.setTheater(theaterDto);
        showDto.setScreen(screenDto);
        bookingDto.setShow(showDto);

        List<ShowSeatDto> seatDtos = seats.stream()
                .map(seat -> {
                    ShowSeatDto seatDto = new ShowSeatDto();
                    seatDto.setId(seat.getId());
                    seatDto.setStatus(seat.getStatus());
                    seatDto.setPrice(seat.getPrice());

                    SeatDto baseSeatDto = new SeatDto();
                    baseSeatDto.setId(seat.getSeat().getId());
                    baseSeatDto.setSeatNumber(seat.getSeat().getSeatNumber());
                    baseSeatDto.setSeatType(seat.getSeat().getSeatType());
                    baseSeatDto.setBasePrice(seat.getSeat().getBasePrice());
                    seatDto.setSeat(baseSeatDto);

                    return seatDto;
                })
                .collect(Collectors.toList());

        bookingDto.setSeats(seatDtos);

        if (booking.getPayment() != null) {
            PaymentDto paymentDto = new PaymentDto();
            paymentDto.setId(booking.getPayment().getId());
            paymentDto.setAmount(booking.getPayment().getAmount());
            paymentDto.setPaymentMethod(booking.getPayment().getPaymentMethod());
            paymentDto.setPaymentTime(booking.getPayment().getPaymentTime());
            paymentDto.setStatus(booking.getPayment().getStatus());
            paymentDto.setTransactionId(booking.getPayment().getTransactionId());
            bookingDto.setPayment(paymentDto);
        }

        return bookingDto;
    }
}
