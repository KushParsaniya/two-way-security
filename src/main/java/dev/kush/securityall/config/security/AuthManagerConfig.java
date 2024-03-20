package dev.kush.securityall.config.security;

import dev.kush.securityall.config.security.provider.*;
import lombok.*;
import org.springframework.context.annotation.*;
import org.springframework.security.authentication.*;

import java.util.*;

@Configuration
@RequiredArgsConstructor
public class AuthManagerConfig {


    private final AuthenticationProvider daoAuthenticationProvider;
    private final CustomAuthenticationProvider customAuthenticationProvider;

    @Bean
    AuthenticationManager authenticationManager() throws Exception {
//        return configuration.getAuthenticationManager();
        return new ProviderManager(List.of(customAuthenticationProvider,daoAuthenticationProvider));
    }
}
