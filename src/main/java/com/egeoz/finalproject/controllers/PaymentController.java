package com.egeoz.finalproject.controllers;

import com.egeoz.finalproject.entities.Payment;
import com.egeoz.finalproject.services.IPaymentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/payment")
public class PaymentController {
    private final IPaymentService paymentService;

    public PaymentController(IPaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping
    public List<Payment> getAll() {
        return paymentService.getAll().getBody();
    }

    @GetMapping("/{paymentid}")
    public Payment getByID(@PathVariable("paymentid") Long paymentid) {
        return paymentService.getByID(paymentid).getBody();
    }
}
