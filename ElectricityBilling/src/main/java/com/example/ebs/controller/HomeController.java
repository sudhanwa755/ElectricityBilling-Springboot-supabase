package com.example.ebs.controller;
import org.springframework.stereotype.Controller; import org.springframework.web.bind.annotation.GetMapping; import org.springframework.ui.Model; import com.example.ebs.repo.CustomerRepo;
@Controller public class HomeController { private final CustomerRepo crepo; public HomeController(CustomerRepo crepo){ this.crepo=crepo; }
  @GetMapping({"/","/dashboard"}) public String dashboard(Model m){ m.addAttribute("recentCustomers", crepo.findAll().stream().sorted((a,b)->Long.compare(b.getId(), a.getId())).limit(10).toList()); return "dashboard"; }
  @GetMapping("/login") public String login(){ return "login"; }
}
