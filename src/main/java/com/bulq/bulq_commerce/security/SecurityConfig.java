package com.bulq.bulq_commerce.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private RSAKey rsaKey;

    @Bean
    public JWKSource<SecurityContext> jwkSource() {
        rsaKey = Jwks.generateRsa();
        JWKSet jwkSet = new JWKSet(rsaKey);
        return (JWKSelector, securityContext) -> JWKSelector.select(jwkSet);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authManager(UserDetailsService userDetailsService) {
        var authProvider = new DaoAuthenticationProvider();
        authProvider.setPasswordEncoder(passwordEncoder());
        authProvider.setUserDetailsService(userDetailsService);
        return new ProviderManager(authProvider);
    }

    @Bean
    JwtDecoder jwtDecoder() throws JOSEException {
        return NimbusJwtDecoder.withPublicKey(rsaKey.toRSAPublicKey()).build();
    }

    @Bean
    JwtEncoder jwtEncoder(JWKSource<SecurityContext> jwks) {
        return new NimbusJwtEncoder(jwks);
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowCredentials(true); // Allow cookies to be included in requests
        config.addAllowedOriginPattern("*"); // Allow all origins
        config.addAllowedHeader("*"); // Allow all headers
        config.addAllowedMethod("*"); // Allow all methods (GET, POST, etc.)

        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(
                        csrf -> csrf
                                .ignoringRequestMatchers("/db/console/**"))
                .headers(
                        header -> header.frameOptions().sameOrigin())
                .authorizeHttpRequests(
                        authorizeRequests -> authorizeRequests
                                // .requestMatchers("api/v1/**").permitAll()
                                // .requestMatchers("/").permitAll()
                                .requestMatchers("/api/v1/auth/token").permitAll()
                                .requestMatchers("/api/v1/auth/users/register").permitAll()
                                .requestMatchers("/api/v1/auth/profile").permitAll()
                                .requestMatchers("/api/v1/auth/users").hasAuthority("SCOPE_ADMIN")
                                .requestMatchers("/api/v1/auth/profile").authenticated()
                                .requestMatchers("/api/v1/auth/profile/delete").authenticated()
                                .requestMatchers("/api/v1/auth/users/change-password").authenticated()
                                .requestMatchers("/api/v1/auth/users/reset-password").permitAll()
                                .requestMatchers("/api/v1/auth/profile/update-password").authenticated()
                                .requestMatchers("/api/v1/auth/users/{userId}/update-authorities")
                                .hasAuthority("SCOPE_ADMIN")
                                .requestMatchers("/api/v1/auth/users/verification-lnk/**").permitAll()
                                .requestMatchers("/api/v1/auth/user/profile/{userId}/update-profile").permitAll()
                                .requestMatchers("/api/v1/products/create-product")
                                .hasAnyAuthority("SCOPE_ADMIN", "SCOPE_BUSINESS")
                                .requestMatchers("/api/v1/products/{productId}/update")
                                .hasAnyAuthority("SCOPE_ADMIN", "SCOPE_BUSINESS")
                                .requestMatchers("/api/v1/products/delete-product")
                                .hasAnyAuthority("SCOPE_ADMIN", "SCOPE_BUSINESS")
                                .requestMatchers("/api/v1/products/add-image")
                                .hasAnyAuthority("SCOPE_ADMIN", "SCOPE_BUSINESS")
                                .requestMatchers("/api/v1/products/add-color")
                                .hasAnyAuthority("SCOPE_ADMIN", "SCOPE_BUSINESS")
                                .requestMatchers("/api/v1/products/add-size")
                                .hasAnyAuthority("SCOPE_ADMIN", "SCOPE_BUSINESS")
                                .requestMatchers("/api/v1/products/{productId}/view").permitAll()
                                .requestMatchers("/api/v1/products/").permitAll()
                                .requestMatchers("/api/v1/business/create-business")
                                .hasAnyAuthority("SCOPE_ADMIN", "SCOPE_BUSINESS")
                                .requestMatchers("/api/v1/business/").hasAnyAuthority("SCOPE_ADMIN", "SCOPE_BUSINESS")
                                .requestMatchers("/api/v1/business/{businessId}/view")
                                .hasAnyAuthority("SCOPE_ADMIN", "SCOPE_BUSINESS")
                                .requestMatchers("/api/v1/business/{businessId}/update")
                                .hasAnyAuthority("SCOPE_ADMIN", "SCOPE_BUSINESS")
                                .requestMatchers("/api/v1/business/product/delete-product")
                                .hasAnyAuthority("SCOPE_ADMIN", "SCOPE_BUSINESS")
                                .requestMatchers("/api/v1/business/product/add-image")
                                .hasAnyAuthority("SCOPE_ADMIN", "SCOPE_BUSINESS")
                                .requestMatchers("/api/v1/business/product/add-size")
                                .hasAnyAuthority("SCOPE_ADMIN", "SCOPE_BUSINESS")
                                .requestMatchers("/api/v1/business/product/add-color")
                                .hasAnyAuthority("SCOPE_ADMIN", "SCOPE_BUSINESS")
                                .requestMatchers("/api/v1/business/{businessId}/product/update")
                                .hasAnyAuthority("SCOPE_ADMIN", "SCOPE_BUSINESS")
                                .requestMatchers("/api/v1/orders/{orderId}/view-order")
                                .hasAnyAuthority("SCOPE_ADMIN", "SCOPE_BUSINESS", "SCOPE_USER")
                                .requestMatchers("/api/v1/orders/{orderId}/update-order")
                                .hasAnyAuthority("SCOPE_ADMIN", "SCOPE_BUSINESS", "SCOPE_USER")
                                .requestMatchers("/api/v1/kyc/perform-kyc")
                                .hasAnyAuthority("SCOPE_ADMIN", "SCOPE_BUSINESS", "SCOPE_USER")
                                .requestMatchers("/api/v1/kyc/all")
                                .hasAnyAuthority("SCOPE_ADMIN", "SCOPE_BUSINESS", "SCOPE_USER")
                                .requestMatchers("/api/v1/kyc/{kycId}/view-kyc")
                                .hasAnyAuthority("SCOPE_ADMIN", "SCOPE_BUSINESS", "SCOPE_USER")
                                .requestMatchers("/api/v1/kyc/update-kyc")
                                .hasAnyAuthority("SCOPE_ADMIN", "SCOPE_BUSINESS", "SCOPE_USER")
                                .requestMatchers("/api/v1/kyc/update-kyc")
                                .hasAnyAuthority("SCOPE_ADMIN", "SCOPE_BUSINESS", "SCOPE_USER")
                                .requestMatchers("/api/v1/notifications/send-notification").hasAnyAuthority("SCOPE_USER", "SCOPE_ADMIN")
                                .requestMatchers("/api/v1/notifications/{accountId}/view-notification").hasAnyAuthority("SCOPE_USER", "SCOPE_ADMIN")
                                .requestMatchers("/swagger-ui/**").permitAll()
                                .requestMatchers("/v3/api-docs/**").permitAll())
                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.csrf(
                csrf -> csrf.disable());
        return http.build();
    }
}
