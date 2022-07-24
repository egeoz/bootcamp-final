package com.egeoz.finalproject.controllers;

import com.egeoz.finalproject.entities.Customer;
import com.egeoz.finalproject.entities.Invoice;
import com.egeoz.finalproject.services.ICustomerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/customers")
public class CustomerController {
    private final ICustomerService customerService;

    public CustomerController(ICustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public List<Customer> getAll() {
        return customerService.getAll().getBody();
    }

    @GetMapping("/{customerid}")
    public Customer getByID(@PathVariable("customerid") Long customerid) {
        return customerService.getByID(customerid).getBody();
    }

    @GetMapping("/{customerid}/details")
    public String getCustomerDetails(@PathVariable("customerid") Long customerid) {
        return customerService.getCustomerDetails(customerid).getBody();
    }

    @PostMapping
    public String add(@RequestBody Customer c) {
        return customerService.add(c).getBody();
    }

    @PutMapping("/{customerid}")
    public String update(@RequestBody Customer c, @PathVariable("customerid") Long customerid) {
        return customerService.update(c, customerid).getBody();
    }

    @PostMapping("/{customerid}/addinvoice")
    public String addInvoice(@RequestBody Invoice i, @PathVariable("customerid") Long customerid) {
        return customerService.addInvoice(i, customerid).getBody();
    }

    @PostMapping("/{customerid}/{invoiceid}/pay")
    public String payInvoice(@PathVariable("customerid") Long customerid, @PathVariable("invoiceid") Long invoiceid) {
        return customerService.addPayment(customerid, invoiceid).getBody();
    }

    @DeleteMapping("/{customerid}/{invoiceid}/delete")
    public String deleteInvoice(@PathVariable("customerid") Long customerid, @PathVariable("invoiceid") Long invoiceid) {
        return customerService.removeInvoice(customerid, invoiceid).getBody();
    }

    @DeleteMapping("/{customerid}")
    public String remove(@PathVariable("customerid") Long customerid) {
        return customerService.remove(customerid).getBody();
    }
}
