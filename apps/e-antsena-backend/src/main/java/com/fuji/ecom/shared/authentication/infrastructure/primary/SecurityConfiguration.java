package com.fuji.ecom.shared.authentication.infrastructure.primary;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
  private final KindeJwtAuthenticationConverter jwtAuthenticationConverter;

  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
    httpSecurity
      .csrf(AbstractHttpConfigurer::disable)
      .authorizeHttpRequests(auth-> auth.anyRequest().permitAll());

    httpSecurity
      .sessionManagement(session-> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
      .oauth2ResourceServer(oauth2-> oauth2.jwt(jwt-> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter)));

    return httpSecurity.build();
  }
}
