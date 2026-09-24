package vn.edu.crs.registrationservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.edu.crs.registrationservice.client.CourseClient;
import vn.edu.crs.registrationservice.dto.RegistrationRequestDTO;
import vn.edu.crs.registrationservice.entity.Registration;
import vn.edu.crs.registrationservice.repository.RegistrationRepository;

@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final RegistrationRepository registrationRepository;
    private final CourseClient courseClient;

    public Registration register(RegistrationRequestDTO dto) {
        // 1. Giữ chỗ bên course-service
        courseClient.reserveSeat(dto.getCourseId());

        // 2. Lưu đăng ký
        Registration registration = new Registration();
        registration.setStudentId(dto.getStudentId());
        registration.setCourseId(dto.getCourseId());
        registration.setTrangThai("DA_DANG_KY");

        return registrationRepository.save(registration);
    }

    // BO SUNG: Phương thức hủy đăng ký môn học
    public void cancel(Long id) {
        Registration registration = registrationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Khong tim thay ban ghi dang ky"));

        // 1. Hoàn trả lại chỗ bên course-service
        courseClient.releaseSeat(registration.getCourseId());

        // 2. Cập nhật trạng thái thành DA_HUY (hoặc xóa khỏi database)
        registration.setTrangThai("DA_HUY");
        registrationRepository.save(registration);
    }
}