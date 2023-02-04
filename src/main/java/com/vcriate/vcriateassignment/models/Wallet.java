package com.vcriate.vcriateassignment.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Wallet extends BaseModel   {

    private User user;
    private double balance;

}
