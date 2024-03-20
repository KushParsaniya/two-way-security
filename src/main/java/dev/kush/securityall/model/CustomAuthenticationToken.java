package dev.kush.securityall.model;

import lombok.*;
import org.springframework.security.core.*;
import org.springframework.security.core.authority.*;

import java.util.*;

@AllArgsConstructor
@Getter
@Setter
public class CustomAuthenticationToken implements Authentication {
    private boolean isAuth;

    private String key;



    @Override
    public boolean isAuthenticated() {
        return isAuth;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.isAuth = isAuthenticated;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }


    @Override
    public String getName() {
        return null;
    }
}
