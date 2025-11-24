package com.example.ebs.repo;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.ebs.entity.Tariff;
import java.util.Optional;
public interface TariffRepo extends JpaRepository<Tariff, Long> {  }
