package com.vcriate.vcriateassignment.dtos.requestdtos;

import com.vcriate.vcriateassignment.models.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class CreateWalletRequestDto {

    private String name;
    private String username;
    private String email;
    private Long phoneNumber;
    private String password;
    private Set<Role> roleSet;
}
