package vn.edu.crs.smartstadium.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    private String email;
    private String phone;

    @Enumerated(EnumType.STRING)
    private Role role; // ADMIN, OWNER, CUSTOMER

    public enum Role {
        ADMIN, OWNER, CUSTOMER
    }
}