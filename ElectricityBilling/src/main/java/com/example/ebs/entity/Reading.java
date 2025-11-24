package com.example.ebs.entity;
import jakarta.persistence.*; import lombok.*; import java.time.LocalDate;
@Entity @Table(name="readings")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Reading {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
  @ManyToOne(optional=false) @JoinColumn(name="customer_id", nullable=false) private Customer customer;
  @Column(name="period_start", nullable=false) private LocalDate periodStart;
  @Column(name="period_end", nullable=false) private LocalDate periodEnd;
  @Column(name="previous_reading_kwh", nullable=false) private int previousReadingKwh;
  @Column(name="current_reading_kwh", nullable=false) private int currentReadingKwh;
}
