package com.egeoz.finalproject;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Customer {
    @Getter
    private @Id
    @GeneratedValue Long id;

    @Getter @Setter @NonNull
    private String customerName;
    @Getter @Setter @NonNull
    private String customerSurname;
    @Getter @Setter @NonNull
    private long customerSubscriptionID;
}
