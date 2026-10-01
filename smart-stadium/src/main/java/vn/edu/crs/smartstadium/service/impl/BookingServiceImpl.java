package vn.edu.crs.smartstadium.service.impl;

import vn.edu.crs.smartstadium.entity.Booking;
import vn.edu.crs.smartstadium.entity.Field;
import vn.edu.crs.smartstadium.entity.User;
import vn.edu.crs.smartstadium.repository.BookingRepository;
import vn.edu.crs.smartstadium.repository.FieldRepository;
import vn.edu.crs.smartstadium.repository.UserRepository;
import vn.edu.crs.smartstadium.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Duration;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final FieldRepository fieldRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public Booking createBooking(Long userId, Long fieldId, LocalDate date, LocalTime start, LocalTime end) {
        // 1. Kiểm tra trùng lịch
        List<Booking> conflicts = bookingRepository.findOverlappingBookings(fieldId, date, start, end);
        if (!conflicts.isEmpty()) {
            throw new RuntimeException("Sân đã có người đặt trong khoảng thời gian này!");
        }

        // 2. Lấy thông tin User và Field từ DB
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng"));
        Field field = fieldRepository.findById(fieldId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sân"));

        // 3. Tính toán tổng tiền dựa theo số giờ đặt và giá tiền của sân
        long hours = Duration.between(start, end).toHours();
        if (hours <= 0) {
            throw new RuntimeException("Giờ kết thúc phải lớn hơn giờ bắt đầu!");
        }
        double totalPrice = hours * field.getPricePerHour();

        // 4. Lưu đơn đặt sân
        Booking booking = Booking.builder()
                .user(user)
                .field(field)
                .bookingDate(date)
                .startTime(start)
                .endTime(end)
                .totalPrice(totalPrice)
                .status(Booking.BookingStatus.CONFIRMED)
                .build();

        return bookingRepository.save(booking);
    }
}