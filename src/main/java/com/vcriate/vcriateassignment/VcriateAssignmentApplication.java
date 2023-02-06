package com.vcriate.vcriateassignment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@SpringBootApplication
public class VcriateAssignmentApplication {


    public static void main(String[] args) {
        SpringApplication.run(VcriateAssignmentApplication.class, args);
    }

}
