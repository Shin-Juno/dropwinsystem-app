package com.dropwinsystem.app.controller;

import com.dropwinsystem.app.domain.Item;
import com.dropwinsystem.app.domain.Member;
import com.dropwinsystem.app.service.ItemService;
import com.dropwinsystem.app.service.MemberService;

import jakarta.servlet.http.HttpSession;

import com.dropwinsystem.app.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class ItemDetailController {

    private static final Logger logger = LoggerFactory.getLogger(ItemDetailController.class);

    @Autowired
    private ItemService itemService;

    @Autowired(required = false)
    private MemberService memberService;

    @Autowired
    private FavoriteService favoriteService;

    @GetMapping("/item/{itemId}")
    public String itemDetail(@PathVariable("itemId") int itemId, Model model, HttpSession session) {
        logger.info("--- ItemDetailController: itemDetail called ---");
        logger.info("  Received Item ID: {}", itemId);

        Item item = itemService.getItemById(itemId);

        if (item != null) {
            model.addAttribute("item", item);

            List<String> allImagePaths = item.getImagePathList();
            String mainImgUrl;

            if (allImagePaths != null && !allImagePaths.isEmpty()) {
                mainImgUrl = "/auction_uploads/" + allImagePaths.get(0);
            } else {

                mainImgUrl = "/img/no_image_available.png"; 
            }
            logger.debug("  Constructed main image URL: {}", mainImgUrl);
            model.addAttribute("mainImageUrl", mainImgUrl);

            List<String> otherImageUrls = new ArrayList<>();
            if (allImagePaths != null && allImagePaths.size() > 1) {
                for (int i = 1; i < allImagePaths.size(); i++) {
                    otherImageUrls.add("/auction_uploads/" + allImagePaths.get(i));
                }
            }
            logger.debug("  Other image URLs for Thymeleaf: {}", otherImageUrls);
            model.addAttribute("otherImageUrls", otherImageUrls);

            String sellerNameToDisplay;
            if (item.getSellerName() != null) {
                sellerNameToDisplay = item.getSellerName();
            } else if (item.getSellerId() != null && memberService != null) {
                Member seller = memberService.getMember(item.getSellerId());
                if (seller != null) {
                    sellerNameToDisplay = seller.getName();
                } else {
                    sellerNameToDisplay = "알 수 없음";
                }
            } else {
                sellerNameToDisplay = "알 수 없음";
            }
            logger.debug("  Seller name to display: {}", sellerNameToDisplay);
            model.addAttribute("sellerName", sellerNameToDisplay);
            
            Member member = (Member) session.getAttribute("loginUser");
            boolean isFavorited = false;
            if (member != null) {
                isFavorited = favoriteService.isItemFavorited(member.getId(), itemId);
            }
            model.addAttribute("isFavorited", isFavorited);

            List<Item> tempRelatedProducts = itemService.getAllItems(item.getCategory(), "", "");
            tempRelatedProducts.removeIf(p -> p.getId() == itemId);
            
            List<Item> finalRelatedProducts = new ArrayList<>();
            int count = 0;
            for (Item relatedItem : tempRelatedProducts) {
                if (count >= 3) break;

                finalRelatedProducts.add(relatedItem);
                count++;
            }
            logger.debug("  Number of related products: {}", finalRelatedProducts.size());
            model.addAttribute("relatedProducts", finalRelatedProducts);

            logger.info("  Item detail data prepared for item ID: {}", itemId);
            logger.info("--------------------------------------------------");

            return "views/itemDetail";
        } else {
            logger.warn("  WARN: Item with ID {} not found. Redirecting to /error.", itemId);
            return "redirect:/error";
        }
    }
}