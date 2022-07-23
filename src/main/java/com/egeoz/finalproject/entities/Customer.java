package com.egeoz.finalproject.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@NonNull
@Table(name = "customers")
@Entity
public class Customer implements Serializable {
    private @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) Long customerID;
    private String customerName;
    private String customerSurname;

    @Override
    public String toString() {
        return String.format("Customer ID: %d%nCustomer Name: %s %s", customerID, customerName, customerSurname);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;

        // Check for the possibility of 'o' having null attributes.
        try {
            return customerName.equals(customer.customerName) && customerSurname.equals(customer.customerSurname);
        } catch (NullPointerException e) {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
