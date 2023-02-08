package com.vcriate.vcriateassignment.services;

import com.vcriate.vcriateassignment.models.Role;
import com.vcriate.vcriateassignment.repository.RoleRepository;


public class RoleService {
    private RoleRepository roleRepository;


    public RoleService (RoleRepository roleRepository)  {
        this.roleRepository = roleRepository;
    }

    public Role createRole (String code, String name)   {

        Role role = new Role();
        role.setCode(code);
        role.setName(name);

        return role;
    }
}
