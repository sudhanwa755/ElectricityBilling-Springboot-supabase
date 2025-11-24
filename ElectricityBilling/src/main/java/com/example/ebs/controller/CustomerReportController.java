package com.example.ebs.controller;

import com.example.ebs.entity.Bill;
import com.example.ebs.entity.Customer;
import com.example.ebs.entity.Reading;
import com.example.ebs.repo.BillRepo;
import com.example.ebs.repo.CustomerRepo;
import com.example.ebs.repo.ReadingRepo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/customer-report")
public class CustomerReportController {
    private final CustomerRepo customers;
    private final ReadingRepo readings;
    private final BillRepo bills;

    public CustomerReportController(CustomerRepo customers, ReadingRepo readings, BillRepo bills) {
        this.customers = customers;
        this.readings = readings;
        this.bills = bills;
    }

    @GetMapping
    public String report(Model m){
        List<Customer> all = customers.findAll();

        // For each customer, find latest reading and latest bill
        List<Map<String, Object>> rows = all.stream().map(c -> {
            Reading latestReading = readings.findAll().stream()
                    .filter(r -> r.getCustomer()!=null && r.getCustomer().getId().equals(c.getId()))
                    .max(Comparator.comparing(Reading::getPeriodEnd))
                    .orElse(null);

            Bill latestBill = bills.findAll().stream()
                    .filter(b -> b.getCustomer()!=null && b.getCustomer().getId().equals(c.getId()))
                    .max(Comparator.comparing(Bill::getBillDate))
                    .orElse(null);

            return Map.of(
                    "customer", c,
                    "latestReading", latestReading,
                    "latestBill", latestBill
            );
        }).collect(Collectors.toList());

        m.addAttribute("rows", rows);
        return "report/customer_report";
    }
}
