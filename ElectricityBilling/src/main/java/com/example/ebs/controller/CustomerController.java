package com.example.ebs.controller;
import com.example.ebs.entity.Customer; import com.example.ebs.repo.CustomerRepo;
import jakarta.validation.Valid; import org.springframework.stereotype.Controller; import org.springframework.ui.Model; import org.springframework.validation.BindingResult; import org.springframework.web.bind.annotation.*;
@Controller @RequestMapping("/customers")
public class CustomerController {
  @org.springframework.security.access.prepost.PreAuthorize("hasRole('ADMIN')")
  @GetMapping("/edit/{id}") public String edit(@PathVariable("id") Long id, Model m){ m.addAttribute("customer", repo.findById(id).orElseThrow()); return "customer/edit"; }
  @org.springframework.security.access.prepost.PreAuthorize("hasRole('ADMIN')")
  @PostMapping("/edit/{id}") public String update(@PathVariable("id") Long id, @jakarta.validation.Valid @ModelAttribute("customer") com.example.ebs.entity.Customer customerDetails, org.springframework.validation.BindingResult br){
    if (br.hasErrors()) { return "customer/edit"; }

    com.example.ebs.entity.Customer existingCustomer = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid customer Id:" + id));

    // uniqueness checks
    if (customerDetails.getEmail() != null && !customerDetails.getEmail().isBlank()) {
        var ex = repo.findByEmailIgnoreCase(customerDetails.getEmail());
        if (ex.isPresent() && !ex.get().getId().equals(id)) {
            br.rejectValue("email", "exists", "Email already exists");
            return "customer/edit";
        }
    }
    if (customerDetails.getPhone() != null && !customerDetails.getPhone().isBlank()) {
        var ex2 = repo.findByPhone(customerDetails.getPhone());
        if (ex2.isPresent() && !ex2.get().getId().equals(id)) {
            br.rejectValue("phone", "exists", "Phone already exists");
            return "customer/edit";
        }
    }

    existingCustomer.setName(customerDetails.getName());
    existingCustomer.setEmail(customerDetails.getEmail());
    existingCustomer.setPhone(customerDetails.getPhone());
    existingCustomer.setAddress(customerDetails.getAddress());
    // existingCustomer.setMeterNo(customerDetails.getMeterNo()); // Handled by DB

    repo.save(existingCustomer);
    return "redirect:/customers";
}
  @org.springframework.security.access.prepost.PreAuthorize("hasRole('ADMIN')")
  @GetMapping("/delete/{id}") public String confirmDelete(@PathVariable("id") Long id, Model m){ m.addAttribute("customer", repo.findById(id).orElseThrow()); return "customer/delete"; }
  @org.springframework.security.access.prepost.PreAuthorize("hasRole('ADMIN')")
  @PostMapping("/delete/{id}") public String delete(@PathVariable("id") Long id){ repo.deleteById(id); return "redirect:/customers"; }

  private final CustomerRepo repo; public CustomerController(CustomerRepo r){ this.repo=r; }
  @GetMapping public String list(Model m){ m.addAttribute("customers", repo.findAll()); return "customer/list"; }
@GetMapping("/{id}") public String view(@PathVariable("id") Long id, Model m){ m.addAttribute("customer", repo.findById(id).orElseThrow()); return "customer/view"; }
  @GetMapping("/new") public String form(Model m){ m.addAttribute("customer", new Customer()); return "customer/form"; }
  @PostMapping public String create(@ModelAttribute @Valid Customer c, BindingResult br){
  if (br.hasErrors()) return "customer/form";
  if (c.getEmail()!=null && !c.getEmail().isBlank() && repo.findByEmailIgnoreCase(c.getEmail()).isPresent()){
    br.rejectValue("email","exists","Email already exists");
    return "customer/form";
  }
  if (c.getPhone()!=null && !c.getPhone().isBlank() && repo.findByPhone(c.getPhone()).isPresent()){
    br.rejectValue("phone","exists","Phone already exists");
    return "customer/form";
  }
  Customer saved = repo.save(c);
  return "redirect:/customers/" + saved.getId();
}
}
