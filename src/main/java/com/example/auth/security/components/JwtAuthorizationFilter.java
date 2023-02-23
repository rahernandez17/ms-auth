package com.example.auth.security.components;

import com.example.auth.security.models.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.PostConstruct;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    @Value("${jwt.header}")
    private String jwtHeader;

    @Value("${jwt.prefix}")
    private String jwtPrefix;

    private String prefix;

    private final TokenUtilComponent tokenUtilComponent;

    private final UserDetailsService userDetailService;

    @Autowired
    public JwtAuthorizationFilter(
            TokenUtilComponent tokenUtilComponent,
            UserDetailsService userDetailService) {
        this.tokenUtilComponent = tokenUtilComponent;
        this.userDetailService = userDetailService;
    }

    @PostConstruct
    public void init() {
        prefix = String.format("%s ", jwtPrefix);
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        final String authHeader = request.getHeader(jwtHeader);
        final String jwt;
        final String userUsername;

        if (Objects.isNull(authHeader) || !authHeader.startsWith(prefix)) {
            filterChain.doFilter(request, response);
            return;
        }

        jwt = authHeader.replace(prefix, "");

        if (tokenUtilComponent.isTokenExpired(jwt)) {
            filterChain.doFilter(request, response);
            return;
        }

        userUsername = tokenUtilComponent.extractUsername(jwt);
        if (Objects.nonNull(userUsername) && Objects.isNull(SecurityContextHolder.getContext().getAuthentication())) {
            UserDetailsImpl userDetails = (UserDetailsImpl) userDetailService.loadUserByUsername(userUsername);
            if (tokenUtilComponent.isTokenValid(jwt, userDetails.getUsername())) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}
