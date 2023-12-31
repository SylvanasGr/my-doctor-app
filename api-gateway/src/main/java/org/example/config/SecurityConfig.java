package org.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig  implements WebFluxConfigurer {

    public static final String DOCTOR_APP_USER = "doctor-app-user";
    public static final String DOCTOR_APP_ADMIN = "doctor-app-admin";
    public static final String DOCTOR_APP_SUPER_USER = "doctor-app-super-user";

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity serverHttpSecurity) {

        serverHttpSecurity
                .authorizeExchange(ex -> ex
                        .pathMatchers("/**").hasAnyRole(DOCTOR_APP_ADMIN, DOCTOR_APP_USER, DOCTOR_APP_SUPER_USER)
                        .anyExchange().permitAll())
                .oauth2ResourceServer(configurer -> configurer
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(new KeycloakJwtConverter())))
                .cors(cors -> cors.configurationSource(req -> {
                    var configuration = new CorsConfiguration();
                    configuration.setAllowedOrigins(List.of("*"));
                    configuration.setAllowedMethods(List.of("*"));
                    configuration.setAllowedHeaders(List.of("*"));
                    return configuration;
                }))
        ;
        return serverHttpSecurity.build();
    }

}
