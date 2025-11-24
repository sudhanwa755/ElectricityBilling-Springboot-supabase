package com.example.ebs.repo;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.ebs.entity.Bill;
import java.util.Optional;
public interface BillRepo extends JpaRepository<Bill, Long> {  }
