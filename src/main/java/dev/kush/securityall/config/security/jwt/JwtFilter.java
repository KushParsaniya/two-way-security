package dev.kush.securityall.config.security.jwt;

import dev.kush.securityall.service.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import lombok.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.context.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.*;
import org.springframework.web.filter.*;

import java.io.*;
import java.time.*;
import java.util.*;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    private final SecuredUserService securedUserService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("x-jwt-token");

        if (header == null || !header.startsWith("Bearer ")) {
            sendErrorMessage("Invalid token format.", HttpServletResponse.SC_FORBIDDEN, response);
            return;
        }

        String token = header.substring(7);
        String usernameFromToken = null;
        try {
            usernameFromToken = jwtService.extractUserName(token);
        } catch (Exception e) {
            sendErrorMessage("Invalid token Signature.", HttpServletResponse.SC_FORBIDDEN, response);
            return;
        }

        if (usernameFromToken != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = securedUserService.loadUserByUsername(usernameFromToken);

            try {
                if (jwtService.isTokenValid(token, userDetails)) {

                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails.getUsername(), null, userDetails.getAuthorities()
                    );
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                    filterChain.doFilter(request, response);
                } else {
                    sendErrorMessage("Invalid token.", HttpServletResponse.SC_FORBIDDEN, response);
                }
            } catch (Exception e) {
                sendErrorMessage("Invalid token.", HttpServletResponse.SC_FORBIDDEN, response);
                return;
            }
        } else {
            sendErrorMessage("Invalid username.", HttpServletResponse.SC_FORBIDDEN, response);
            return;
        }

    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        final String header = request.getHeader("x-secret-key");

        String servletPath = request.getServletPath();
        Set<String> nonFilteredPath = new HashSet<>(List.of("/sign-in"));
        return header != null || nonFilteredPath.contains(servletPath);
    }

    private void sendErrorMessage(String message, int status, HttpServletResponse response) throws IOException {
        String errorMessage = String.format(
                """
                        {
                            "timestamp" : "%s",
                            "status" : %d,
                            "message" : "%s"
                        }
                        """,
                LocalDateTime.now(),
                status,
                message
        );

        response.getWriter().write(errorMessage);
    }
}
