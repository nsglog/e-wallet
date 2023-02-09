package com.vcriate.vcriateassignment.dtos.requestdtos;

import com.vcriate.vcriateassignment.models.Role;
import jakarta.annotation.Nonnull;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
@Getter
@Setter
public class CreateAccountRequestDto {
    private String username;
    @Nonnull
    private String password;
    private Set<Role> roleSet;

    private String name;
    private String email;
    private Long phoneNumber;
}
