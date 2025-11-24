package com.example.ebs.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.example.ebs.entity.Payment;
import java.time.LocalDateTime;

public interface PaymentRepo extends JpaRepository<Payment, Long> {
    @Query("SELECT COALESCE(SUM(p.amount), 0.0) FROM Payment p WHERE p.paymentDate >= :startDate")
    double sumPaymentsByDateAfter(LocalDateTime startDate);
}
