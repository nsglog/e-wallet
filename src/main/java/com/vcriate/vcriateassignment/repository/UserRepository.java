package com.vcriate.vcriateassignment.repository;

import com.vcriate.vcriateassignment.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Override
    User save (User user);

    User getUserByPhoneNumber(long phoneNumber);
}
