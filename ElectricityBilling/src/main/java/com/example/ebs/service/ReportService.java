package com.example.ebs.service;

import com.example.ebs.entity.Bill;
import com.example.ebs.repo.BillRepo;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReportService {
    private final BillRepo bills;
    public ReportService(BillRepo bills){ this.bills = bills; }

    public Map<YearMonth, Double> monthlyRevenue(int monthsBack){
        LocalDate from = LocalDate.now().minusMonths(monthsBack);
        List<Bill> list = bills.findAll().stream().filter(b->!b.getBillDate().isBefore(from)).toList();
        Map<YearMonth, Double> map = new LinkedHashMap<>();
        for (int i=monthsBack; i>=0; i--){
            map.put(YearMonth.now().minusMonths(i), 0.0);
        }
        for (Bill b: list){
            YearMonth ym = YearMonth.from(b.getBillDate());
            map.computeIfPresent(ym, (k,v)-> v + b.getTotal());
        }
        return map;
    }

    public Map<YearMonth, Integer> monthlyUsage(int monthsBack){
        LocalDate from = LocalDate.now().minusMonths(monthsBack);
        List<Bill> list = bills.findAll().stream().filter(b->!b.getBillDate().isBefore(from)).toList();
        Map<YearMonth, Integer> map = new LinkedHashMap<>();
        for (int i=monthsBack; i>=0; i--){
            map.put(YearMonth.now().minusMonths(i), 0);
        }
        for (Bill b: list){
            YearMonth ym = YearMonth.from(b.getBillDate());
            map.computeIfPresent(ym, (k,v)-> v + b.getUnits());
        }
        return map;
    }
}
