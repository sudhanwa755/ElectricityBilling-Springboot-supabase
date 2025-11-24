package com.example.ebs.repo;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.ebs.entity.Reading;
import java.util.Optional;
public interface ReadingRepo extends JpaRepository<Reading, Long> {  }
