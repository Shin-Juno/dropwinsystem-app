package com.dropwinsystem.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dropwinsystem.app.domain.Item;
import com.dropwinsystem.app.domain.ItemDetail;
import com.dropwinsystem.app.domain.Member;
import com.dropwinsystem.app.mapper.ItemMapper;
import com.dropwinsystem.app.service.BidService;
import com.dropwinsystem.app.mapper.ItemDetailMapper;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Service
public class ItemService {

    private static final Logger logger = LoggerFactory.getLogger(ItemService.class);

    private final ItemMapper itemMapper;
    private final BidService bidService;
    private final MemberService memberService;
    private final ItemDetailMapper itemDetailMapper;

    @Autowired
    public ItemService(ItemMapper itemMapper, BidService bidService, MemberService memberService, ItemDetailMapper itemDetailMapper) {
        this.itemMapper = itemMapper;
        this.bidService = bidService;
        this.memberService = memberService;
        this.itemDetailMapper = itemDetailMapper;
    }

    @Transactional
    public void registerItem(Item item) {
        item.setStatus("pending");
        item.setPaid(false);
        itemMapper.insertItem(item);

        ItemDetail itemDetail = new ItemDetail();
        itemDetail.setItemId(item.getId());
        itemDetail.setSize(item.getSize());
        itemDetail.setReleaseYear(item.getReleaseYear());
        itemDetail.setCountry(item.getCountry());
        itemDetail.setMaterial(item.getMaterial());
        itemDetail.setItemCondition(item.getItemCondition());
        itemDetail.setColor(item.getColor());
        itemDetail.setBrand(item.getBrand());
        itemDetail.setFeatures(item.getFeatures());

        itemDetailMapper.insertItemDetail(itemDetail);
    }

    public void endAuction(int itemId) {
        // ItemMapper의 updateEndTime은 @Param으로 id와 endTime을 직접 받습니다.
        itemMapper.updateEndTime(itemId, LocalDateTime.now());
    }
    
    public Item getItemById(int id) {
        Item item = itemMapper.selectItemById(id);
        if (item != null && item.getSellerId() != null && (item.getSellerName() == null || item.getSellerName().isEmpty())) {
            if (memberService != null) {
                Member member = memberService.getMember(item.getSellerId());
                if (member != null) {
                    item.setSellerName(member.getName());
                }
            }
        }
        return item;
    }
    @Transactional
    public boolean placeBid(int itemId, int newPrice) {
        // 1. SELECT ... FOR UPDATE를 사용해 물품 정보를 조회하고 락을 획득
        Item item = itemMapper.selectItemForUpdateById(itemId);

        // 2. 락을 획득한 후, 입찰 유효성을 다시 확인
        if (item == null) {
            logger.error("입찰하려는 물품이 존재하지 않습니다. itemId: {}", itemId);
            return false;
        }

        // 입찰 마감 시간이 지났는지 확인
        if (LocalDateTime.now().isAfter(item.getEndTime())) {
            logger.warn("입찰 마감 시간이 지난 물품입니다. itemId: {}", itemId);
            return false;
        }

        // 새로운 입찰가가 현재 가격보다 낮은지 확인
        if (newPrice <= item.getCurrentPrice()) {
            logger.warn("새로운 입찰가가 현재 가격보다 낮거나 같습니다. itemId: {}, newPrice: {}, currentPrice: {}",
                    itemId, newPrice, item.getCurrentPrice());
            return false;
        }

        // 3. 모든 검증이 통과하면 가격과 입찰 횟수 업데이트
        try {
            Map<String, Object> priceParams = new HashMap<>();
            priceParams.put("itemId", itemId);
            priceParams.put("newPrice", newPrice);
            itemMapper.updateCurrentPrice(priceParams);

            itemMapper.incrementBidCount(itemId);
            logger.info("물품 입찰 성공: itemId = {}, newPrice = {}", itemId, newPrice);
            return true;
        } catch (Exception e) {
            // 업데이트 중 예외 발생 시 트랜잭션이 롤백됩니다.
            logger.error("입찰 업데이트 중 오류 발생", e);
            throw new RuntimeException("입찰 업데이트 실패", e);
        }
    }
    
    public Integer getMaxBidPriceForItemId(int itemId) {
        return bidService.getMaxBidPriceForItemId(itemId);
    }

    @Transactional
    public void updateCurrentPrice(int itemId, int newPrice) {
        Map<String, Object> params = new HashMap<>();
        params.put("itemId", itemId);
        params.put("newPrice", newPrice);
        itemMapper.updateCurrentPrice(params);
    }

    @Transactional
    public void incrementBidCount(int itemId) {
        itemMapper.incrementBidCount(itemId);
    }

    public List<Item> getPendingItems() {
        List<Item> items = itemMapper.selectPendingItems();
        if (items != null) {
            for (Item item : items) {
                if (item != null && item.getSellerId() != null && (item.getSellerName() == null || item.getSellerName().isEmpty())) {
                    if (memberService != null) {
                        Member member = memberService.getMember(item.getSellerId());
                        if (member != null) {
                            item.setSellerName(member.getName());
                        }
                    }
                }
            }
        }
        return items;
    }

    @Transactional
    public void updateItemStatus(int itemId, String action) {
        String newStatus;
        if ("approve".equals(action)) {
            newStatus = "approved";
        } else if ("reject".equals(action)) {
            newStatus = "rejected";
        } else {
            throw new IllegalArgumentException("유효하지 않은 액션입니다: " + action);
        }
        Map<String, Object> params = new HashMap<>();
        params.put("id", itemId);
        params.put("status", newStatus);
        itemMapper.updateItemStatus(params);
    }
    
    @Transactional
    public void updateItemPaymentStatus(int itemId, boolean isPaid) {
        Map<String, Object> params = new HashMap<>();
        params.put("itemId", itemId);
        params.put("isPaid", isPaid);
        itemMapper.updateItemPaymentStatus(params);
    }
    
    @Transactional
    public void processAllExpiredAuctions() {
        List<Item> expiredItems = itemMapper.findExpiredItemsWithPendingStatus();

        if (expiredItems != null && !expiredItems.isEmpty()) {
            for (Item item : expiredItems) {
                bidService.processExpiredAuction(item.getId());
                
                Map<String, Object> params = new HashMap<>();
                params.put("id", item.getId());
                params.put("status", "경매 종료");
                itemMapper.updateItemStatus(params);
            }
        }
    }

    public List<Item> getAllItems(String category, String searchQuery, String filter) {
        Map<String, Object> params = new HashMap<>();
        params.put("category", category);
        params.put("searchQuery", searchQuery);
        params.put("filter", filter);

        List<Item> items = itemMapper.selectAllItems(params);
        if (items != null) {
            for (Item item : items) {
                if (item != null && item.getSellerId() != null && (item.getSellerName() == null || item.getSellerName().isEmpty())) {
                    if (memberService != null) {
                        Member member = memberService.getMember(item.getSellerId());
                        if (member != null) {
                            item.setSellerName(member.getName());
                        }
                    }
                }
            }
            return items;
        }
        return items;
    }
    public List<Item> getBestItems() {
        List<Item> items = itemMapper.selectBestItems();
        if (items != null) {
            for (Item item : items) {
                if (item != null && item.getSellerId() != null && (item.getSellerName() == null || item.getSellerName().isEmpty())) {
                    if (memberService != null) {
                        Member member = memberService.getMember(item.getSellerId());
                        if (member != null) {
                            item.setSellerName(member.getName());
                        }
                    }
                }
            }
            return items;
        }
        return items;
    }
}