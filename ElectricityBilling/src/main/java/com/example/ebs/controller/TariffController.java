package com.example.ebs.controller;

import com.example.ebs.entity.Tariff;
import com.example.ebs.repo.TariffRepo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/tariffs")
public class TariffController {
  private final TariffRepo repo;

  public TariffController(TariffRepo r) {
    this.repo = r;
  }

  @GetMapping
  public String list(Model m) {
    m.addAttribute("tariffs", repo.findAll());
    return "tariff/list";
  }

  @GetMapping("/new")
  public String form(Model m) {
    m.addAttribute("tariff", new Tariff());
    return "tariff/form";
  }

  @PostMapping
  public String create(@ModelAttribute Tariff t) {
    repo.save(t);
    return "redirect:/tariffs";
  }

  @GetMapping("/edit/{id}")
  public String editForm(@PathVariable Long id, Model m) {
    m.addAttribute("tariff", repo.findById(id).orElseThrow());
    return "tariff/form";
  }

  @PostMapping("/delete/{id}")
  public String delete(@PathVariable Long id) {
    repo.deleteById(id);
    return "redirect:/tariffs";
  }
}
