package com.egeoz.finalproject.services.impl;

import com.egeoz.finalproject.entities.Customer;
import com.egeoz.finalproject.entities.Invoice;
import com.egeoz.finalproject.entities.Payment;
import com.egeoz.finalproject.repos.CustomerRepository;
import com.egeoz.finalproject.repos.InvoiceRepository;
import com.egeoz.finalproject.repos.PaymentRepository;
import com.egeoz.finalproject.services.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CustomerService implements ICustomerService {
    private final CustomerRepository customerRepository;
    private final InvoiceRepository invoiceRepository;
    private final PaymentRepository paymentRepository;
    private ResponseEntity resp;

    @Autowired
    public CustomerService(CustomerRepository customerRepository, InvoiceRepository invoiceRepository, PaymentRepository paymentRepository) {
        this.customerRepository = customerRepository;
        this.invoiceRepository = invoiceRepository;
        this.paymentRepository = paymentRepository;
    }

    // Get a list of all customers.
    @Override
    public ResponseEntity<List<Customer>> getAll() {
        return new ResponseEntity<>(customerRepository.findAll(), HttpStatus.OK);
    }

    // Get a specific customer by identifier, if not found return HTTP NOT FOUND.
    @Override
    public ResponseEntity<Customer> getByID(Long customerid) {
        // Return customer if found, otherwise return HTTP NOT FOUND.
        Optional<Customer> customerOptional = customerRepository.findById(customerid);
        return customerOptional.map(ResponseEntity::ok)
                .orElse(ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(null));
    }

    @Override
    public ResponseEntity<String> getCustomerDetails(Long id) {
        // Check if customer exists and if not, return HTTP NOT FOUND.
        resp = getByID(id);
        if (resp.getStatusCode() == HttpStatus.NOT_FOUND)
            return new ResponseEntity<>(String.format("Error: Customer %d is not found.", id), HttpStatus.NOT_FOUND);

        StringBuilder invoiceList = new StringBuilder();

        // Get payment information, skip if there are no payments yet.
        Payment pay = paymentRepository.findByCustomerID(id);
        boolean hasUnpaidInvoices = false;
        if (pay != null) invoiceList.append(pay).append("\n");

        // Add all invoices
        invoiceList.append("\n[");
        for (Invoice i : invoiceRepository.findByCustomerID(id)) {
            if (!i.getIsPaid()) hasUnpaidInvoices = true;
            invoiceList.append(i);
        }

        invoiceList.append("]\n\n");
        invoiceList.append(!hasUnpaidInvoices ? "Customer has no unpaid invoices." : "Customer has unpaid invoices.");

        // Return the output as ResponseEntity type.
        return new ResponseEntity<>(String.format("%s%n%s", Objects.requireNonNull(resp.getBody()), invoiceList), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> add(Customer c) {
        // Return BAD REQUEST if any of the attributes are null.
        if (c.getCustomerName() == null || c.getCustomerSurname() == null)
            return new ResponseEntity<>("Error: Bad request.", HttpStatus.BAD_REQUEST);
        resp = new ResponseEntity<>(customerRepository.save(c).getCustomerID(), HttpStatus.OK);

        // Check if the customer was added correctly.
        if (getByID((Long) resp.getBody()).getStatusCode() == HttpStatus.OK) {
            return new ResponseEntity<>(String.format("Customer %d has been created successfully.", (Long) resp.getBody()), HttpStatus.OK);
        }
        return new ResponseEntity<>("Error: Unable to create customer.", HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<String> update(Customer c, Long customerid) {
        // Check if customer exists and if not, return HTTP NOT FOUND.
        resp = getByID(customerid);
        if (resp.getStatusCode() == HttpStatus.NOT_FOUND)
            return new ResponseEntity<>(String.format("Error: Customer %d is not found.", customerid), HttpStatus.NOT_FOUND);

        // Check if the new data is unchanged and if not, return HTTP NOT MODIFIED:
        if (c.equals(resp.getBody()))
            return new ResponseEntity<>(String.format("Error: Specified data for customer %d is unchanged.", customerid), HttpStatus.NOT_MODIFIED);

        // If any of the new attributes are null, keep the previous values. This way the user can adjust a single attribute at a time.
        if (c.getCustomerName() == null) c.setCustomerName(((Customer) resp.getBody()).getCustomerName());
        if (c.getCustomerSurname() == null) c.setCustomerSurname(((Customer) resp.getBody()).getCustomerSurname());

        customerRepository.update(customerid, c.getCustomerName(), c.getCustomerSurname());
        resp = getByID(customerid);
        return new ResponseEntity<>(String.format("Customer %d has been updated successfully:%n%s", customerid, Objects.requireNonNull(resp.getBody())), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> addInvoice(Invoice i, Long customerid) {
        // Check if customer exists and if not, return HTTP NOT FOUND.
        resp = getByID(customerid);
        if (resp.getStatusCode() == HttpStatus.NOT_FOUND)
            return new ResponseEntity<>(String.format("Error: Customer %d is not found.", customerid), HttpStatus.NOT_FOUND);

        i.setCustomerID((Customer) resp.getBody()); // Set customerID foreign key.
        i.setInvoiceDate(new Date(System.currentTimeMillis())); // Set invoice date as now.
        i.setIsPaid(false); // By default payment status is false.
        return new ResponseEntity<>(String.format("Invoice %d has been created successfully.", invoiceRepository.save(i).getInvoiceID()), HttpStatus.OK);
    }

    // Find Payment as optional because customers may not have done any payments yet, therefore no entry on the payment table.
    private ResponseEntity<Payment> getPayment(Long customerid) {
        return (Optional.ofNullable(paymentRepository.findByCustomerID(customerid))).map(ResponseEntity::ok).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    @Override
    public ResponseEntity<String> removeInvoice(Long customerid, Long invoiceid) {
        // Check if customer exists and if not, return HTTP NOT FOUND.
        resp = getByID(customerid);
        if (resp.getStatusCode() == HttpStatus.NOT_FOUND)
            return new ResponseEntity<>(String.format("Error: Customer %d not found.", customerid), HttpStatus.NOT_FOUND);

        // Check if invoice exists and if not, return HTTP NOT FOUND.
        Invoice inv = invoiceRepository.findByIDandCustomer(customerid, invoiceid);
        if (inv == null)
            return new ResponseEntity<>(String.format("Error: Invoice %d is not found.", invoiceid), HttpStatus.NOT_FOUND);

        // Finally, delete the invoice record.
        invoiceRepository.deleteById(invoiceid);
        return new ResponseEntity<>(String.format("Invoice %d has been deleted successfully.", invoiceid), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> addPayment(Long customerid, Long invoiceid) {
        // Check if customer exists and if not, return HTTP NOT FOUND.
        resp = getByID(customerid);
        if (resp.getStatusCode() == HttpStatus.NOT_FOUND)
            return new ResponseEntity<>(String.format("Error: Customer %d is not found.", customerid), HttpStatus.NOT_FOUND);

        // Check if invoice exists and if not, return HTTP NOT FOUND.
        Invoice inv = invoiceRepository.findByIDandCustomer(customerid, invoiceid);
        if (inv == null)
            return new ResponseEntity<>(String.format("Error: Invoice %d is not found.", invoiceid), HttpStatus.NOT_FOUND);

        // Check if the specified invoice has already been paid.
        if (inv.getIsPaid())
            return new ResponseEntity<>(String.format("Error: Invoice %d is already paid.", invoiceid), HttpStatus.BAD_REQUEST);

        ResponseEntity<Payment> paymentResponse = getPayment(customerid);
        Payment pay;

        double total = inv.getInvoiceAmount();

        // If the customer has no prior payments.
        if (paymentResponse.getStatusCode() == HttpStatus.NOT_FOUND) {
            pay = new Payment();
            pay.setCustomerID((Customer) resp.getBody());
            pay.setPaymentDate(new Date(System.currentTimeMillis()));
            pay.setPaymentAmount(total);
            paymentRepository.save(pay);
        } else { // If the customer has prior payments (already has an entry on the payment table).
            pay = paymentResponse.getBody();
            assert pay != null;
            total += pay.getPaymentAmount();
            pay.setPaymentAmount(total);
            pay.setPaymentDate(new Date(System.currentTimeMillis()));
            paymentRepository.updatePayment(customerid, total);
        }

        invoiceRepository.update(customerid, invoiceid, true);
        return new ResponseEntity<>(String.format("Amount %.2f is added to payment. Total amount is now: %.2f", inv.getInvoiceAmount(), pay.getPaymentAmount()), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> remove(Long customerid) {
        // Check if customer exists and if not, return HTTP NOT FOUND.
        resp = getByID(customerid);
        if (resp.getStatusCode() == HttpStatus.NOT_FOUND)
            return new ResponseEntity<>(String.format("Error: Customer %d not found.", customerid), HttpStatus.NOT_FOUND);

        // Delete all invoices that belong to the specified customer.
        for (Invoice i : invoiceRepository.findByCustomerID(customerid)) invoiceRepository.deleteById(i.getInvoiceID());

        // Delete the payment table entry if it exists.
        ResponseEntity<Payment> paymentResponse = getPayment(customerid);
        if (paymentResponse.getStatusCode() != HttpStatus.NOT_FOUND)
            paymentRepository.deleteById(Objects.requireNonNull(paymentResponse.getBody()).getPaymentID());

        // Finally, delete the customer record.
        customerRepository.deleteById(customerid);
        return new ResponseEntity<>(String.format("Customer %d has been deleted successfully.", customerid), HttpStatus.OK);
    }
}
