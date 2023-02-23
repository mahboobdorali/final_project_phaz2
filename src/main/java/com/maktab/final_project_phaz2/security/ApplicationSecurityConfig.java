package com.maktab.final_project_phaz2.security;

import com.maktab.final_project_phaz2.date.repository.AdminRepository;
import com.maktab.final_project_phaz2.date.repository.CustomerRepository;
import com.maktab.final_project_phaz2.date.repository.ExpertRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class ApplicationSecurityConfig {
    private final PasswordEncoder passwordEncoder;
    private final ExpertRepository expertRepository;
    private final CustomerRepository customerRepository;
    private final AdminRepository adminRepository;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/expert/add_expert").permitAll()
                .requestMatchers("/customer/add_customer").permitAll()
                .requestMatchers("/expert/verify").permitAll()
                .requestMatchers("/admin/add_admin").permitAll()
                .requestMatchers("/expert/**").hasRole("EXPERT")
                .requestMatchers("/customer/**").hasRole("CUSTOMER")
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated().and().httpBasic();
        return http.build();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth)
            throws Exception {

        auth.userDetailsService(username -> customerRepository.findByEmailAddress(username)
                        .orElseThrow(() -> new UsernameNotFoundException(String
                                .format("this %s not found", username))))
                .passwordEncoder(passwordEncoder).and()
                .userDetailsService(username -> expertRepository.findByEmailAddress(username)
                        .orElseThrow(() -> new UsernameNotFoundException(String
                                .format("this %s not found", username))))
                .passwordEncoder(passwordEncoder).and()
                .userDetailsService(username -> adminRepository.findByEmail(username)
                        .orElseThrow(() -> new UsernameNotFoundException(String
                                .format("this %s not found", username))))
                .passwordEncoder(passwordEncoder);
    }
}
