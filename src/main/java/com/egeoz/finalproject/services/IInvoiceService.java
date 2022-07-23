package com.egeoz.finalproject.services;

import com.egeoz.finalproject.entities.Invoice;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IInvoiceService {
    ResponseEntity<List<Invoice>> getAll();

    ResponseEntity<List<Invoice>> getByPaymentStatus(boolean isPaid);

    ResponseEntity<Invoice> getByID(Long id);
}
