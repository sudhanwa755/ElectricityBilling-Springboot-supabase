package com.example.ebs.entity;
import jakarta.persistence.*; import lombok.*; import java.time.LocalDate;
@Entity @Table(name="tariff")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Tariff {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
  @Column(nullable=false, length=60) private String name;
  @Column(name="slab_start_kwh", nullable=false) private int slabStartKwh;
  @Column(name="slab_end_kwh") private Integer slabEndKwh;
  @Column(name="rate_per_kwh", nullable=false) private double ratePerKwh;
  @Column(name="fixed_charge", nullable=false) private double fixedCharge;
  @Column(name="effective_from", nullable=false) private LocalDate effectiveFrom;
  @Column(name="effective_to") private LocalDate effectiveTo;
}
