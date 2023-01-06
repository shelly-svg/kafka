package com.example.config.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.web.server.header.ContentSecurityPolicyServerHttpHeadersWriter.CONTENT_SECURITY_POLICY;

@Profile("secure")
@EnableWebSecurity
@Configuration
public class SecurityConfiguration {

    @Value("${spring.security.oauth2.client.registration.custom.client_name}")
    private String applicationName;

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
            .and()
            .headers()
            .contentSecurityPolicy(CONTENT_SECURITY_POLICY)
            .and()
            .frameOptions()
            .disable()
            .and()
            .authorizeHttpRequests()
            .requestMatchers(HttpMethod.GET, "/actuator", "/actuator/**", "/swagger-ui**", "/swagger-ui/**", "/api-docs/**")
            .hasAnyAuthority(authorityConfiguration().getSuperAuthorities())
            .anyRequest()
            .authenticated()
            .and()
            .csrf()
            .disable();

        return http.build();
    }

    @Bean
    protected AuthorityConfiguration authorityConfiguration() {
        return new AuthorityConfiguration(applicationName);
    }

}