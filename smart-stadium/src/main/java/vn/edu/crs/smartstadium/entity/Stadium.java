package vn.edu.crs.smartstadium.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "stadiums")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Stadium {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String address;
    private String description;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;
}