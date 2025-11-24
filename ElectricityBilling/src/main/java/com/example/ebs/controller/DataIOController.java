package com.example.ebs.controller;

import com.example.ebs.entity.Bill;
import com.example.ebs.entity.Customer;
import com.example.ebs.repo.BillRepo;
import com.example.ebs.repo.CustomerRepo;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

@Controller @RequestMapping("/data")
public class DataIOController {
    private final CustomerRepo customers; private final BillRepo bills;
    public DataIOController(CustomerRepo customers, BillRepo bills){ this.customers=customers; this.bills=bills; }

    @GetMapping("/export/customers.csv")
    public ResponseEntity<byte[]> exportCustomers(){
        List<Customer> list = customers.findAll();
        String csv = "id,name,email,phone,address,meterNo\n" + list.stream()
                .map(c -> String.join(",", String.valueOf(c.getId()), safe(c.getName()), safe(c.getEmail()), safe(c.getPhone()), safe(c.getAddress()), safe(c.getMeterNo())))
                .collect(Collectors.joining("\n"));
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=customers.csv")
                .contentType(MediaType.TEXT_PLAIN)
                .body(csv.getBytes(StandardCharsets.UTF_8));
    }

    @GetMapping("/export/bills.csv")
    public ResponseEntity<byte[]> exportBills(){
        List<Bill> list = bills.findAll();
        String csv = "id,billNo,customer,units,total,status\n" + list.stream()
                .map(b -> String.join(",", String.valueOf(b.getId()), safe(b.getBillNo()), safe(b.getCustomer().getName()), String.valueOf(b.getUnits()), String.valueOf(b.getTotal()), safe(b.getStatus())))
                .collect(Collectors.joining("\n"));
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=bills.csv")
                .contentType(MediaType.TEXT_PLAIN)
                .body(csv.getBytes(StandardCharsets.UTF_8));
    }

    @GetMapping("/import")
    public String importPage(){ return "data/import"; }

    @PostMapping("/import/customers.csv")
    public String importCustomers(@RequestParam("file") MultipartFile file, Model m) throws Exception {
        String data = new String(file.getBytes(), StandardCharsets.UTF_8);
        String[] lines = data.split("\r?\n");
        int count = 0;
        for (int i=1; i<lines.length; i++){
            String[] cols = lines[i].split(",");
            if (cols.length>=2){
                String name = cols[1].trim();
                String meter = cols.length>=6 ? cols[5].trim() : ("M"+System.currentTimeMillis()+i);
                if (!name.isEmpty() && customers.findAll().stream().noneMatch(c->meter.equalsIgnoreCase(c.getMeterNo()))){
                    customers.save(Customer.builder().name(name).email(get(cols,2)).phone(get(cols,3)).address(get(cols,4)).meterNo(meter).build());
                    count++;
                }
            }
        }
        m.addAttribute("message", "Imported "+count+" customers");
        return "data/import";
    }

    private String safe(String s){ return s==null? "": s.replace(",", " "); }
    private String get(String[] a, int idx){ return idx<a.length? a[idx].trim(): null; }
}
