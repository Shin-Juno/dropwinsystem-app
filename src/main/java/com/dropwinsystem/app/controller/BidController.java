package com.dropwinsystem.app.controller;

import com.dropwinsystem.app.domain.Bid;
import com.dropwinsystem.app.domain.Item;
import com.dropwinsystem.app.domain.Member;
import com.dropwinsystem.app.service.BidService;
import com.dropwinsystem.app.service.ItemService;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@Controller
public class BidController {

    @Autowired
    private BidService bidService;

    @Autowired
    private ItemService itemService;

    @PostMapping("/bids/place")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> placeBid(@RequestBody Map<String, Object> payload, HttpSession session) {
        Member loggedInMember = (Member) session.getAttribute("loginUser");

        if (loggedInMember == null || loggedInMember.getId() == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("success", false, "message", "로그인이 필요합니다."));
        }
        String userId = loggedInMember.getId();

        Map<String, Object> response = new HashMap<>();

        try {
            int itemId = (Integer) payload.get("itemId");
            int bidPrice = (Integer) payload.get("bidPrice");

            Item item = itemService.getItemById(itemId);

            if (item == null) {
                response.put("success", false);
                response.put("message", "존재하지 않는 상품입니다.");
                return ResponseEntity.badRequest().body(response);
            }

            if (item.getEndTime().isBefore(LocalDateTime.now())) {
                response.put("success", false);
                response.put("message", "경매가 이미 종료되었습니다.");
                return ResponseEntity.badRequest().body(response);
            }

            int currentHighestPrice = (item.getCurrentPrice() != null) ? item.getCurrentPrice() : item.getStartPrice();

            // ⭐⭐ 즉시구매 로직 시작 ⭐⭐
            // 입찰 금액이 즉시구매가와 같고, 즉시구매가가 설정되어 있다면 즉시구매 처리
            if (item.getBuyNowPrice() != null && bidPrice == item.getBuyNowPrice()) {
                // 입찰 정보 생성
                Bid bid = new Bid();
                bid.setItemId(itemId);
                bid.setUserId(userId);
                bid.setBidPrice(bidPrice);
                bidService.placeBid(bid);

                // 아이템 정보 업데이트
                itemService.updateCurrentPrice(itemId, bidPrice);
                itemService.incrementBidCount(itemId);

                // 경매 즉시 종료 및 입찰 상태 '낙찰'로 변경
                itemService.endAuction(itemId);
                bidService.updateBidStatus(bid.getId(), "낙찰"); // 방금 생성된 Bid의 ID를 사용

                response.put("success", true);
                response.put("message", "즉시구매가로 낙찰되었습니다! 결제 페이지로 이동합니다.");
                response.put("itemId", itemId);
                response.put("bidPrice", bidPrice);
                return ResponseEntity.ok(response);
            }
            // ⭐⭐ 즉시구매 로직 끝 ⭐⭐
            
            // 일반 입찰 로직
            if (bidPrice <= currentHighestPrice) {
                response.put("success", false);
                response.put("message", "입찰 금액은 현재 최고 입찰가(" + currentHighestPrice + "원)보다 높아야 합니다.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
            
            // 입찰 성공 시 BidService에 입찰 정보 저장
            Bid bid = new Bid();
            bid.setItemId(itemId);
            bid.setUserId(userId);
            bid.setBidPrice(bidPrice);
            bidService.placeBid(bid);

            // ItemService에 입찰 정보 업데이트
            itemService.updateCurrentPrice(itemId, bidPrice);
            itemService.incrementBidCount(itemId);

            response.put("success", true);
            response.put("message", "입찰이 성공적으로 접수되었습니다.");
            response.put("itemId", itemId);
            response.put("bidPrice", bidPrice);
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            System.err.println("입찰 처리 중 오류 발생: " + e.getMessage());
            e.printStackTrace();
            response.put("success", false);
            response.put("message", "입찰 처리 중 오류가 발생했습니다. (" + e.getMessage() + ")");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/reservation")
    public String myBids(HttpSession session, Model model) {

        itemService.processAllExpiredAuctions(); 

        Member loggedInMember = (Member) session.getAttribute("loginUser");

        if (loggedInMember == null || loggedInMember.getId() == null) {
            return "redirect:/loginForm";
        }
        String userId = loggedInMember.getId();

        List<Bid> userBids = bidService.getUserBids(userId);

        Map<Integer, Bid> highestBidsByItem = new HashMap<>();
        Map<Integer, Boolean> isWinningMap = new HashMap<>();
        Map<Integer, Boolean> isAuctionEndedMap = new HashMap<>();

        if (userBids != null) {
            for (Bid bid : userBids) {
                int itemId = bid.getItemId();

                Item item = itemService.getItemById(itemId);
                if (item != null) {
                    isAuctionEndedMap.put(itemId, item.getEndTime().isBefore(LocalDateTime.now()));
                }

                Bid winningBid = bidService.getWinningBidByItemId(itemId);
                
                boolean isWinningBid = (winningBid != null && winningBid.getUserId().equals(userId) && winningBid.getBidPrice() == bid.getBidPrice());

                isWinningMap.put(bid.getId(), isWinningBid);
                highestBidsByItem.put(itemId, winningBid);
            }
        }

        model.addAttribute("userBids", userBids);
        model.addAttribute("highestBidsByItem", highestBidsByItem);
        model.addAttribute("isWinningMap", isWinningMap);
        model.addAttribute("isAuctionEndedMap", isAuctionEndedMap);

        return "views/reservation";
    }

    @GetMapping("/bids/history/{itemId}")
    @ResponseBody
    public ResponseEntity<List<Bid>> getBidHistory(@PathVariable("itemId") int itemId) {
        List<Bid> bidHistory = bidService.getBidsByItemId(itemId);
        return ResponseEntity.ok(bidHistory);
    }
}
