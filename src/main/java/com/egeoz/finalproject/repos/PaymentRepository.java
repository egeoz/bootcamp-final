package com.egeoz.finalproject.repos;

import com.egeoz.finalproject.entities.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    // Find payment that belongs to a specific customer.
    @Query(value = "SELECT p.customerid, p.paymentid, p.payment_amount, p.payment_date FROM payment p WHERE p.customerid = :customerID", nativeQuery = true)
    Payment findByCustomerID(@Param("customerID") long customerID);

    // Update payment by increasing the total payment amount.
    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Transactional
    @Query(value = "UPDATE payment SET payment_amount = :paymentAmount WHERE customerid = :customerID", nativeQuery = true)
    void updatePayment(@Param("customerID") long customerID, @Param("paymentAmount") double paymentAmount);
}
