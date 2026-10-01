package vn.edu.crs.smartstadium.service;

import vn.edu.crs.smartstadium.entity.Booking;
import java.time.LocalDate;
import java.time.LocalTime;

public interface BookingService {
    Booking createBooking(Long userId, Long fieldId, LocalDate date, LocalTime start, LocalTime end);
}