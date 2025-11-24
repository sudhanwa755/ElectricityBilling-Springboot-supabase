package com.example.ebs.entity;
import jakarta.persistence.*; import lombok.*;
@Entity @Table(name="users")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class User {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
  @Column(name="username", unique=true, nullable=false, length=50) private String username;
  @Column(name="password_hash", nullable=false, length=255) private String passwordHash;
  @Column(name="role", nullable=false, length=30) private String role;
  @Builder.Default @Column(name="active", nullable=false) private boolean active=true;
}
