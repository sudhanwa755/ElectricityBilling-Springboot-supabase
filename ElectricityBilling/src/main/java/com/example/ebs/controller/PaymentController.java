package com.example.ebs.controller;

import com.example.ebs.entity.Bill;
import com.example.ebs.entity.Payment;
import com.example.ebs.repo.BillRepo;
import com.example.ebs.repo.PaymentRepo;
import com.example.ebs.service.BillingService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;

@Controller
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentRepo prepo;
    private final BillRepo brepo;
    private final BillingService billing;

    public PaymentController(PaymentRepo prepo, BillRepo brepo, BillingService billing) {
        this.prepo = prepo;
        this.brepo = brepo;
        this.billing = billing;
    }

    @GetMapping("/new/{billId}")
    public String form(@PathVariable("billId") Long billId, Model m){
        Bill b = brepo.findById(billId).orElseThrow();
        if (b.getTotal()==0 || b.getEnergyCharge()==0 || b.getUnits()==0){
            if (b.getReading()!=null){
                int units = b.getReading().getCurrentReadingKwh() - b.getReading().getPreviousReadingKwh();
                var res = billing.compute(units, b.getReading().getPeriodEnd());
                b.setUnits(res.units); b.setEnergyCharge(res.energy); b.setFixedCharge(res.fixed); b.setTaxes(res.taxes); b.setTotal(res.total);
            }
            if (b.getBillNo()==null) b.setBillNo("B"+System.currentTimeMillis());
            brepo.save(b);
        }
        m.addAttribute("bill", b);
        m.addAttribute("payment", Payment.builder()
                .bill(b).paidOn(LocalDate.now()).amount(b.getTotal()).method("CASH").build());
        return "payment/form";
    }

    @PostMapping
    public String create(@ModelAttribute Payment p){
        Bill b = brepo.findById(p.getBill().getId()).orElseThrow();
        // Recompute bill totals to be safe
        if (b.getTotal()==0 || b.getEnergyCharge()==0 || b.getUnits()==0){
            if (b.getReading()!=null){
                int units = b.getReading().getCurrentReadingKwh() - b.getReading().getPreviousReadingKwh();
                var res = billing.compute(units, b.getReading().getPeriodEnd());
                b.setUnits(res.units); b.setEnergyCharge(res.energy); b.setFixedCharge(res.fixed); b.setTaxes(res.taxes); b.setTotal(res.total);
            }
            if (b.getBillNo()==null) b.setBillNo("B"+System.currentTimeMillis());
            brepo.save(b);
        }
        // Force payment amount to match bill total
        p.setAmount(b.getTotal());
        p.setBill(b);
        prepo.save(p);
        b.setStatus("PAID");
        brepo.save(b);
        return "redirect:/payments/success/" + b.getId();
    }

    @GetMapping("/success/{billId}")
    public String success(@PathVariable("billId") Long billId, Model m){
        m.addAttribute("billId", billId);
        return "payment/success";
    }
}
