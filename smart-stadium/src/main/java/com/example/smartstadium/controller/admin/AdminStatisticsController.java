package com.example.smartstadium.controller.admin;

import com.example.smartstadium.repository.BookingRepository;
import com.example.smartstadium.repository.StadiumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/statistics")
@CrossOrigin(origins = "*") // Cho phép Frontend gọi API lấy dữ liệu thống kê
public class AdminStatisticsController {

    @Autowired
    private StadiumRepository stadiumRepository;

    @Autowired
    private BookingRepository bookingRepository;

    // API cung cấp số liệu tổng quan cho trang Dashboard của Admin
    @GetMapping
    public ResponseEntity<Map<String, Object>> getDashboardStatistics() {
        Map<String, Object> stats = new HashMap<>();

        // Đếm tổng số lượng sân bóng và tổng số lượt đặt sân
        long totalStadiums = stadiumRepository.count();
        long totalBookings = bookingRepository.count();

        // Bạn có thể viết thêm logic tính tổng doanh thu từ cơ sở dữ liệu ở đây
        double totalRevenue = 15000000.0; // Dữ liệu ví dụ

        stats.put("totalStadiums", totalStadiums);
        stats.put("totalBookings", totalBookings);
        stats.put("totalRevenue", totalRevenue);

        return ResponseEntity.ok(stats);
    }
}