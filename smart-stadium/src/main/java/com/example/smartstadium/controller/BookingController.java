package com.example.smartstadium.controller;

import com.example.smartstadium.entity.Booking;
import com.example.smartstadium.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@CrossOrigin(origins = "*") // Cho phép gọi API từ giao diện frontend
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    // API tạo lịch đặt mới (có kiểm tra trùng giờ ở tầng Service)
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

    // API bổ sung: Lấy danh sách lịch đã đặt của một sân trong một ngày cụ thể
    // Giúp frontend kiểm tra hoặc hiển thị các khung giờ đã bị khóa
    @GetMapping("/field/{fieldId}")
    public ResponseEntity<List<Booking>> getBookingsByFieldAndDate(
            @PathVariable Long fieldId,
            @RequestParam LocalDate date) {
        List<Booking> bookings = bookingService.getBookingsByFieldAndDate(fieldId, date);
        return ResponseEntity.ok(bookings);
    }
}