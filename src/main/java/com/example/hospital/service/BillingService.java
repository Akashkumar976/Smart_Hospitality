package com.example.hospital.service;

import com.example.hospital.model.Billing;
import com.example.hospital.repository.BillingRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class BillingService {

    private final BillingRepository billingRepository;

    public BillingService(BillingRepository billingRepository) {
        this.billingRepository = billingRepository;
    }

    public List<Billing> getAllBills() {
        return billingRepository.findAll();
    }

    public Billing getBillById(Long id) {
        return billingRepository.findById(id).orElse(null);
    }

    public void saveBill(Billing bill) {
        if (bill.getDate() == null) bill.setDate(LocalDate.now());
        if (bill.getTime() == null) bill.setTime(LocalTime.now());
        billingRepository.save(bill);
    }

    public void deleteBill(Long id) {
        billingRepository.deleteById(id);
    }
}
