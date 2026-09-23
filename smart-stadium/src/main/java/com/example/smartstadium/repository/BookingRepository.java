package com.example.smartstadium.repository;

import com.example.smartstadium.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    // Hỗ trợ nếu Service gọi findByFieldIdAndDate
    @Query("SELECT b FROM Booking b WHERE b.field.id = :fieldId AND b.bookingDate = :date")
    List<Booking> findByFieldIdAndDate(@Param("fieldId") Long fieldId, @Param("date") LocalDate date);

    // Hỗ trợ nếu Service gọi findByFieldIdAndBookingDate
    @Query("SELECT b FROM Booking b WHERE b.field.id = :fieldId AND b.bookingDate = :date")
    List<Booking> findByFieldIdAndBookingDate(@Param("fieldId") Long fieldId, @Param("date") LocalDate date);

    // Kiểm tra xem khung giờ khách muốn đặt có bị đè lên đơn đã CONFIRMED nào trước đó không
    @Query("SELECT b FROM Booking b WHERE b.field.id = :fieldId " +
            "AND b.bookingDate = :date " +
            "AND b.status = 'CONFIRMED' " +
            "AND ((b.startTime < :endTime) AND (b.endTime > :startTime))")
    List<Booking> findOverlappingBookings(@Param("fieldId") Long fieldId,
                                          @Param("date") LocalDate date,
                                          @Param("startTime") LocalTime startTime,
                                          @Param("endTime") LocalTime endTime);
}