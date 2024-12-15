package org.pizza.crm;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/css/**", "/js/**", "/images/**", "/static/**").permitAll()
                        .requestMatchers("/cart/**").hasAnyRole("ADMIN", "CLIENT")
                        .requestMatchers("/menu/**").hasAnyRole("ADMIN", "CLIENT")
                        .requestMatchers("/orders").hasRole("COURIER")
                        .requestMatchers("/orders/**").hasAnyRole("ADMIN", "COURIER")
                        .requestMatchers("/orders/create").hasAnyRole("ADMIN", "CLIENT")
                        .requestMatchers("/orders").hasAnyRole("ADMIN", "COURIER")
                        .requestMatchers("/couriers").hasAnyRole("ADMIN", "COURIER")
                        .requestMatchers("/couriers/**").hasRole("ADMIN")
                        .requestMatchers("/login", "/register", "/").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/", true)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .permitAll()
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
