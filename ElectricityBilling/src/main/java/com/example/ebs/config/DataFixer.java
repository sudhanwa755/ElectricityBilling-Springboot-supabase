package com.example.ebs.config;

import com.example.ebs.entity.Bill;
import com.example.ebs.entity.Reading;
import com.example.ebs.repo.BillRepo;
import com.example.ebs.repo.ReadingRepo;
import com.example.ebs.service.BillingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.List;

@Configuration
public class DataFixer {
    private static final Logger log = LoggerFactory.getLogger(DataFixer.class);

    @Bean CommandLineRunner repairBills(BillRepo bills, ReadingRepo readings, BillingService billing){
        return args -> {
            List<Bill> list = bills.findAll();
            int fixed = 0;
            for (Bill b : list){
                boolean needs = b.getTotal() == 0 || b.getEnergyCharge() == 0 || b.getUnits() == 0 || b.getBillNo()==null;
                if (needs){
                    Reading r = b.getReading();
                    if (r == null){
                        // Try to infer reading by ID match (skip if none)
                        continue;
                    }
                    int units = r.getCurrentReadingKwh() - r.getPreviousReadingKwh();
                    var res = billing.compute(units, r.getPeriodEnd()!=null? r.getPeriodEnd(): LocalDate.now());
                    if (b.getBillNo() == null) b.setBillNo("B"+System.currentTimeMillis());
                    b.setUnits(res.units);
                    b.setEnergyCharge(res.energy);
                    b.setFixedCharge(res.fixed);
                    b.setTaxes(res.taxes);
                    b.setTotal(res.total);
                    if (b.getBillDate() == null) b.setBillDate(LocalDate.now());
                    bills.save(b);
                    fixed++;
                }
            }
            if (fixed>0) log.info("DataFixer repaired {} bills with missing totals/fields", fixed);
        };
    }
}
