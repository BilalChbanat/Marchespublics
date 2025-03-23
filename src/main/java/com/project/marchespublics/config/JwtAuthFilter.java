package com.project.marchespublics.config;

import com.project.marchespublics.model.User;
import com.project.marchespublics.service.interfaces.auth.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final HandlerExceptionResolver handlerExceptionResolver;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String path = request.getServletPath();

        if (shouldSkipAuth(path)) {
            filterChain.doFilter(request, response);
            return;
        }

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            final String jwt = authHeader.substring(7);
            final String userEmail = jwtService.extractEmail(jwt);

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (userEmail != null && authentication == null) {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);

                if (jwtService.isTokenValid(jwt, userDetails)) {
                    Long id = null;
                    List<GrantedAuthority> authorities = new ArrayList<>();

                    if (userDetails instanceof User) {
                        User user = (User) userDetails;
                        id = user.getId();
                        if (user.getRole() != null) {
                            authorities.add(new SimpleGrantedAuthority(user.getRole().name()));
                        }
                    }

                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            id,
                            authorities
                    );

                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }

            filterChain.doFilter(request, response);
        } catch (Exception exception) {
            handlerExceptionResolver.resolveException(request, response, null, exception);
        }
    }

    private boolean shouldSkipAuth(String path) {
        return path.startsWith("/auth/") ||
                path.startsWith("/categories/") ||
                path.startsWith("/companies/user/") ||
                path.startsWith("/companies/") ||
                path.startsWith("/departments/") ||
                path.startsWith("/departments/user/") ||
                path.startsWith("/users/") ||
                path.startsWith("/pubs/") ||
                path.startsWith("/applications/") ||
                path.startsWith("/files/") ||
                path.startsWith("/applications/company/") ||
                path.startsWith("/applications/publication/") ||
                path.startsWith("/applications/**/status") ||
                path.startsWith("/applications/check") ||
                path.startsWith("/pubs/user/") ||
                path.startsWith("/categories") ||
                path.startsWith("/companies/user") ||
                path.startsWith("/companies") ||
                path.startsWith("/departments") ||
                path.startsWith("/users") ||
                path.startsWith("/pubs") ||
                path.startsWith("/departments/user") ||
                path.startsWith("/applications") ||
                path.startsWith("/files") ||
                path.startsWith("/pubs/user") ||
                path.startsWith("/pubs/department/");
    }
}