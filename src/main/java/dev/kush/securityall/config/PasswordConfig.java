package dev.kush.securityall.config;

import org.springframework.context.annotation.*;
import org.springframework.security.crypto.password.*;

@Configuration
public class PasswordConfig {

    @Bean
    PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
