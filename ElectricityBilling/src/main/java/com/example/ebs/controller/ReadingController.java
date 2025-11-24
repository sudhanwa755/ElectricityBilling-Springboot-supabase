package com.example.ebs.controller;

import com.example.ebs.entity.Bill;
import com.example.ebs.entity.Reading;
import com.example.ebs.entity.Tariff;
import com.example.ebs.repo.BillRepo;
import com.example.ebs.repo.CustomerRepo;
import com.example.ebs.repo.ReadingRepo;
import com.example.ebs.repo.TariffRepo;
import com.example.ebs.service.BillingService;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/readings")
@Transactional
public class ReadingController {
    private final ReadingRepo rrepo;
    private final CustomerRepo crepo;
    private final BillRepo brepo;
    private final BillingService billing;
    private final TariffRepo trepo;

    public ReadingController(ReadingRepo rrepo, CustomerRepo crepo, BillRepo brepo, BillingService billing,
            TariffRepo trepo) {
        this.rrepo = rrepo;
        this.crepo = crepo;
        this.brepo = brepo;
        this.billing = billing;
        this.trepo = trepo;
    }

    @GetMapping("/new")
    public String form(@RequestParam(required = false) Long customerId, Model m) {
        Reading reading = new Reading();

        // If customerId is provided, pre-select that customer
        if (customerId != null) {
            reading.setCustomer(crepo.findById(customerId).orElse(null));
        }

        m.addAttribute("reading", reading);
        m.addAttribute("customers", crepo.findAll());
        List<Tariff> tariffs = trepo.findAll().stream()
                .filter(t -> (t.getEffectiveFrom() == null || !LocalDate.now().isBefore(t.getEffectiveFrom())))
                .filter(t -> (t.getEffectiveTo() == null || !LocalDate.now().isAfter(t.getEffectiveTo())))
                .toList();
        m.addAttribute("tariffs", tariffs);

        // If customerId is provided, show the reading input form, otherwise show
        // customer selection
        if (customerId != null) {
            return "reading/input";
        }
        return "reading/form";
    }

    @PostMapping
    public String create(@ModelAttribute Reading r) {
        if (r.getCurrentReadingKwh() < r.getPreviousReadingKwh())
            throw new IllegalArgumentException("Current >= Previous");

        // Set reading date to today if not provided
        if (r.getReadingDate() == null) {
            r.setReadingDate(LocalDate.now());
        }

        rrepo.save(r);
        // Auto-generate bill right away
        int units = r.getCurrentReadingKwh() - r.getPreviousReadingKwh();
        com.example.ebs.service.BillingService.Result result = billing.compute(units,
                r.getPeriodEnd() != null ? r.getPeriodEnd() : LocalDate.now());
        Bill b = Bill.builder()
                .billNo("B" + System.currentTimeMillis())
                .customer(r.getCustomer())
                .reading(r)
                .billDate(LocalDate.now())
                .units(result.units)
                .energyCharge(result.energy)
                .fixedCharge(result.fixed)
                .taxes(result.taxes)
                .adjustments(0)
                .total(result.total)
                .status("DUE")
                .build();
        brepo.save(b);
        return "redirect:/billing/bills";
    }
}
