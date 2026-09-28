package vn.edu.crs.smartstadium.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.crs.smartstadium.entity.Field;
import vn.edu.crs.smartstadium.entity.Stadium;
import vn.edu.crs.smartstadium.repository.FieldRepository;
import vn.edu.crs.smartstadium.service.StadiumService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/api/stadiums")
public class StadiumController {

    @Autowired
    private StadiumService stadiumService;

    @Autowired
    private FieldRepository fieldRepository;

    // API lấy danh sách tất cả cụm sân
    @GetMapping
    public ResponseEntity<List<Stadium>> getAllStadiums() {
        List<Stadium> stadiums = stadiumService.getAllStadiums();
        return ResponseEntity.ok(stadiums);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStadium(@PathVariable Long id) {
        stadiumService.deleteStadium(id);
        return ResponseEntity.ok().build();
    }

    // API tìm kiếm sân còn trống theo Ngày, Từ giờ, Đến giờ
    @GetMapping("/search-available")
    public ResponseEntity<List<Field>> searchAvailableFields(
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam("startTime") @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime startTime,
            @RequestParam("endTime") @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime endTime) {

        List<Field> availableFields = stadiumService.findAvailableFields(date, startTime, endTime);
        return ResponseEntity.ok(availableFields);
    }

    // API lấy danh sách sân con thuộc cụm sân thông qua FieldRepository
    @GetMapping("/{id}/fields")
    public ResponseEntity<List<Field>> getFieldsByStadiumId(@PathVariable Long id) {
        // Kiểm tra xem cụm sân có tồn tại không trước khi lấy danh sách sân con
        Stadium stadium = stadiumService.getStadiumById(id).orElse(null);
        if (stadium == null) {
            return ResponseEntity.notFound().build();
        }

        List<Field> fields = fieldRepository.findByStadiumId(id);
        return ResponseEntity.ok(fields);
    }
}