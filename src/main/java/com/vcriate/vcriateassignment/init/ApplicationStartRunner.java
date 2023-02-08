package com.vcriate.vcriateassignment.init;

import com.vcriate.vcriateassignment.models.Role;
import com.vcriate.vcriateassignment.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import static java.util.Arrays.asList;

@Component
@RequiredArgsConstructor
public class ApplicationStartRunner implements CommandLineRunner {
    private final RoleRepository roleRepository;

    @Override
    public void run(String ...args) throws Exception {
        Role roleUser = new Role("123", "ROLE_USER");
        Role roleAdmin = new Role("456", "ROLE_ADMIN");
        roleRepository.saveAll(asList(roleUser, roleAdmin));
    }
}
