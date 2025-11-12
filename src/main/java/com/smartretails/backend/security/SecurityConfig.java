package com.smartretails.backend.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.smartretails.backend.service.CustomUserDetailsService;

import lombok.RequiredArgsConstructor;

import java.util.List;

@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CustomUserDetailsService customUserDetailsService;

    // ✅ Password encoder for bcrypt
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // ✅ Authentication manager setup
    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(customUserDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(provider);
    }

    // ✅ Main security filter chain with CORS support
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // ✅ Enable CORS
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // 🔓 Authentication APIs
                        .requestMatchers("/auth/login").permitAll()
                        .requestMatchers("/auth/signup").hasRole("ADMIN")

                        // 🛍️ Product APIs
                        .requestMatchers(HttpMethod.GET, "/api/products/search-products", "/api/products/{id}")
                        .hasAnyRole("ADMIN", "MANAGER", "CASHIER")
                        .requestMatchers(HttpMethod.GET, "/api/products/**")
                        .hasAnyRole("ADMIN", "MANAGER", "CASHIER")
                        .requestMatchers(HttpMethod.POST, "/api/products/**")
                        .hasAnyRole("ADMIN", "MANAGER")
                        .requestMatchers(HttpMethod.PUT, "/api/products/**")
                        .hasAnyRole("ADMIN", "MANAGER")
                        .requestMatchers(HttpMethod.DELETE, "/api/products/**")
                        .hasRole("ADMIN")

                        // 📦 Stock / Inventory
                        .requestMatchers("/api/stock/**").hasAnyRole("ADMIN", "MANAGER")

                        // 🧾 Purchase Orders
                        .requestMatchers("/api/purchase-orders/**", "/api/purchase-order-items/**")
                        .hasAnyRole("ADMIN", "MANAGER")

                        // 🏭 Suppliers
                        .requestMatchers("/api/suppliers/**").hasAnyRole("ADMIN", "MANAGER")

                        // 💰 Sales
                        .requestMatchers(HttpMethod.POST, "/api/sales/**").hasAnyRole("ADMIN", "MANAGER", "CASHIER")
                        .requestMatchers(HttpMethod.GET, "/api/sales/**").hasAnyRole("ADMIN", "MANAGER")

                        // 👤 User Management
                        .requestMatchers("/api/users/**").hasRole("ADMIN")

                        // 📊 Reports
                        .requestMatchers("/api/reports/**").hasAnyRole("ADMIN", "MANAGER", "CASHIER")

                        // Default rule
                        .anyRequest().authenticated()
                )
                .userDetailsService(customUserDetailsService)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // ✅ Global CORS configuration (for CloudFront + local)
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // ⚠️ Add both environments for safety
        configuration.setAllowedOrigins(List.of(
                "https://d1x2sux8i7gb9h.cloudfront.net", // Production (CloudFront)
                "http://localhost:5173" // Local development
        ));

        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));
        configuration.setAllowCredentials(true); // ✅ Needed for cookies/JWT with credentials

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // Apply globally
        return source;
    }
}
