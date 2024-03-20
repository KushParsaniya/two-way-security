package dev.kush.securityall.service;

import dev.kush.securityall.config.security.jwt.*;
import dev.kush.securityall.dto.*;
import dev.kush.securityall.model.*;
import lombok.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.*;

@Service
@RequiredArgsConstructor
public class UserService {

    private final AuthenticationManager authenticationManager;

    private final SecuredUserService securedUserService;
    private final JwtService jwtService;

    public String signIn(SignInDto signInDto,String header) {

        if (header != null) {
            try {
            authenticationManager.authenticate(new CustomAuthenticationToken(false,header));
            } catch (AuthenticationException ex) {
                throw new BadCredentialsException("Invalid Credentials.");
            }

            UserDetails userDetails = securedUserService.loadUserByUsername("root@test.com");

            return jwtService.generateToken(userDetails);
        }

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    signInDto.username(), signInDto.password()
            ));
        } catch (AuthenticationException ex) {
            throw new BadCredentialsException("Invalid Credentials.");
        }
        UserDetails userDetails = securedUserService.
                loadUserByUsername(signInDto.username());

        return jwtService.generateToken(userDetails);

    }
}
