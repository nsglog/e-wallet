package com.vcriate.vcriateassignment.repository;

import com.vcriate.vcriateassignment.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
