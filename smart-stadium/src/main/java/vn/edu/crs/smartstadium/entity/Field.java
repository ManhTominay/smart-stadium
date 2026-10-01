package vn.edu.crs.smartstadium.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "fields")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Field {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String type; // Bóng đá, Cầu lông, Tennis...

    @Column(name = "price_per_hour", nullable = false)
    private Double pricePerHour;

    @ManyToOne
    @JoinColumn(name = "stadium_id")
    private Stadium stadium;
}