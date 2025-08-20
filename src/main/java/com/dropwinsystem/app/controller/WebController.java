package com.dropwinsystem.app.controller;

import java.util.*;

import com.dropwinsystem.app.domain.Item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.dropwinsystem.app.domain.Item;
import com.dropwinsystem.app.service.ItemService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j // 로깅을 위해 사용
public class WebController {
	
	@Autowired
	private ItemService itemService;
	
	@GetMapping("/home")
	public String home(Model model) {
		List<Item> bestItems = itemService.getBestItems();
		model.addAttribute("bestItems", bestItems);
		
		return "views/home";
	}

	@GetMapping("/login")
    public String login() {
        return "views/login";
    }
	
	@GetMapping("/about")
	public String about() {
		return "views/about";
	}
	
	@GetMapping("/guide")
	public String guide() {
		return "views/guide";
	}

	@GetMapping("/itemForm")
	public String itemForm() {
		return "views/itemForm";
	}

	@GetMapping("/pendingList")
	public String pendingList(Model model) {

	    List<Item> pendingItems = itemService.getPendingItems();
	    model.addAttribute("pendingItems", pendingItems);
	    return "admin/pendingList";
	}

	@PostMapping("/pendingList")
	public String updateItemStatus(
	    @RequestParam("itemId") int itemId,
	    @RequestParam("action") String action,
	    RedirectAttributes redirectAttributes
	) {
	    try {
	        itemService.updateItemStatus(itemId, action);
	        redirectAttributes.addFlashAttribute("message", "물품 상태가 성공적으로 업데이트되었습니다.");
	    } catch (IllegalArgumentException e) {
	        redirectAttributes.addFlashAttribute("error", e.getMessage());
	    } catch (Exception e) {
	        redirectAttributes.addFlashAttribute("error", "물품 상태 업데이트 중 오류가 발생했습니다: " + e.getMessage());
	    }
	    return "redirect:/pendingList";
	}

}
