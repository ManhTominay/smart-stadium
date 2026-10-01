package vn.edu.crs.smartstadium.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "payments")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "booking_id")
    private Booking booking;

    private Double amount;

    @Column(name = "payment_method")
    private String paymentMethod; // VNPAY, MOMO, CASH

    @Column(name = "transaction_status")
    private String transactionStatus; // SUCCESS, FAILED, PENDING
}