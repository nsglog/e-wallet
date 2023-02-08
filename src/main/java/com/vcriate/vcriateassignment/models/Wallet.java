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

    @OneToOne
    @Nonnull
    private User user;
    private double balance;
    private String username;
    private String password;
    @ManyToMany(fetch = EAGER, cascade = ALL)
    @JoinTable(
            name = "WalletRole",
            joinColumns = @JoinColumn(name = "walletId", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "roleId", referencedColumnName = "id")
    )
    private Set<Role> roleSet;
    @Nonnull
    private WalletStatusForTransaction walletStatusForTransaction = WalletStatusForTransaction.UNLOCKED;

}
