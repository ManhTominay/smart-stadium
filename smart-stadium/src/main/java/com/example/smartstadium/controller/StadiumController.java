package com.example.smartstadium.controller;

import com.example.smartstadium.entity.Field;
import com.example.smartstadium.entity.Stadium;
import com.example.smartstadium.repository.FieldRepository;
import com.example.smartstadium.repository.StadiumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class StadiumController {

    @Autowired
    private StadiumRepository stadiumRepository;

    @Autowired
    private FieldRepository fieldRepository; // Thêm Repository của sân con

    // API công khai để lấy toàn bộ danh sách sân cho trang chủ
    @GetMapping("/api/stadiums")
    public List<Stadium> getAllStadiums() {
        return stadiumRepository.findAll();
    }

    // API lấy danh sách sân con thuộc một cụm sân theo ID (Được gọi khi bấm xem chi tiết)
    @GetMapping("/api/stadiums/{id}/fields")
    public List<Field> getFieldsByStadiumId(@PathVariable Long id) {
        return fieldRepository.findByStadiumId(id);
    }

    // Các API quản lý dành cho Admin giữ nguyên tiền tố /api/admin
    @PostMapping("/api/admin/stadiums")
    public Stadium addStadium(@RequestBody Stadium stadium) {
        return stadiumRepository.save(stadium);
    }

    @PutMapping("/api/admin/stadiums/{id}")
    public Stadium updateStadium(@PathVariable Long id, @RequestBody Stadium stadiumDetails) {
        Stadium stadium = stadiumRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sân với ID: " + id));

        stadium.setName(stadiumDetails.getName());
        stadium.setAddress(stadiumDetails.getAddress());
        stadium.setDescription(stadiumDetails.getDescription());

        return stadiumRepository.save(stadium);
    }

    @DeleteMapping("/api/admin/stadiums/{id}")
    public void deleteStadium(@PathVariable Long id) {
        stadiumRepository.deleteById(id);
    }
}