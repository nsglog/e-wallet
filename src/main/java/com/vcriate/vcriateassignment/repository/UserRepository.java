package com.vcriate.vcriateassignment.repository;

import com.vcriate.vcriateassignment.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Override
    User save (User user);

    Optional<User> getUserByPhoneNumber(long phoneNumber);
}
