package vn.edu.crs.smartstadium.repository;

import vn.edu.crs.smartstadium.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

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