package com.example.ebs.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.example.ebs.entity.Bill;
import java.time.LocalDate;
import java.util.List;

public interface BillRepo extends JpaRepository<Bill, Long> {
    long countByStatus(String status);

    @Query("SELECT b FROM Bill b WHERE b.billDate >= :startDate ORDER BY b.billDate DESC")
    List<Bill> findRecentBills(LocalDate startDate);

    List<Bill> findTop10ByOrderByBillDateDesc();
}
