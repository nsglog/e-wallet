package com.vcriate.vcriateassignment.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.http.HttpMethod.POST;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfiguration {

    private AccountAuthenticationProvider authenticationProvider;

    @Autowired
    public WebSecurityConfiguration(AccountAuthenticationProvider authenticationProvider) {
        this.authenticationProvider = authenticationProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

            http.authorizeHttpRequests((requests) -> requests
                        .requestMatchers(POST, "/wallet").permitAll())
                    .authenticationProvider(authenticationProvider)
                    .httpBasic(Customizer.withDefaults());


            return http.build();
    }
}
