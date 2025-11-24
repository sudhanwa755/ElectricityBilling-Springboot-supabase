package com.example.ebs.repo;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.ebs.entity.Payment;
import java.util.Optional;
public interface PaymentRepo extends JpaRepository<Payment, Long> {  }
