package com.dropwinsystem.app.mapper;

import com.dropwinsystem.app.domain.Bid;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map; // Map을 파라미터로 받을 때 필요

@Mapper
public interface BidMapper {

    void insertBid(Bid bid);

    List<Bid> findBidsByUserId(@Param("userId") String userId);

    Integer findMaxBidPriceByItemId(int itemId);

    List<Bid> findBidsByItemIdOrderByBidTimeDesc(@Param("itemId") int itemId);

    Bid findWinningBidByItemId(@Param("itemId") int itemId);

    void updateBidStatusByItemIdAndUserId(Map<String, Object> params);

    void updateBidStatus(Map<String, Object> params);

    void updateLosingBidsByItemId(@Param("itemId") int itemId, @Param("winningBidId") int winningBidId);
}
