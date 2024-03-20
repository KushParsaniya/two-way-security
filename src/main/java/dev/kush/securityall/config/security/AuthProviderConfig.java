package dev.kush.securityall.config.security;

import dev.kush.securityall.service.*;
import lombok.*;
import org.springframework.context.annotation.*;
import org.springframework.security.authentication.*;
import org.springframework.security.authentication.dao.*;
import org.springframework.security.crypto.password.*;

@Configuration
@RequiredArgsConstructor
public class AuthProviderConfig {

    private final PasswordEncoder passwordEncoder;

    private final SecuredUserService securedUserService;

    @Bean
    AuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(securedUserService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        return daoAuthenticationProvider;
    }
}
