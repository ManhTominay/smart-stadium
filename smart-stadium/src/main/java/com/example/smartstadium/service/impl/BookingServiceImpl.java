package com.example.smartstadium.service.impl;

import com.example.smartstadium.entity.Booking;
import com.example.smartstadium.entity.Field;
import com.example.smartstadium.entity.User;
import com.example.smartstadium.repository.BookingRepository;
import com.example.smartstadium.repository.FieldRepository;
import com.example.smartstadium.repository.UserRepository;
import com.example.smartstadium.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final FieldRepository fieldRepository;

    @Override
    public Booking createBooking(Long userId, Long fieldId, LocalDate date, LocalTime start, LocalTime end) {
        // 0. Kiểm tra trùng lịch (Chống đặt đè khung giờ)
        List<Booking> existingBookings = bookingRepository.findByFieldIdAndDate(fieldId, date);
        for (Booking b : existingBookings) {
            // Công thức kiểm tra xung đột thời gian (Overlap)
            // (StartA < EndB) và (EndA > StartB)
            if (start.isBefore(b.getEndTime()) && end.isAfter(b.getStartTime())) {
                throw new RuntimeException("Khung giờ này đã có người đặt, vui lòng chọn giờ khác!");
            }
        }

        // 1. Tìm thông tin User từ CSDL
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng với ID: " + userId));

        // 2. Tìm thông tin Sân con (Field) từ CSDL
        Field field = fieldRepository.findById(fieldId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sân với ID: " + fieldId));

        // 3. Khởi tạo đối tượng Booking và gán dữ liệu
        Booking booking = new Booking();
        booking.setUser(user);
        booking.setField(field);
        booking.setBookingDate(date);
        booking.setStartTime(start);
        booking.setEndTime(end);

        // Gán trạng thái (Xử lý cho cả Enum hoặc String tùy theo Entity của bạn)
        try {
            booking.setStatus(Booking.BookingStatus.valueOf("CONFIRMED"));
        } catch (Exception e) {
            // booking.setStatus("CONFIRMED");
        }

        // Gán tổng tiền kiểu Double để tránh lỗi không được null trong CSDL
        booking.setTotalPrice(250000.0);

        // 4. Lưu xuống CSDL MySQL thông qua JpaRepository
        return bookingRepository.save(booking);
    }

    @Override
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    @Override
    public Booking getBookingById(Long id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy lịch đặt với ID: " + id));
    }

    @Override
    public void cancelBooking(Long id) {
        bookingRepository.deleteById(id);
    }

    @Override
    public List<Booking> getBookingsByFieldAndDate(Long fieldId, LocalDate date) {
        return bookingRepository.findByFieldIdAndDate(fieldId, date);
    }
}