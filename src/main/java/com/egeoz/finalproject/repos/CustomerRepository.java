package com.egeoz.finalproject.repos;

import com.egeoz.finalproject.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    // Update customer details.
    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Transactional
    @Query(value = "UPDATE customers SET customer_name = :customerName, customer_surname = :customerSurname WHERE customerid = :customerID", nativeQuery = true)
    void update(@Param("customerID") Long customerID, @Param("customerName") String customerName, @Param("customerSurname") String customerSurname);
}