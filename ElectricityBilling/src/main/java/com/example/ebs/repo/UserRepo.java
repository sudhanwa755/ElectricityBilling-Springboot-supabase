package com.example.ebs.repo;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.ebs.entity.User;
import java.util.Optional;
public interface UserRepo extends JpaRepository<User, Long> { Optional<User> findByUsername(String username); }
