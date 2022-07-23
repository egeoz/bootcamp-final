package com.egeoz.finalproject.controllers;

import com.egeoz.finalproject.entities.Invoice;
import com.egeoz.finalproject.services.IInvoiceService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/invoice")
public class InvoiceController {
    private final IInvoiceService invoiceService;

    public InvoiceController(IInvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @GetMapping
    public List<Invoice> getAll() {
        return invoiceService.getAll().getBody();
    }

    @GetMapping("/status/{ispaid}")
    public List<Invoice> getByPaymentStatus(@PathVariable("ispaid") Boolean ispaid) {
        return invoiceService.getByPaymentStatus(ispaid).getBody();
    }

    @GetMapping("/{invoiceid}")
    public Invoice getByID(@PathVariable("invoiceid") Long invoiceid) {
        return invoiceService.getByID(invoiceid).getBody();
    }
}
