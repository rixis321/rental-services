package com.example.rentalservices.config;

import com.example.rentalservices.security.*;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@SecurityScheme(
        name = "Bearer Authentication",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
public class SecurityConfig {
    private final CustomUserDetailsService userDetailsService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    public SecurityConfig(CustomUserDetailsService userDetailsService, JwtAuthenticationFilter jwtAuthenticationFilter, JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint) {
        this.userDetailsService = userDetailsService;

        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }



    @Bean
   public  SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

         return http
                 .authorizeHttpRequests((auth) -> auth
                         //customer
                         .requestMatchers(HttpMethod.POST,"/api/auth/customer/register").permitAll()
                         .requestMatchers(HttpMethod.POST,"/api/auth/customer/login").permitAll()
                         .requestMatchers(HttpMethod.GET,"/api/customers").hasAuthority("ADMIN")
                         .requestMatchers(HttpMethod.GET,"/api/customers/{customerId}").hasAnyAuthority("CLIENT", "ADMIN")
                         .requestMatchers(HttpMethod.GET,"/api/customers/{customerId}/pesel").hasAnyAuthority("CLIENT", "ADMIN")
                         .requestMatchers(HttpMethod.PUT,"/api/auth/customer/activate").permitAll()
                         //employee
                         .requestMatchers(HttpMethod.POST,"/api/auth/employee/login").permitAll()
                         .requestMatchers(HttpMethod.POST,"/api/auth/employee/register").hasRole("ADMIN")
                         //cars
                         .requestMatchers(HttpMethod.GET,"/api/cars").permitAll()
                         .requestMatchers(HttpMethod.GET,"/api/cars/{carId}").permitAll()
                         .requestMatchers(HttpMethod.POST,"/api/cars").hasRole("ADMIN")
                         .requestMatchers(HttpMethod.PUT,"/api/cars/{carId}").hasRole("ADMIN")
                         //reservations
                         .requestMatchers(HttpMethod.POST,"/api/customers/{customerId}/cars/{carId}/reservations").hasAuthority("CLIENT")
                         .requestMatchers(HttpMethod.GET,"/api/reservations").hasAuthority("ADMIN")
                         .requestMatchers(HttpMethod.GET,"/api/customers/{customerID}/reservations").hasAuthority("CLIENT")
                         //logs
                         .requestMatchers(HttpMethod.GET,"/api/logs").hasAuthority("ADMIN")
                         //swagger
                         .requestMatchers("/swagger-ui/**").permitAll()
                         .requestMatchers("/v3/api-docs/**").permitAll()
                         .anyRequest().denyAll()
                )
                .exceptionHandling((exception)->
                        exception.authenticationEntryPoint(jwtAuthenticationEntryPoint))
                .sessionManagement((session)->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                 .csrf().disable()
                 .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

}
