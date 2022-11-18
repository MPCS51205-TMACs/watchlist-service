package com.example.watchlistservice.security

import org.springframework.context.annotation.Bean
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.oauth2.server.resource.web.DefaultBearerTokenResolver
import org.springframework.security.web.SecurityFilterChain
import org.springframework.stereotype.Component

@Component
@EnableWebSecurity
class SecurityConfig {

    @Bean
    fun configure(http: HttpSecurity?): SecurityFilterChain {
        return http!!
            .csrf { csrf -> csrf.disable() } // (1)
            .authorizeRequests { auth ->
                auth
                    .anyRequest().permitAll() // (2)
            }
            .build();


    }

}