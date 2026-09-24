package vn.edu.crs.registrationservice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RegistrationRequestDTO {

    @NotNull(message = "2311063284")
    private Long studentId;

    @NotNull(message = "1")
    private Long courseId;
}