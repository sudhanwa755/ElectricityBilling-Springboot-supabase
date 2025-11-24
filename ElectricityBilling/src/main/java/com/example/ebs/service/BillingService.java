package com.example.ebs.service;

import com.example.ebs.entity.Tariff;
import com.example.ebs.repo.TariffRepo;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

@Service
public class BillingService {
    private final TariffRepo tariffs;
    public BillingService(TariffRepo tariffs){ this.tariffs = tariffs; }

    public static class Result {
        public int units;
        public double energy;
        public double fixed;
        public double taxes;
        public double total;
    }

    public Result compute(int units, LocalDate billDate){
        final LocalDate date = (billDate == null ? LocalDate.now() : billDate);
        List<Tariff> active = tariffs.findAll().stream()
                .filter(t -> (t.getEffectiveFrom()==null || !date.isBefore(t.getEffectiveFrom()))
                        && (t.getEffectiveTo()==null || !date.isAfter(t.getEffectiveTo())))
                .sorted(Comparator.comparingInt(Tariff::getSlabStartKwh))
                .toList();

        Result r = new Result();
        r.units = Math.max(0, units);
        
        final int totalUnits = r.units;
        Tariff tariff = active.stream()
                .filter(t -> totalUnits >= t.getSlabStartKwh() && (t.getSlabEndKwh() == null || totalUnits < t.getSlabEndKwh()))
                .findFirst()
                .orElse(null);

        if (tariff != null) {
            r.energy = round2(totalUnits * tariff.getRatePerKwh());
            r.fixed = round2(tariff.getFixedCharge());
        } else {
            r.energy = 0.0;
            r.fixed = 0.0;
        }

        r.taxes = 0.0;
        r.total = round2(r.energy + r.fixed);
        return r;
    }

    private double round2(double v){ return Math.round(v * 100.0) / 100.0; }
}
