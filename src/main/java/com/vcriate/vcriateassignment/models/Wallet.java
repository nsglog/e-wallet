package com.vcriate.vcriateassignment.models;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.util.Set;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.FetchType.EAGER;

@Getter
@Setter
@Entity
public class Wallet extends BaseModel   {

    @Nonnull
    private String name;
    private String email;
    private Long phoneNumber;
    private double balance;
    private WalletStatusForTransaction walletStatusForTransaction = WalletStatusForTransaction.UNLOCKED;

}
