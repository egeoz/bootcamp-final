package com.egeoz.finalproject.services.impl;

import com.egeoz.finalproject.entities.Payment;
import com.egeoz.finalproject.repos.PaymentRepository;
import com.egeoz.finalproject.services.IPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentService implements IPaymentService {
    private final PaymentRepository paymentRepository;

    @Autowired
    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    // Get a list of all payments.
    @Override
    public ResponseEntity<List<Payment>> getAll() {
        return new ResponseEntity<>(paymentRepository.findAll(), HttpStatus.OK);
    }

    // Get a specific payment by identifier, if not found return HTTP NOT FOUND.
    @Override
    public ResponseEntity<Payment> getByID(Long id) {
        Optional<Payment> paymentOptional = paymentRepository.findById(id);
        return paymentOptional.map(ResponseEntity::ok)
                .orElse(ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(null));
    }
}