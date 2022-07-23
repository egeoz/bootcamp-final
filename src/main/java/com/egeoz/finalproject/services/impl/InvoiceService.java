package com.egeoz.finalproject.services.impl;

import com.egeoz.finalproject.entities.Invoice;
import com.egeoz.finalproject.repos.InvoiceRepository;
import com.egeoz.finalproject.services.IInvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InvoiceService implements IInvoiceService {
    private final InvoiceRepository invoiceRepository;

    @Autowired
    public InvoiceService(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    // Get a list of all invoices.
    @Override
    public ResponseEntity<List<Invoice>> getAll() {
        return new ResponseEntity<>(invoiceRepository.findAll(), HttpStatus.OK);
    }

    // Get a list of all invoices by payment status.
    @Override
    public ResponseEntity<List<Invoice>> getByPaymentStatus(boolean isPaid) {
        return new ResponseEntity<>(invoiceRepository.findByInvoiceStatus(isPaid), HttpStatus.OK);
    }

    // Get a specific invoice by identifier, if not found return HTTP NOT FOUND.
    @Override
    public ResponseEntity<Invoice> getByID(Long id) {
        Optional<Invoice> invoiceOptional = invoiceRepository.findById(id);
        return invoiceOptional.map(ResponseEntity::ok)
                .orElse(ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(null));
    }

}
