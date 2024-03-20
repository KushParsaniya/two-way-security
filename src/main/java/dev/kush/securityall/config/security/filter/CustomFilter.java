package dev.kush.securityall.config.security.filter;

import dev.kush.securityall.model.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.springframework.context.annotation.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.*;
import org.springframework.security.core.context.*;
import org.springframework.stereotype.*;
import org.springframework.web.filter.*;

import java.io.*;

@Component
public class CustomFilter extends OncePerRequestFilter {

    private final AuthenticationManager authenticationManager;

    public CustomFilter(@Lazy AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String header = request.getHeader("x-secret-key");

        CustomAuthenticationToken token = new CustomAuthenticationToken(false,header);

        final Authentication authenticate = authenticationManager.authenticate(token);


        if (authenticate.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(authenticate);
            filterChain.doFilter(request,response);
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        final String header = request.getHeader("x-secret-key");
        return header == null || "null".equals(header) || header.isEmpty();
    }
}
