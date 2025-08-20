package com.dropwinsystem.app.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dropwinsystem.app.domain.Member;

import jakarta.servlet.http.HttpSession; 
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class AuthController {

    @GetMapping("/me")
    public ResponseEntity<Map<String, Object>> getCurrentUser(HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        Member member = (Member) session.getAttribute("loginUser");
        System.out.println("AuthController: /api/user/me 호출됨. 세션 ID: " + session.getId());
        System.out.println("AuthController: 세션 'loginUser' 값: " + (member != null ? member.getName() : "null (로그인되지 않음)"));

        if (member != null) {
            response.put("loggedIn", true);
            response.put("username", member.getName());
            response.put("userId", member.getId());
            System.out.println("AuthController: 응답: 로그인된 사용자 이름: " + member.getName() + ", ID: " + member.getId());
        } else {
            response.put("loggedIn", false);
            response.put("username", "익명");
            response.put("userId", null);
            System.out.println("AuthController: 응답: 로그인되지 않은 사용자.");
        }
        return ResponseEntity.ok(response);
    }
}