package com.dropwinsystem.app.mapper;

import com.dropwinsystem.app.domain.Item;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Mapper
public interface ItemMapper {

    void insertItem(Item item);

    Item selectItemById(int id);

    List<Item> selectPendingItems();

    void updateItemStatus(Map<String, Object> params);

    void updateCurrentPrice(Map<String, Object> params);

    List<Item> selectAllItems(Map<String, Object> params);
    
    List<Item> selectBestItems();
    
    void incrementBidCount(int itemId);

    void updateEndTime(@Param("id") int id, @Param("endTime") LocalDateTime endTime);

    void updateItemPaymentStatus(Map<String, Object> params);

    List<Item> findExpiredItemsWithPendingStatus();
    
    Item selectItemForUpdateById(int id);
}
