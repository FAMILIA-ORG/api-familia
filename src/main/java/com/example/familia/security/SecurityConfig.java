package com.example.familia.security;

import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> {})
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sm ->
                        sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint((req, res, e) -> res.sendError(HttpStatus.UNAUTHORIZED.value()))
                        .accessDeniedHandler((req, res, e) -> res.sendError(HttpStatus.FORBIDDEN.value()))
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                            "/api/auth/register",
                            "/api/auth/login",
                            "/api/auth/activate",
                            "/api/auth/forgot-password",
                            "/api/auth/reset-password",
                            "/v3/api-docs/**",
                            "/v3/api-docs",
                            "/swagger-ui/**",
                            "/swagger-ui.html",
                            "/error"
                        ).permitAll()
                    
                        .requestMatchers("/api/auth/me").authenticated()
                    
                        .requestMatchers(HttpMethod.GET, "/avatars/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/uploads/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/**").permitAll()
                    
                        .requestMatchers("/api/**").hasAnyRole("ADMIN", "USER")
                    
                        .anyRequest().authenticated()
                    )
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config
    ) throws Exception {
        return config.getAuthenticationManager();
    }
}