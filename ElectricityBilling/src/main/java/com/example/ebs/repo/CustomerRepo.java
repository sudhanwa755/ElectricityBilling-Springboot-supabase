package com.example.ebs.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.ebs.entity.Customer;
import java.util.Optional;

public interface CustomerRepo extends JpaRepository<Customer, Long> {
  java.util.Optional<Customer> findByEmailIgnoreCase(String email);

  java.util.Optional<Customer> findByPhone(String phone);

  java.util.List<Customer> findTop5ByOrderByCreatedAtDesc();
}
