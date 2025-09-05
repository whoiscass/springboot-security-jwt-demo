package com.example.demo.config.security;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import io.jsonwebtoken.Claims;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.Date;

/**
 * JwtAuthenticationFilter is a custom Spring Security filter that intercepts HTTP requests
 * to validate and process JWT-based authentication.
 *
 * This filter ensures that:
 * - Requests to authentication endpoints are ignored.
 * - Valid JWTs are extracted and parsed from the Authorization header.
 * - Expired or invalid tokens are ignored.
 * - Upon successful validation, the user is authenticated in the SecurityContext.
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    /**
     * Filters incoming HTTP requests and performs JWT authentication if applicable.
     *
     * @param request the incoming HttpServletRequest
     * @param response the HttpServletResponse
     * @param filterChain the filter chain to pass control to the next filter
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        if (request.getServletPath().contains("/auth") ||
                request.getServletPath().contains("/v3") ||
                request.getServletPath().contains("/swagger-ui")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String jwt = authHeader.substring(7);
        final Claims claims = jwtService.extractUsername(jwt);
        if (claims == null || claims.getSubject() == null) {
            filterChain.doFilter(request, response);
            return;
        }

        if (claims.getExpiration().before(new Date(System.currentTimeMillis()))) {
            filterChain.doFilter(request, response);
            return;
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(claims.getSubject());
        final UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);
    }
}
