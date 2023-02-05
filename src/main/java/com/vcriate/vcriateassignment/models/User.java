package com.vcriate.vcriateassignment.models;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class User extends BaseModel {

    private String name;
    private String email;
    private long phoneNumber;
}
