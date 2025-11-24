package com.example.ebs.config;

import com.example.ebs.service.AppUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {
    @Bean public PasswordEncoder passwordEncoder(){ return new BCryptPasswordEncoder(); }
    @Bean public DaoAuthenticationProvider authProvider(AppUserDetailsService uds, PasswordEncoder enc){
        DaoAuthenticationProvider p = new DaoAuthenticationProvider();
        p.setUserDetailsService(uds); p.setPasswordEncoder(enc); return p;
    }
    @Bean public SecurityFilterChain filter(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/css/**","/*.css","/js/**","/images/**","/h2-console/**","/register","/login").permitAll()
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .requestMatchers("/my/**").hasAnyRole("CUSTOMER","ADMIN","CLERK")
                .requestMatchers("/billing/**","/payments/**","/reports/**","/data/**").hasAnyRole("ADMIN","CLERK")
                .anyRequest().authenticated())
            .formLogin(form -> form.loginPage("/login").permitAll().defaultSuccessUrl("/dashboard", true))
            .logout(logout -> logout.logoutUrl("/logout").logoutSuccessUrl("/login?out").permitAll())
            .headers(h->h.frameOptions(f->f.disable()))
            .csrf(csrf->csrf.ignoringRequestMatchers("/h2-console/**"));
        return http.build();
    }
}
