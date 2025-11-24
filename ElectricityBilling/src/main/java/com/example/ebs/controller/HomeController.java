package com.example.ebs.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import com.example.ebs.repo.CustomerRepo;
import com.example.ebs.repo.BillRepo;
import com.example.ebs.repo.PaymentRepo;
import com.example.ebs.entity.Bill;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

// Note: recentCustomers will be fetched inside the dashboard method
@Controller
public class HomeController {
  private final CustomerRepo crepo;
  private final BillRepo billRepo;
  private final PaymentRepo paymentRepo;

  public HomeController(CustomerRepo crepo, BillRepo billRepo, PaymentRepo paymentRepo) {
    this.crepo = crepo;
    this.billRepo = billRepo;
    this.paymentRepo = paymentRepo;
  }

  @GetMapping({ "/", "/dashboard" })
  public String dashboard(Model m) {
    // Total customers
    long totalCustomers = crepo.count();

    // Pending bills (status = "PENDING" or "OVERDUE")
    long pendingBills = billRepo.countByStatus("PENDING");
    long overdueBills = billRepo.countByStatus("OVERDUE");

    // Month-to-date revenue
    LocalDate firstDayOfMonth = LocalDate.now().withDayOfMonth(1);
    double monthlyRevenue = paymentRepo.sumPaymentsByDateAfter(firstDayOfMonth.atStartOfDay());

    // Recent bills for chart data (last 10)
    List<Bill> recentBills = billRepo.findTop10ByOrderByBillDateDesc();

    // Sort by date ascending for the chart (oldest to newest)
    List<Bill> chartBills = recentBills.stream()
        .sorted(Comparator.comparing(Bill::getBillDate))
        .collect(Collectors.toList());

    // Prepare chart data
    List<String> chartLabels = chartBills.stream()
        .map(b -> b.getBillDate().toString())
        .collect(Collectors.toList());

    List<Integer> consumptionData = chartBills.stream()
        .map(Bill::getUnits)
        .collect(Collectors.toList());

    List<Double> revenueData = chartBills.stream()
        .map(Bill::getTotal)
        .collect(Collectors.toList());

    // Add attributes for charts
    m.addAttribute("chartLabels", chartLabels);
    m.addAttribute("consumptionData", consumptionData);
    m.addAttribute("revenueData", revenueData);

    // Add other attributes
    m.addAttribute("totalCustomers", totalCustomers);
    m.addAttribute("pendingBills", pendingBills);
    m.addAttribute("overdueBills", overdueBills);
    m.addAttribute("monthlyRevenue", monthlyRevenue);

    // Fetch recent customers
    List<com.example.ebs.entity.Customer> recentCustomers = crepo.findTop5ByOrderByCreatedAtDesc();
    m.addAttribute("recentCustomers", recentCustomers);

    m.addAttribute("recentBills", recentBills);

    return "dashboard";

  }

  @GetMapping("/login")
  public String login() {
    return "login";
  }
}
