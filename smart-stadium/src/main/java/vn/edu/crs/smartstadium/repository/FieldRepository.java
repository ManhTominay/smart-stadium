package vn.edu.crs.smartstadium.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.edu.crs.smartstadium.entity.Field;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface FieldRepository extends JpaRepository<Field, Long> {

    @Query("SELECT f FROM Field f WHERE f.id NOT IN " +
            "(SELECT b.field.id FROM Booking b WHERE b.bookingDate = :date AND b.status != 'CANCELLED' " +
            "AND ((b.startTime < :endTime) AND (b.endTime > :startTime)))")
    List<Field> findAvailableFields(
            @Param("date") LocalDate date,
            @Param("startTime") LocalTime startTime,
            @Param("endTime") LocalTime endTime
    );
    List<Field> findByStadiumId(Long stadiumId);
}