package vn.edu.crs.courseservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.crs.courseservice.entity.Course;

public interface CourseRepository extends JpaRepository<Course, Long> {

    // Kiểm tra trùng tên môn học (không phân biệt hoa/thường)
    boolean existsByTenMonHocIgnoreCase(String tenMonHoc);

    // Tìm kiếm môn học theo từ khóa chứa trong tên (LIKE %keyword%) có phân trang
    Page<Course> findByTenMonHocContainingIgnoreCase(String keyword, Pageable pageable);
}