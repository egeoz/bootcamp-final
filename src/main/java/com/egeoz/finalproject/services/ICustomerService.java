package com.egeoz.finalproject.services;

import com.egeoz.finalproject.entities.Customer;
import com.egeoz.finalproject.entities.Invoice;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ICustomerService {
    ResponseEntity<List<Customer>> getAll();

    ResponseEntity<Customer> getByID(Long customerid);

    ResponseEntity<String> getCustomerDetails(Long id);

    ResponseEntity<String> add(Customer c);

    ResponseEntity<String> update(Customer c, Long customerid);

    ResponseEntity<String> addInvoice(Invoice i, Long customerid);

    ResponseEntity<String> addPayment(Long customerid, Long invoiceid);

    ResponseEntity<String> remove(Long customerid);
}
