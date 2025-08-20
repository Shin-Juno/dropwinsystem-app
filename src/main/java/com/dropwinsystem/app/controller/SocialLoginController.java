package com.dropwinsystem.app.controller;

import java.io.IOException;
import java.sql.Timestamp;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dropwinsystem.app.domain.Member;
import com.dropwinsystem.app.domain.NaverUser;
import com.dropwinsystem.app.service.MemberService;
import com.dropwinsystem.app.social.NaverOauth;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class SocialLoginController {

    private final NaverOauth naverOauth;
    private final MemberService memberService;
    private final HttpSession session;

    @GetMapping("/auth")
    public void redirectToNaver(HttpServletResponse response) throws IOException {
        String url = naverOauth.getOauthRedirectURL();
        response.sendRedirect(url);
    }

    @GetMapping("/callback")
    public String handleNaverCallback(@RequestParam("code") String code) {
        String tokenJson = naverOauth.requestAccessToken(code);

        String accessToken;
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode tokenNode = mapper.readTree(tokenJson);
            accessToken = tokenNode.path("access_token").asText();
        } catch (Exception e) {
            throw new RuntimeException("토큰 파싱 실패", e);
        }

        NaverUser naverUser = naverOauth.getUserInfo(accessToken);
        
        String socialId = "naver_" + naverUser.getId();

        Member member = memberService.getMember(socialId);

        if (member == null) {
            member = new Member();
            member.setId(socialId);
            member.setName(naverUser.getName());
            member.setEmail(naverUser.getEmail());
            member.setPass("");
            member.setMobile("");
            member.setRole("user");
            member.setRegDate(new Timestamp(System.currentTimeMillis()));
            member.setEssOption(false);
            member.setUnOption(false);

            memberService.addMember(member);
        }

        session.setAttribute("loginUser", member);
        return "redirect:/home";
    }
}
