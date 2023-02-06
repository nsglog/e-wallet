package com.vcriate.vcriateassignment.models;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Wallet extends BaseModel   {

    @OneToOne
    private User user;
    private double balance;
    private WalletStatusForTransaction walletStatusForTransaction;

}
