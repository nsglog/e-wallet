package com.vcriate.vcriateassignment.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.http.HttpMethod.GET;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration {

    private AccountAuthenticationProvider authenticationProvider;

    @Autowired
    public WebSecurityConfiguration(AccountAuthenticationProvider authenticationProvider) {
        this.authenticationProvider = authenticationProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

            http.csrf().disable();
            http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
            http.authorizeHttpRequests().requestMatchers("/wallet").permitAll();
            http.authorizeHttpRequests().requestMatchers("/user/**").hasAnyRole("ROLE_USER","ROLE_ADMIN").anyRequest().authenticated();
            http.authenticationProvider(authenticationProvider);

            return http.build();
    }
}
