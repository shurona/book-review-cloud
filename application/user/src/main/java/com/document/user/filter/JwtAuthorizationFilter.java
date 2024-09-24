package com.document.user.filter;

import com.document.user.security.JwtUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final JwtUtils jwtUtils;
    private final UserDetailsService userDetailsService;

    public JwtAuthorizationFilter(JwtUtils jwtUtils, UserDetailsService userDetailsService) {
        this.jwtUtils = jwtUtils;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String jwtToken = jwtUtils.getJwtTokenFromHeader(request);
        if (StringUtils.hasText(jwtToken)) {
            boolean isValid = jwtUtils.checkValidJwtToken(jwtToken);
            if (!isValid) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }

            Claims jwtBodyInfo = jwtUtils.getBodyFromJwt(jwtToken);

            try {
                putAuthentication(jwtBodyInfo.getSubject());
            } catch (Exception e) {
                logger.error("Context Holder 생성 중 에러 {}", e.getMessage());
                return;
            }
        }
//        else if (resrequest.getRequestURI().startsWith("/errors")) {

//            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//            return;
//        }
//        else {
//            // token이 없으면 401
//            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//            return;
//        }

        //
        filterChain.doFilter(request, response);
    }

    // 생성된 인증 객체를 Security Context Holder 넣어준다.
    public void putAuthentication(String username) {
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(generateAuthentication(username));
        SecurityContextHolder.setContext(context);
    }

    // 인증 객체 생성
    private Authentication generateAuthentication(String username) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities()
        );
    }
}
