package com.example.hospital.config;

import com.example.hospital.service.CustomStaffDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomStaffDetailsService staffDetailsService;
    private final AuthenticationSuccessHandler successHandler;

    // Constructor injection
    public SecurityConfig(CustomStaffDetailsService staffDetailsService, AuthenticationSuccessHandler successHandler) {
        this.staffDetailsService = staffDetailsService;
        this.successHandler = successHandler;
    }

    // Password encoder
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Authentication provider
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(staffDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    // Authentication manager
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    // Security filter chain
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/admin/staff/login", "/admin/staff/signup", "/css/**", "/js/**").permitAll()
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/admin/staff/login")
                .loginProcessingUrl("/admin/staff/login")
                .usernameParameter("staffId")
                .passwordParameter("password")
                .successHandler(successHandler) // custom handler for personal dashboard
                .failureUrl("/admin/staff/login?error")
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/admin/staff/logout")
                .logoutSuccessUrl("/admin/staff/login?logout")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
            )
            .authenticationProvider(authenticationProvider());

        return http.build();
    }
}
