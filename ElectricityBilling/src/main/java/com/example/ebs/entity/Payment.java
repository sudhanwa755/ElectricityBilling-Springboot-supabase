package com.example.ebs.entity;
import jakarta.persistence.*; import lombok.*; import java.time.LocalDate;
@Entity @Table(name="payments")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Payment {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
  @ManyToOne(optional=false) @JoinColumn(name="bill_id", nullable=false) private Bill bill;
  @Column(name="paid_on", nullable=false) private LocalDate paidOn;
  @Column(name="amount", nullable=false) private double amount;
  @Column(name="method", length=30) private String method;
  @Column(name="txn_ref", length=80) private String txnRef;
}
