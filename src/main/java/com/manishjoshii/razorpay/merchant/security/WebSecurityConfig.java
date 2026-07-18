package com.manishjoshii.razorpay.merchant.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurityConfig {

    private static final String[] JWT_ROUTES = {
        "/v1/auth/**", "/v1/merchants/**", "/v1/admin/**", "/actuator/**",
    };
    private static final String[] SWAGGER_ROUTES = {
        "/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html"
    };
    private static final String[] API_KEY_ROUTES = {
        "/v1/orders/**", "/v1/paymnets/**", "/v1/vault/**"
    };
    private static final String[] AUTH_ROUTES = {"/v1/auth/signup", "/v1/auth/login"};

    @Bean
    public SecurityFilterChain jwtChain(HttpSecurity http) {
        return http.securityMatcher(JWT_ROUTES)
                .csrf(csrf -> csrf.disable())
                .sessionManagement(
                        session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(
                        auth ->
                                auth.requestMatchers(AUTH_ROUTES)
                                        .permitAll()
                                        .requestMatchers(SWAGGER_ROUTES)
                                        .permitAll()
                                        .anyRequest()
                                        .authenticated())
                .formLogin(formLogin -> formLogin.disable())
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            MerchantUserDetailService userDetailService, PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailService);
        provider.setPasswordEncoder(passwordEncoder);
        return new ProviderManager(provider);
    }
}
