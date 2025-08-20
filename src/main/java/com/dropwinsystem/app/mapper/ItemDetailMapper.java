package com.dropwinsystem.app.mapper;

import com.dropwinsystem.app.domain.ItemDetail;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ItemDetailMapper {
    void insertItemDetail(ItemDetail itemDetail);
    ItemDetail getItemDetailByItemId(int itemId);
}