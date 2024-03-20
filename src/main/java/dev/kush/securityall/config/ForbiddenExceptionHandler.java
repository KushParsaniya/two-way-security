package dev.kush.securityall.config;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.springframework.security.access.*;
import org.springframework.security.web.access.*;
import org.springframework.stereotype.*;

import java.io.*;
import java.time.*;

@Component
public class ForbiddenExceptionHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {


        response.setStatus(HttpServletResponse.SC_FORBIDDEN);

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
                HttpServletResponse.SC_FORBIDDEN,
                "You are not authorize to access this resource."
        );

        response.getWriter().write(errorMessage);
    }
}
