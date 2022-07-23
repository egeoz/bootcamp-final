package com.egeoz.finalproject.repos;

import com.egeoz.finalproject.entities.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    // Find invoices that belong to a specific customer.
    @Query(value = "SELECT i.customerid, i.invoiceid, i.invoice_amount, i.invoice_date, i.is_paid FROM invoice i WHERE i.customerid = :customerID", nativeQuery = true)
    List<Invoice> findByCustomerID(@Param("customerID") long customerID);

    // Find invoices by isPaid.
    @Query(value = "SELECT i.customerid, i.invoiceid, i.invoice_amount, i.invoice_date, i.is_paid FROM invoice i WHERE i.is_paid = :isPaid", nativeQuery = true)
    List<Invoice> findByInvoiceStatus(@Param("isPaid") boolean isPaid);

    // Find a specific invoice with invoice and customer identifiers.
    @Query(value = "SELECT i.customerid, i.invoiceid, i.invoice_amount, i.invoice_date, i.is_paid FROM customers c, invoice i WHERE c.customerid = :customerID AND i.invoiceid = :invoiceID", nativeQuery = true)
    Invoice findByIDandCustomer(@Param("customerID") long customerID, @Param("invoiceID") long invoiceID);

    // Update invoice status by checking for invoice and customer identifiers.
    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Transactional
    @Query(value = "UPDATE invoice SET is_paid = :isPaid WHERE customerid = :customerID AND invoiceid = :invoiceID", nativeQuery = true)
    void update(@Param("customerID") long customerID, @Param("invoiceID") Long invoiceID, @Param("isPaid") boolean isPaid);
}
