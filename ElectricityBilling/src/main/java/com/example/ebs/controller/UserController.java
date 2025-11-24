package com.example.ebs.controller;

import com.example.ebs.dto.UserForm;
import com.example.ebs.entity.User;
import com.example.ebs.repo.UserRepo;
import jakarta.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {
    private final UserRepo userRepo; private final PasswordEncoder encoder;
    public UserController(UserRepo userRepo, PasswordEncoder encoder){ this.userRepo=userRepo; this.encoder=encoder; }

    @GetMapping("/register")
    public String registerForm(Model m){ m.addAttribute("userForm", new UserForm()); return "user/register"; }

    @PostMapping("/register")
    public String register(@ModelAttribute @Valid UserForm form, BindingResult br){
        if (br.hasErrors()) return "user/register";
        if (userRepo.findByUsername(form.getUsername()).isPresent()){
            br.rejectValue("username","exists","Username already exists");
            return "user/register";
        }
        User u = User.builder()
                .username(form.getUsername())
                .passwordHash(encoder.encode(form.getPassword()))
                .role("ROLE_CUSTOMER")
                .active(true)
                .build();
        userRepo.save(u);
        return "redirect:/login?registered=1";
    }
}
