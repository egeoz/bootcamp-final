package com.egeoz.finalproject;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Invoice {
    @Getter
    private @Id
    @GeneratedValue Long id;

    @Getter @Setter @NonNull
    private long subscriberID;
    @Getter @Setter @NonNull
    private long invoiceID;
    @Getter @Setter @NonNull
    private double invoiceAmount;
    @Getter @Setter @NonNull
    private String transactionDate;
}
