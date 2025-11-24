package com.example.ebs.controller;

import com.example.ebs.entity.Bill;
import com.example.ebs.entity.Reading;
import com.example.ebs.repo.BillRepo;
import com.example.ebs.repo.ReadingRepo;
import com.example.ebs.service.BillingService;
import com.example.ebs.service.PdfService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/billing")
public class BillController {

    private final BillRepo brepo;
    private final ReadingRepo rrepo;
    private final BillingService billing;
    private final PdfService pdfService;

    public BillController(BillRepo brepo, ReadingRepo rrepo, BillingService billing, PdfService pdfService) {
        this.brepo = brepo;
        this.rrepo = rrepo;
        this.billing = billing;
        this.pdfService = pdfService;
    }

    @GetMapping("/bills")
    public String list(Model model) {
        java.util.List<Bill> bills = brepo.findAll();
        for (Bill b : bills) {
            boolean needs = (b.getTotal() == 0 || b.getEnergyCharge() == 0 || b.getUnits() == 0);
            if (needs) {
                int units = b.getUnits();
                java.time.LocalDate date = b.getBillDate() != null ? b.getBillDate()
                        : (b.getReading() != null && b.getReading().getPeriodEnd() != null
                                ? b.getReading().getPeriodEnd()
                                : java.time.LocalDate.now());
                if (b.getReading() != null) {
                    units = b.getReading().getCurrentReadingKwh() - b.getReading().getPreviousReadingKwh();
                }
                com.example.ebs.service.BillingService.Result res = billing.compute(Math.max(0, units), date);
                b.setUnits(res.units);
                b.setEnergyCharge(res.energy);
                b.setFixedCharge(res.fixed);
                b.setTaxes(res.taxes);
                b.setTotal(res.total);
                if (b.getBillNo() == null)
                    b.setBillNo("B" + System.currentTimeMillis());
                if (b.getBillDate() == null)
                    b.setBillDate(date);
                brepo.save(b);
            }
        }

        // Calculate stats
        long paidCount = bills.stream().filter(b -> "PAID".equals(b.getStatus())).count();
        long pendingCount = bills.stream().filter(b -> "DUE".equals(b.getStatus()) || "OVERDUE".equals(b.getStatus()))
                .count();
        double totalRevenue = bills.stream().mapToDouble(Bill::getTotal).sum();

        model.addAttribute("bills", bills);
        model.addAttribute("paidCount", paidCount);
        model.addAttribute("pendingCount", pendingCount);
        model.addAttribute("totalRevenue", String.format("%.2f", totalRevenue));
        return "bill/list";
    }

    @GetMapping("/pdf/{id}")
    public ResponseEntity<byte[]> pdf(@PathVariable("id") Long id) {
        Bill b = brepo.findById(id).orElseThrow();
        if (b.getTotal() == 0 || b.getEnergyCharge() == 0 || b.getUnits() == 0) {
            if (b.getReading() != null) {
                int units = b.getReading().getCurrentReadingKwh() - b.getReading().getPreviousReadingKwh();
                var res = billing.compute(units, b.getReading().getPeriodEnd());
                b.setUnits(res.units);
                b.setEnergyCharge(res.energy);
                b.setFixedCharge(res.fixed);
                b.setTaxes(res.taxes);
                b.setTotal(res.total);
            }
            if (b.getBillNo() == null) {
                b.setBillNo("B" + System.currentTimeMillis());
            }
            brepo.save(b);
        }
        byte[] bytes = pdfService.billToPdf(b);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=bill-" + b.getId() + ".pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(bytes);
    }

    @PostMapping("/markPaid/{id}")
    public String markPaid(@PathVariable("id") Long id) {
        Bill b = brepo.findById(id).orElseThrow();
        b.setStatus("PAID");
        brepo.save(b);
        return "redirect:/billing/bills";
    }

    @PostMapping("/markAllPaid")
    public String markAllPaid() {
        List<Bill> bills = brepo.findAll();
        for (Bill b : bills) {
            b.setStatus("PAID");
            brepo.save(b);
        }
        return "redirect:/billing/bills";
    }
}
