package com.example.smartstadium.controller;

import com.example.smartstadium.entity.Booking;
import com.example.smartstadium.service.BookingService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    public ResponseEntity<?> createBooking(
            @RequestParam Long userId,
            @RequestParam Long fieldId,
            @RequestParam LocalDate date,
            @RequestParam LocalTime startTime,
            @RequestParam LocalTime endTime
    ) {

        try {

            Booking booking =
                    bookingService.createBooking(
                            userId,
                            fieldId,
                            date,
                            startTime,
                            endTime
                    );

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(booking);

        } catch (IllegalArgumentException e) {

            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }


    @GetMapping("/available-fields")
    public ResponseEntity<?> getAvailableFields(
            @RequestParam LocalDate date,
            @RequestParam LocalTime startTime,
            @RequestParam LocalTime endTime,
            @RequestParam(required = false) String sport,
            @RequestParam(required = false) String keyword
    ) {

        try {

            return ResponseEntity.ok(
                    bookingService.findAvailableFields(
                            date,
                            startTime,
                            endTime,
                            sport,
                            keyword
                    )
            );

        } catch (IllegalArgumentException e) {

            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }


    @GetMapping("/field/{fieldId}")
    public List<Booking> getBookingsByFieldAndDate(
            @PathVariable Long fieldId,
            @RequestParam LocalDate date
    ) {

        return bookingService
                .getBookingsByFieldAndDate(
                        fieldId,
                        date
                );
    }


    @GetMapping("/user/{userId}")
    public List<Booking> getBookingsByUser(
            @PathVariable Long userId
    ) {

        return bookingService
                .getBookingsByUser(userId);
    }


    @PutMapping("/{id}/cancel")
    public ResponseEntity<?> cancelBooking(
            @PathVariable Long id
    ) {

        try {

            return ResponseEntity.ok(
                    bookingService.cancelBooking(id)
            );

        } catch (IllegalArgumentException e) {

            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }
}