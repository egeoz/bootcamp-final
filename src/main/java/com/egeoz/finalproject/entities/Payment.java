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
@Table(name = "payment")
@Entity
public class Payment {
    private @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) Long paymentID;
    @OneToOne
    @JoinColumn(name = "customerid", referencedColumnName = "customerid", nullable = false)
    private Customer customerID;
    private double paymentAmount;
    private Date paymentDate;

    @Override
    public String toString() {
        return String.format("%nPayment ID: %d%nPayment Amount: %.2f%nPayment Date: %s%n", paymentID, paymentAmount, paymentDate);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Payment payment = (Payment) o;

        // Check for the possibility of 'o' having null attributes.
        try {
            return Double.compare(payment.paymentAmount, paymentAmount) == 0 && paymentDate.equals(payment.paymentDate) && customerID.equals(payment.customerID);

        } catch (NullPointerException e) {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerID, paymentID, paymentAmount, paymentDate);
    }
}
