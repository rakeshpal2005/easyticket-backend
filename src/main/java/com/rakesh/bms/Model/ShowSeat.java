package com.rakesh.bms.Model;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="showseats")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShowSeat {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //table

    @ManyToOne
    @JoinColumn(name = "show_id",nullable = false)
    private Show show;

    @ManyToOne
    @JoinColumn(name = "seat_id",nullable = false)
    private Seat seat;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private Double price;

    @ManyToOne
    @JoinColumn(name = "booking_id")
    private Booking booking;
}
