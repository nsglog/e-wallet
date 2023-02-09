package com.vcriate.vcriateassignment.models;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.FetchType.EAGER;

@Getter
@Setter
@Entity
public class Account extends BaseModel  {
    @Nonnull
    private String username;
    @Nonnull
    private String password;
    private boolean enabled = true;
    private boolean credentialsExpired = false;
    private boolean expired = false;
    private boolean locked = false;
    @ManyToMany(fetch = EAGER, cascade = ALL)
    @JoinTable(
            name = "AccountRole",
            joinColumns = @JoinColumn(name = "accountid", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "roleid", referencedColumnName = "id")
    )
    private Set<Role> roleSet;
    @OneToOne
    private Wallet wallet;
}
