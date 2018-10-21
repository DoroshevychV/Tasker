package com.tasker.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.IOException;

@Component
public class AuthenticationTokenFilter extends UsernamePasswordAuthenticationFilter {

    @Value("${lgs.token.header}")
    private String tokenHeader;

    @Autowired
    private TokenUtils tokenUtils;

    @Qualifier("personSI")
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;


        String tkn = "";
        Cookie[] cookies = httpRequest.getCookies();
        if (cookies != null) {
            for (Cookie cook : cookies) {
                if (cook.getName().equals("A-Token")) {
                    tkn = cook.getValue();
                    break;
                }
            }
        }


        if (tkn.length() > 0) {

            String username = this.tokenUtils.getUsernameFromToken(tkn);
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
                if (this.tokenUtils.validateToken(tkn, userDetails)) {
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpRequest));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } else {
                    Cookie cookie = new Cookie(tokenHeader, "");
                    cookie.setMaxAge(0);

                    HttpServletResponse httpServletResponse = (HttpServletResponse) response;
                    httpServletResponse.addCookie(cookie);

                }
            }
        }
        chain.doFilter(request, response);
    }


}
