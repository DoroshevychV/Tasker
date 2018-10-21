/**
 * @description Головний контроллер
 * @author Vadym Doroshevych
 * @email doroshevychv@gmail.com
 * @date 17.10.2018 20:00
 **/
package com.tasker.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class MainController {

    @Value("${lgs.token.header}")
    private String tokenHeader;

    @GetMapping("/")
    public String getIndexPage() {
        return "index";
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String getUserPage() {
        return "user";
    }

    @GetMapping("/user/logout")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String logout(ServletRequest request, ServletResponse response) {
        Cookie cookie = new Cookie(tokenHeader, "");
        cookie.setPath("/");
        cookie.setMaxAge(0);
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.addCookie(cookie);
        return "redirect:/";

    }
}
