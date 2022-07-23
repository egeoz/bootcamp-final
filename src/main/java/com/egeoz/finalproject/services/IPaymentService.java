package com.egeoz.finalproject.services;

import com.egeoz.finalproject.entities.Payment;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IPaymentService {
    ResponseEntity<List<Payment>> getAll();

    ResponseEntity<Payment> getByID(Long id);
}
