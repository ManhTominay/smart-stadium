package vn.edu.crs.smartstadium.controller;

import vn.edu.crs.smartstadium.entity.Booking;
import vn.edu.crs.smartstadium.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    public ResponseEntity<?> createBooking(@RequestParam Long userId,
                                           @RequestParam Long fieldId,
                                           @RequestParam LocalDate date,
                                           @RequestParam LocalTime startTime,
                                           @RequestParam LocalTime endTime) {
        try {
            Booking booking = bookingService.createBooking(userId, fieldId, date, startTime, endTime);
            return ResponseEntity.ok(booking);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}