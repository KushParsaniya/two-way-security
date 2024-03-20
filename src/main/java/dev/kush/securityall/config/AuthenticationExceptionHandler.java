package dev.kush.securityall.config;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.springframework.security.core.*;
import org.springframework.security.web.*;
import org.springframework.stereotype.*;

import java.io.*;
import java.time.*;

@Component
public class AuthenticationExceptionHandler implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        response.setContentType("application/json");

        String errorMessage = String.format(
                """
                {
                    "timestamp" : "%s",
                    "status" : %d,
                    "message" : "%s"
                }
                """,
                LocalDateTime.now(),
                HttpServletResponse.SC_UNAUTHORIZED,
                "Wrong Credential."
        );

        response.getWriter().write(errorMessage);
    }
}
