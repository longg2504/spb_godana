package com.godana.security;

import com.godana.service.jwt.JwtService;
import com.godana.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private IUserService userService;


    private String getBearerTokenRequest(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null) {
            if (authHeader.startsWith("Bearer ")) {
                return authHeader.replace("Bearer ", "");
            }
            return authHeader;
        }

        return null;
    }

    private String getCookieValue(HttpServletRequest req) {
        Cookie[] cookies = req.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("JWT")) {
                    return cookie.getValue();
                }
            }
        }

        return null;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        System.out.println("request.getServletPath() ==================");
        System.out.println(request.getServletPath());
        if (request.getServletPath().equals("/api/auth/login")) {
            filterChain.doFilter(request, response);
        }
        else {
            try {
                String bearerToken = getBearerTokenRequest(request);
                String authorizationCookie = getCookieValue(request);

                setAuthentication(request, bearerToken);
                setAuthentication(request, authorizationCookie);

            } catch (Exception e) {
                logger.error("Can NOT set user authentication -> Message: {0}", e);
            }
            filterChain.doFilter(request, response);
        }
    }

    private void setAuthentication(HttpServletRequest request, String authorizationValue) {
        if (authorizationValue != null && jwtService.validateJwtToken(authorizationValue)) {

            String username = jwtService.getUserNameFromJwtToken(authorizationValue);
            UserDetails userDetails = userService.loadUserByUsername(username);

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());

            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
    }

}
