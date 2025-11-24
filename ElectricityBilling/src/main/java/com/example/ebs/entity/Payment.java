package com.example.ebs.entity;
import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.*;
@Entity @Table(name="payments")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Payment {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
  @ManyToOne(optional=false) @JoinColumn(name="bill_id", nullable=false) private Bill bill;
  @Column(name="payment_date", nullable=false) private LocalDateTime paymentDate;
  @Column(name="amount", nullable=false) private double amount;
  @Column(name="payment_mode", length=30) private String method;
  @Column(name="transaction_ref", length=80) private String txnRef;
}
