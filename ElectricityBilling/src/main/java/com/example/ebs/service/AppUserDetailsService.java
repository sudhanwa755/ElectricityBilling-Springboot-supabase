package com.example.ebs.service;
import com.example.ebs.entity.User; import com.example.ebs.repo.UserRepo;
import org.springframework.security.core.userdetails.UserDetails; import org.springframework.security.core.userdetails.UserDetailsService; import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
@Service public class AppUserDetailsService implements UserDetailsService {
  private final UserRepo userRepo; public AppUserDetailsService(UserRepo r){ this.userRepo=r; }
  @Override public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User u = userRepo.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("Not found"));
    return org.springframework.security.core.userdetails.User.withUsername(u.getUsername()).password(u.getPasswordHash()).roles(u.getRole().replace("ROLE_","")).disabled(!u.isActive()).build();
  }
}
