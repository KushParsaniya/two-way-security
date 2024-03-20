package dev.kush.securityall.config.security;

import dev.kush.securityall.config.*;
import dev.kush.securityall.config.security.filter.*;
import dev.kush.securityall.config.security.jwt.*;
import lombok.*;
import org.springframework.context.annotation.*;
import org.springframework.security.config.*;
import org.springframework.security.config.annotation.web.builders.*;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.config.annotation.web.configurers.*;
import org.springframework.security.web.*;
import org.springframework.security.web.authentication.*;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final ForbiddenExceptionHandler forbiddenExceptionHandler;

    private final CustomFilter customFilter;

    private final JwtFilter jwtFilter;


//    private final AuthenticationExceptionHandler authenticationExceptionHandler;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .sessionManagement(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        req -> req
                                .requestMatchers("/root").hasRole("ROOT")
                                .requestMatchers("/admin").hasAnyRole("ADMIN","ROOT")
                                .requestMatchers("/user").hasAnyRole("ADMIN","USER","ROOT")
                                .requestMatchers("/home","/sign-in").permitAll()
                                .anyRequest().authenticated()
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(customFilter, JwtFilter.class)
                .httpBasic(Customizer.withDefaults())
                .formLogin(Customizer.withDefaults())
                .exceptionHandling(
                        ex -> ex
                                .accessDeniedHandler(forbiddenExceptionHandler)
//                                .authenticationEntryPoint(authenticationExceptionHandler)
                                );
        return http.build();
    }



}
