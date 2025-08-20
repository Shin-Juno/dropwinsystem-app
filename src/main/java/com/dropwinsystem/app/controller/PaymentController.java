package com.dropwinsystem.app.controller;

import com.dropwinsystem.app.domain.Item;
import com.dropwinsystem.app.domain.Member;
import com.dropwinsystem.app.domain.Bid;
import com.dropwinsystem.app.service.ItemService;
import com.dropwinsystem.app.service.BidService;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Controller
public class PaymentController {

    private final ItemService itemService;
    private final BidService bidService;

    @Autowired
    public PaymentController(ItemService itemService, BidService bidService) {
        this.itemService = itemService;
        this.bidService = bidService;
    }

    @GetMapping("/payment/{itemId}")
    public String paymentPage(@PathVariable("itemId") int itemId,
                              @RequestParam("bidPrice") int bidPrice,
                              Model model, HttpSession session) {
        Item item = itemService.getItemById(itemId);

        if (item != null) {
            model.addAttribute("item", item);
            model.addAttribute("paymentPrice", bidPrice);
            System.out.println("결제 페이지 - 물품명: " + item.getName() + ", 낙찰 가격: " + bidPrice);
        } else {
            model.addAttribute("errorMessage", "해당 물품을 찾을 수 없습니다.");
        }

        Member loginUser = (Member) session.getAttribute("loginUser");
        if (loginUser != null) {
            model.addAttribute("name", loginUser.getName());

            String mobile = loginUser.getMobile();
            if (mobile != null && mobile.contains("-")) {
                String[] parts = mobile.split("-");
                if (parts.length == 3) {
                    model.addAttribute("mobilePrefix", parts[0]);
                    model.addAttribute("mobileMiddle", parts[1]);
                    model.addAttribute("mobileLast", parts[2]);
                }
            }

            model.addAttribute("loginUser", loginUser);
        }
        return "payment/payment";
    }

    @PostMapping("/paymentSuccess")
    @Transactional
    public ResponseEntity<?> paymentSuccess(@RequestBody Map<String, Object> requestBody, HttpSession session) { // ⭐⭐ HttpSession 매개변수 추가 ⭐⭐
        try {
            String impUid = (String) requestBody.get("imp_uid");
            String merchantUid = (String) requestBody.get("merchant_uid");
            int itemId = (Integer) requestBody.get("itemId");
            String userId = (String) requestBody.get("userId");

            String itemName = (String) requestBody.get("itemName");

            Integer paymentAmount = (Integer) requestBody.get("paymentAmount"); 
            String paymentMethod = (String) requestBody.get("paymentMethod");
            String address = (String) requestBody.get("address");

            session.setAttribute("itemName", itemName);
            session.setAttribute("paymentAmount", paymentAmount);
            session.setAttribute("paymentMethod", paymentMethod);
            session.setAttribute("address", address);
            
            System.out.println("결제 성공 콜백 수신: imp_uid=" + impUid + ", merchant_uid=" + merchantUid);
            System.out.println("물품 ID: " + itemId + ", 사용자 ID: " + userId);
            System.out.println("세션 저장 정보: 상품명=" + itemName + ", 금액=" + paymentAmount + ", 결제수단=" + paymentMethod + ", 주소=" + address);

            Bid winningBid = bidService.getWinningBidByItemId(itemId);
            
            if (winningBid != null && winningBid.getUserId().equals(userId)) {

                itemService.updateItemPaymentStatus(itemId, true);

                bidService.updateBidToShippingStatus(winningBid.getId()); 
                
                System.out.println("결제 성공 - 물품 및 입찰 상태 업데이트 완료. Item ID: " + itemId + ", Bid ID: " + winningBid.getId());
            } else {
                System.err.println("오류: 낙찰된 입찰을 찾을 수 없거나 결제한 사용자가 낙찰자가 아닙니다. Item ID: " + itemId + ", User ID: " + userId);

                throw new RuntimeException("낙찰 정보 불일치 또는 낙찰된 입찰을 찾을 수 없음."); 
            }

            return ResponseEntity.ok("결제 성공 및 서버 데이터 업데이트 완료.");

        } catch (Exception e) {
            System.err.println("결제 성공 처리 중 오류 발생: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("결제 성공 처리 중 오류 발생", e); 
        }
    }

    @GetMapping("/paymentSuccess")
    public String paymentSuccessPage(HttpSession session, Model model) {
        model.addAttribute("itemName", session.getAttribute("itemName"));
        model.addAttribute("totalPrice", session.getAttribute("paymentAmount"));
        model.addAttribute("paymentMethod", session.getAttribute("paymentMethod"));
        model.addAttribute("address", session.getAttribute("address"));
        return "payment/paymentSuccess";
    }
}
