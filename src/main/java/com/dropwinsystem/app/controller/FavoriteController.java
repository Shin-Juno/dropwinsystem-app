package com.dropwinsystem.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

import com.dropwinsystem.app.domain.Item;
import com.dropwinsystem.app.domain.Member;
import com.dropwinsystem.app.service.FavoriteService;

import jakarta.servlet.http.HttpSession;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

@Controller
@RequestMapping("/favorites")
public class FavoriteController {

    @Autowired
    private FavoriteService favoriteService;

    @GetMapping("")
    public String getFavorites(HttpSession session, Model model) {
        Member member = (Member) session.getAttribute("loginUser");
        if (member == null) {
            return "redirect:/loginForm";
        }

        List<Item> favoriteItems = favoriteService.getFavoriteItemsByUserId(member.getId());
        System.out.println("FavoriteController: 관심 상품 목록 조회 - " + favoriteItems.size() + "개 발견");
        model.addAttribute("favoriteItems", favoriteItems);
        model.addAttribute("favoriteCount", favoriteItems.size());
        return "views/favorites";
    }

    @PostMapping("/toggle")
    public ResponseEntity<Map<String, String>> toggleFavorite(@RequestParam("itemId") int itemId, HttpSession session) {
        Map<String, String> response = new HashMap<>();
        Member member = (Member) session.getAttribute("loginUser");

        if (member == null) {
            response.put("status", "error");
            response.put("message", "로그인이 필요합니다.");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }

        try {
            boolean isFavorited = favoriteService.isItemFavorited(member.getId(), itemId);
            if (isFavorited) {
                favoriteService.removeFavorite(member.getId(), itemId);
                response.put("status", "removed");
                response.put("message", "관심 상품에서 삭제되었습니다.");
            } else {
                favoriteService.addFavorite(member.getId(), itemId);
                response.put("status", "added");
                response.put("message", "관심 상품에 추가되었습니다.");
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "관심 상품 처리 중 오류가 발생했습니다.");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
