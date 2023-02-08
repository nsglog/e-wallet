package com.vcriate.vcriateassignment.services;

//import com.vcriate.vcriateassignment.configurations.SecurityConfiguration;
import com.vcriate.vcriateassignment.models.User;
import com.vcriate.vcriateassignment.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserRepository userRepository;
    @Autowired
    public UserService(UserRepository userRepository)   {
        this.userRepository = userRepository;
    }

    public User createUser (String name, String email, Long phoneNumber, String password) {

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPhoneNumber(phoneNumber);
        userRepository.save(user);

        return user;
    }

}
