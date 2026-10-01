package vn.edu.crs.smartstadium.service;

import vn.edu.crs.smartstadium.entity.Field;
import vn.edu.crs.smartstadium.entity.Stadium;

import java.util.List;
import java.util.Optional;

public interface StadiumService {
    List<Stadium> getAllStadiums();
    Optional<Stadium> getStadiumById(Long id);
    Stadium saveStadium(Stadium stadium);
    void deleteStadium(Long id);

    // Sử dụng tên đầy đủ gói để tránh xung đột
    List<Field> findAvailableFields(java.time.LocalDate date, java.time.LocalTime startTime, java.time.LocalTime endTime);
}