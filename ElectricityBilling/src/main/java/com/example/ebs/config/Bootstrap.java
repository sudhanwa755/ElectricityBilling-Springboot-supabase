package com.example.ebs.config;

import com.example.ebs.entity.Tariff;
import com.example.ebs.entity.User;
import com.example.ebs.repo.TariffRepo;
import com.example.ebs.repo.UserRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Optional;

@Configuration
public class Bootstrap {
  @Bean CommandLineRunner init(UserRepo users, TariffRepo tariffs, PasswordEncoder enc){
    return args -> {
      Optional<User> eu = users.findByUsername("sudhanwa");
      if (eu.isEmpty()){
        users.save(User.builder()
          .username("sudhanwa")
          .passwordHash(enc.encode("sudhanwa"))
          .role("ROLE_ADMIN")
          .active(true).build());
      }
      if (tariffs.count()==0){
        tariffs.save(Tariff.builder().name("Slab 0-100").slabStartKwh(0).slabEndKwh(100).ratePerKwh(4.71).fixedCharge(0).effectiveFrom(LocalDate.now()).build());
        tariffs.save(Tariff.builder().name("Slab 101-300").slabStartKwh(101).slabEndKwh(300).ratePerKwh(10.29).fixedCharge(0).effectiveFrom(LocalDate.now()).build());
        tariffs.save(Tariff.builder().name("Slab 301-500").slabStartKwh(301).slabEndKwh(500).ratePerKwh(14.55).fixedCharge(0).effectiveFrom(LocalDate.now()).build());
        tariffs.save(Tariff.builder().name("Slab 501-1000").slabStartKwh(501).slabEndKwh(1000).ratePerKwh(16.64).fixedCharge(0).effectiveFrom(LocalDate.now()).build());
        tariffs.save(Tariff.builder().name("Slab >1000").slabStartKwh(1001).slabEndKwh(null).ratePerKwh(17.00).fixedCharge(0).effectiveFrom(LocalDate.now()).build());
      }
    };
  }
}
