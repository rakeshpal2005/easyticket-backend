package com.rakesh.bms.config;

import com.rakesh.bms.Service.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.http.HttpMethod;
@Configuration
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth

                        // public auth
                        .requestMatchers("/api/auth/**").permitAll()

                        // public read APIs
                        .requestMatchers(HttpMethod.GET, "/api/movies/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/shows/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/theaters/**").permitAll()

                        // booking -> logged in USER or ADMIN
                        .requestMatchers("/api/bookings/**").hasAnyRole("USER", "ADMIN")

                        // movie admin APIs
                        .requestMatchers(HttpMethod.POST, "/api/movies/create").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/movies/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/movies/**").hasRole("ADMIN")

                        // show admin APIs
                        .requestMatchers(HttpMethod.POST, "/api/shows").hasRole("ADMIN")

                        // theater admin APIs
                        .requestMatchers(HttpMethod.POST, "/api/theaters").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/theaters/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/theaters/**").hasRole("ADMIN")

                        // user APIs
                        .requestMatchers(HttpMethod.POST, "/api/users").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/users/**").permitAll()

                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}