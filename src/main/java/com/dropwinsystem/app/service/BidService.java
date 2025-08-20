package com.dropwinsystem.app.service;

import com.dropwinsystem.app.domain.Bid;
import com.dropwinsystem.app.mapper.BidMapper;
import com.dropwinsystem.app.mapper.ItemMapper; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@Service
public class BidService {

    private final BidMapper bidMapper;
    private final ItemMapper itemMapper;

    @Autowired
    public BidService(BidMapper bidMapper, ItemMapper itemMapper) {
        this.bidMapper = bidMapper;
        this.itemMapper = itemMapper;
    }

    @Transactional
    public void placeBid(Bid bid) {
        bid.setBidTime(LocalDateTime.now());
        bid.setStatus("입찰 중");
        bidMapper.insertBid(bid);
    }

    public List<Bid> getUserBids(String userId) {
        return bidMapper.findBidsByUserId(userId);
    }

    public Integer getMaxBidPriceForItemId(int itemId) {
        return bidMapper.findMaxBidPriceByItemId(itemId);
    }

    public List<Bid> getBidsByItemId(int itemId) {
        return bidMapper.findBidsByItemIdOrderByBidTimeDesc(itemId);
    }

    public Bid getWinningBidByItemId(int itemId) {
        return bidMapper.findWinningBidByItemId(itemId);
    }

    @Transactional
    public void updateBidToShippingStatus(int bidId) {
        Map<String, Object> params = new HashMap<>();
        params.put("bidId", bidId);
        params.put("status", "결제완료"); 
        bidMapper.updateBidStatus(params);
    }

    @Transactional
    public void updateBidStatus(int bidId, String newStatus) {
        Map<String, Object> params = new HashMap<>();
        params.put("bidId", bidId);
        params.put("status", newStatus);
        bidMapper.updateBidStatus(params);
    }
    
    @Transactional
    public void processExpiredAuction(int itemId) {

        Bid winningBid = bidMapper.findWinningBidByItemId(itemId);

        if (winningBid != null) {

            bidMapper.updateBidStatus(Map.of("bidId", winningBid.getId(), "status", "낙찰"));

            bidMapper.updateLosingBidsByItemId(itemId, winningBid.getId());

            Map<String, Object> updatePriceParams = new HashMap<>();
            updatePriceParams.put("itemId", itemId);
            updatePriceParams.put("newPrice", winningBid.getBidPrice());
            itemMapper.updateCurrentPrice(updatePriceParams);

        } else {
            Map<String, Object> params = new HashMap<>();
            params.put("id", itemId);
            params.put("status", "유찰");
            itemMapper.updateItemStatus(params);
        }
    }
}
