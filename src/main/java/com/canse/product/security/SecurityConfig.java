package com.canse.product.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    AuthenticationManager authManager;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(
                        request -> request.requestMatchers("/api/test") // Rajouter ici le login custom path
                        .permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(new JWTAuthentication(authManager), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}

