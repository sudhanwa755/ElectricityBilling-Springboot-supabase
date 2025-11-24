package com.example.ebs.controller;

import com.example.ebs.entity.Customer;
import com.example.ebs.entity.User;
import com.example.ebs.repo.CustomerRepo;
import com.example.ebs.repo.BillRepo;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller @RequestMapping("/my")
public class MyBillsController {
    private final CustomerRepo customers; private final BillRepo bills;
    public MyBillsController(CustomerRepo customers, BillRepo bills){ this.customers=customers; this.bills=bills; }

    @GetMapping("/bills")
    public String myBills(@AuthenticationPrincipal UserDetails principal, Model m){
        if (principal==null) return "redirect:/login";
        String username = principal.getUsername();
        Customer customer = customers.findAll().stream().filter(c -> c.getUser()!=null && username.equals(c.getUser().getUsername())).findFirst().orElse(null);
        if (customer==null){ m.addAttribute("message","Your account is not linked to a customer."); return "error"; }
        m.addAttribute("bills", bills.findAll().stream().filter(b-> b.getCustomer().getId().equals(customer.getId())).toList());
        return "bill/list";
    }
}
