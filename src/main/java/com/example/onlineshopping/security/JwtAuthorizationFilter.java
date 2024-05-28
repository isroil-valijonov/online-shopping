package com.example.onlineshopping.security;

import com.example.onlineshopping.common.exceptions.CustomException;
import com.example.onlineshopping.common.exceptions.TokenException;
import com.example.onlineshopping.user.entity.User;
import com.example.onlineshopping.user.repository.UserRepository;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.persistence.EntityNotFoundException;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.Token;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.csrf.InvalidCsrfTokenException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.security.SignatureException;
import java.util.Base64;
import java.util.Collections;

@Component
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String bearer = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (bearer == null || !bearer.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = bearer.substring(7);
        try {
            validateTokenFormat(token);

            Claims claims = jwtService.claims(token);
            String phoneNumber = claims.getSubject();
            User user = userRepository
                    .findByPhoneNumber(phoneNumber)
                    .orElseThrow(() -> new TokenException("Token is not correct", HttpStatus.BAD_REQUEST.value()));
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(user, null, user.getRole().getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        } catch (InvalidCsrfTokenException | MalformedJwtException | TokenException e) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            response.getWriter().write(e.getMessage());
            return;
        } catch (JwtException e) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write("Unauthorized: Invalid JWT token");
            return;
        }
        filterChain.doFilter(request, response) ;
    }

    private void validateTokenFormat(String token) {
        String[] parts = token.split("\\.");
        if (parts.length != 3) {
            throw new TokenException("Invalid JWT token format", HttpStatus.BAD_REQUEST.value());
        }

        try {
            Base64.getUrlDecoder().decode(parts[0]);
            Base64.getUrlDecoder().decode(parts[1]);
        } catch (IllegalArgumentException e) {
            throw new TokenException("Invalid JWT token format", HttpStatus.BAD_REQUEST.value());
        }
    }
}
