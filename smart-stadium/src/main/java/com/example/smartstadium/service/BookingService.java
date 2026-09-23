package com.example.smartstadium.service;

import com.example.smartstadium.entity.Booking;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface BookingService {
    Booking createBooking(Long userId, Long fieldId, LocalDate date, LocalTime start, LocalTime end);
    List<Booking> getAllBookings();
    Booking getBookingById(Long id);
    void cancelBooking(Long id);

    // Phương thức bổ sung: Lấy danh sách lịch đã đặt theo sân và ngày cụ thể
    List<Booking> getBookingsByFieldAndDate(Long fieldId, LocalDate date);
}