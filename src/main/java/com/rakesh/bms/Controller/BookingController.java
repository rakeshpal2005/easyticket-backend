package com.rakesh.bms.Controller;


import com.rakesh.bms.Dto.BookingDto;
import com.rakesh.bms.Dto.BookingRequestDto;
import com.rakesh.bms.Service.BookingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@CrossOrigin(origins = "http://localhost:5173")
public class BookingController {

    @Autowired
    BookingService bookingService;

    @PostMapping("/create")
    public ResponseEntity<BookingDto> createBooking(@RequestBody BookingRequestDto bookingRequestDto){
        return new ResponseEntity<>(bookingService.createBooking(bookingRequestDto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingDto>getBookingById(@PathVariable Long id){
        return ResponseEntity.ok(bookingService.getBookingById(id));
    }

    @GetMapping("/number/{bookingNumber}")
    public ResponseEntity<BookingDto> getBookingByNumber(@PathVariable String bookingNumber){
        return ResponseEntity.ok(bookingService.getBookingByNumber(bookingNumber));
    }

    @GetMapping("/my")
    public ResponseEntity<List<BookingDto>> getMyBookings() {
        return ResponseEntity.ok(bookingService.getMyBookings());
    }

    @PutMapping("/cancel/{id}")
    public ResponseEntity<BookingDto> cancelBooking(@PathVariable Long id){
        return ResponseEntity.ok(bookingService.cancelBooking(id));
    }
}
