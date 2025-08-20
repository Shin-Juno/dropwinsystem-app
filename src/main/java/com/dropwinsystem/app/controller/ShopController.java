package com.dropwinsystem.app.controller;

import com.dropwinsystem.app.domain.Item;
import com.dropwinsystem.app.domain.Member;
import com.dropwinsystem.app.service.ItemService;

import jakarta.servlet.http.HttpSession;

import com.dropwinsystem.app.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/shop")
public class ShopController {

    @Autowired
    private ItemService itemService;

    @Autowired
    private FavoriteService favoriteService;

    @GetMapping
    public String shop(
            @RequestParam(value = "category", required = false, defaultValue = "") String category,
            @RequestParam(value = "filter", required = false, defaultValue = "all") String filter,
            @RequestParam(value = "searchQuery", required = false, defaultValue = "") String searchQuery,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "9") int size,
            Model model,
            HttpSession session) {

    	Pageable pageable = PageRequest.of(page, size);

        List<Item> allApprovedItems = itemService.getAllItems(category, searchQuery, filter);

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), allApprovedItems.size());
        
        List<Item> pagedItems;
        if (start > allApprovedItems.size()) {
            pagedItems = new ArrayList<>();
        } else {
            pagedItems = allApprovedItems.subList(start, end);
        }

        // 현재 로그인한 사용자의 즐겨찾기 아이템 ID 목록을 가져와 모델에 추가
        Member member = (Member) session.getAttribute("loginUser");
        List<Integer> favoritedItemIds = new ArrayList<>();
        if (member != null) {
            favoritedItemIds = favoriteService.getFavoriteItemsByUserId(member.getId())
                                            .stream()
                                            .map(Item::getId)
                                            .collect(Collectors.toList());
        }
        model.addAttribute("favoritedItemIds", favoritedItemIds);

        Page<Item> itemsPage = new PageImpl<>(pagedItems, pageable, allApprovedItems.size());

        model.addAttribute("itemsPage", itemsPage);
        model.addAttribute("currentCategory", category);
        model.addAttribute("currentFilter", filter);
        model.addAttribute("currentSearchQuery", searchQuery);

        return "views/shop";
    }
}