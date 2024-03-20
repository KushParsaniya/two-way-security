package dev.kush.securityall.service;

import dev.kush.securityall.model.*;
import dev.kush.securityall.model.User;
import dev.kush.securityall.repos.*;
import lombok.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.*;

@Service
@RequiredArgsConstructor
public class SecuredUserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User existingUser = userRepository.findUserByEmail(username).orElseThrow(
                () -> new UsernameNotFoundException("user not found")
        );
        return new SecuredUser(existingUser);
    }
}
