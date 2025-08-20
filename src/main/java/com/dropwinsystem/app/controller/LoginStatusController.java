package com.dropwinsystem.app.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class LoginStatusController {

    private static final Logger logger = LoggerFactory.getLogger(LoginStatusController.class);

    @GetMapping("/api/check-login-status")
    public Map<String, Object> checkLoginStatus() {
        Map<String, Object> response = new HashMap<>();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        logger.info("--- /api/check-login-status 호출됨 ---");
        if (authentication == null) {
            logger.warn("Authentication object is null.");
        } else {
            logger.info("Authentication Principal: {}", authentication.getPrincipal());
            logger.info("Authentication Authenticated: {}", authentication.isAuthenticated());
            logger.info("Authentication Name (User ID): {}", authentication.getName());
            logger.info("Authentication Class: {}", authentication.getClass().getName());
        }


        if (authentication != null && authentication.isAuthenticated() &&
            !(authentication instanceof org.springframework.security.authentication.AnonymousAuthenticationToken)) {

            response.put("isLoggedIn", true);
            response.put("userId", authentication.getName());
            logger.info("User is logged in. userId: {}", authentication.getName());
        } else {

            response.put("isLoggedIn", false);
            logger.info("User is NOT logged in.");
        }
        logger.info("--- /api/check-login-status 응답: {} ---", response);
        return response;
    }
}