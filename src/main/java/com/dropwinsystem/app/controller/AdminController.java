package com.dropwinsystem.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dropwinsystem.app.domain.Item;
import com.dropwinsystem.app.service.ItemService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private ItemService itemService;
    
    @GetMapping("/pendingList")
    public String showPendingItems(Model model) {
    
        List<Item> pendingItems = itemService.getPendingItems();
    
        model.addAttribute("pendingItems", pendingItems);
    
        return "admin/pendingList";
    }

    @PostMapping("/updateStatus")
    public String updateItemStatus(@RequestParam("itemId") int itemId,
                                   @RequestParam("action") String action,
                                   RedirectAttributes redirectAttributes) {
        try {

            itemService.updateItemStatus(itemId, action);

            redirectAttributes.addFlashAttribute("message", "물품 상태가 성공적으로 업데이트되었습니다.");
        } catch (IllegalArgumentException e) {

            redirectAttributes.addFlashAttribute("error", e.getMessage());
        } catch (Exception e) {

            redirectAttributes.addFlashAttribute("error", "물품 상태 업데이트 중 오류가 발생했습니다: " + e.getMessage());
            e.printStackTrace();
        }

        return "redirect:/admin/pendingList";
    }
}
