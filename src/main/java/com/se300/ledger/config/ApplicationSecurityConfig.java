package com.se300.ledger.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * ApplicationSecurityConfig class used for Security Configuration
 *
 * @author  Sergey L. Sundukovskiy
 * @version 1.0
 * @since   2023-10-11
 */
@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig {

    @Value("${spring.security.user.name}")
    private String username;

    @Value("${spring.security.user.password}")
    private String password;

    @Bean
    /**
     *
     */
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests((authz) -> {
                            try {
                                authz
                                        .anyRequest()
                                        .authenticated()
                                        .and()
                                        .httpBasic();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                );


        http.csrf().disable();
        http.headers().frameOptions().disable();

        return http.build();
    }

    @Autowired
    /**
     *
     */
    public void configureGlobal(AuthenticationManagerBuilder auth, PasswordEncoder passwordEncoder) throws Exception {
        auth.inMemoryAuthentication()
                .withUser(username)
                .password(passwordEncoder.encode(password))
                .roles("ADMIN");
    }

    @Bean
    /**
     *
     */
    public WebSecurityCustomizer ignoringCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/actuator/prometheus");
    }
}