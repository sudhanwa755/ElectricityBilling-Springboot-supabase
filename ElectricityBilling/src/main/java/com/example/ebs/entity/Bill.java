package com.example.ebs.entity;
import jakarta.persistence.*; import lombok.*; import java.time.LocalDate;
import java.util.List;

@Entity @Table(name="bills")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Bill {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
  @Column(name="bill_no", unique=true, nullable=false, length=40) private String billNo;
  @ManyToOne(optional=false) @JoinColumn(name="customer_id", nullable=false) private Customer customer;
  @OneToOne(optional=false) @JoinColumn(name="reading_id", nullable=false) private Reading reading;
  @OneToMany(mappedBy = "bill", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Payment> payments;
  @Column(name="bill_date", nullable=false) private LocalDate billDate;
  @Column(name="units", nullable=false) private int units;
  @Column(name="energy_charge", nullable=false) private double energyCharge;
  @Column(name="fixed_charge", nullable=false) private double fixedCharge;
  @Column(name="taxes", nullable=false) private double taxes;
  @Column(name="adjustments", nullable=false) private double adjustments;
  @Column(name="total", nullable=false) private double total;
  @Column(name="status", length=20, nullable=false) private String status;
}