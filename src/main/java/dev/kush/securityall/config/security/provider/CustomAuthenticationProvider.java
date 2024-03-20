package dev.kush.securityall.config.security.provider;

import dev.kush.securityall.model.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.*;
import org.springframework.stereotype.*;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        CustomAuthenticationToken token = (CustomAuthenticationToken) authentication;

        if (token.getKey().equals("kush")) {
            return new CustomAuthenticationToken(true, null);
        }
        throw new BadCredentialsException("Invalid Credentials");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return CustomAuthenticationToken.class.equals(authentication);
    }
}
