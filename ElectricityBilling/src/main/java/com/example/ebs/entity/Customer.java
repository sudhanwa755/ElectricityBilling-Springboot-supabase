package com.example.ebs.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "customers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 120)
  @NotBlank
  private String name;

  @Column(length = 120, unique = true)
  @Email(message = "Invalid email format")
  private String email;

  @Column(length = 20, unique = true)
  @Pattern(regexp = "^\\+?[0-9]{7,15}$", message = "Phone must be 7-15 digits")
  private String phone;

  @Column(length = 1000)
  private String address;

  @Column(name = "meter_no", unique = true)
  private String meterNo;

  @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Bill> bills;

  @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Reading> readings;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @Column(name = "created_at", nullable = false)
  private LocalDateTime createdAt;

  @PrePersist
  public void _prePersist() {
    if (createdAt == null)
      createdAt = LocalDateTime.now();
  }
}
