package com.egeoz.finalproject.entities;

import lombok.*;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@NonNull
@Table(name = "invoice")
@Entity
public class Invoice {
    private @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) Long invoiceID;
    @ManyToOne
    @JoinColumn(name = "customerid", referencedColumnName = "customerid", nullable = false)
    private Customer customerID;
    private double invoiceAmount;
    private Date invoiceDate;
    private Boolean isPaid;

    @Override
    public String toString() {
        return String.format("%nInvoice ID: %d%nInvoice Amount: %.2f%nInvoice Payment Status: %s%nInvoiceDate: %s%n", invoiceID, invoiceAmount, isPaid, invoiceDate);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Invoice invoice = (Invoice) o;

        // Check for the possibility of 'o' having null attributes.
        try {
            return Double.compare(invoice.invoiceAmount, invoiceAmount) == 0 && isPaid == invoice.isPaid && customerID.equals(invoice.customerID) && invoiceDate.equals(invoice.invoiceDate);

        } catch (NullPointerException e) {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerID, invoiceAmount, invoiceDate, isPaid);
    }
}
