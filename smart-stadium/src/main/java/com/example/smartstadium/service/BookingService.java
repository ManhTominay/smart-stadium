package com.example.smartstadium.service;

import com.example.smartstadium.dto.AvailableFieldResponse;
import com.example.smartstadium.entity.Booking;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface BookingService {

    // Tạo booking mới
    Booking createBooking(
            Long userId,
            Long fieldId,
            LocalDate date,
            LocalTime startTime,
            LocalTime endTime
    );


    // Lấy toàn bộ booking
    List<Booking> getAllBookings();


    // Lấy booking theo ID
    Booking getBookingById(Long id);


    // Cập nhật trạng thái booking
    Booking updateStatus(
            Long id,
            Booking.BookingStatus status
    );


    // Hủy booking
    Booking cancelBooking(Long id);


    // Lấy lịch đặt theo sân + ngày
    List<Booking> getBookingsByFieldAndDate(
            Long fieldId,
            LocalDate date
    );


    // Lấy lịch đặt của user
    List<Booking> getBookingsByUser(Long userId);


    // ==========================================
    // TÌM KIẾM NÂNG CAO
    // ==========================================

    List<AvailableFieldResponse> findAvailableFields(
            LocalDate date,
            LocalTime startTime,
            LocalTime endTime,
            String sport,
            String keyword
    );
}