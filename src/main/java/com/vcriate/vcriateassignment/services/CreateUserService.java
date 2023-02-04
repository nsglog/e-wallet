package com.vcriate.vcriateassignment.services;

import com.vcriate.vcriateassignment.models.User;
import org.springframework.stereotype.Service;

@Service
public class CreateUserService {

    public User createUser (String name, String email, Long phoneNumber) {

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPhoneNumber(phoneNumber);

        return user;
    }

}
