package com.dropwinsystem.app.mapper;

import com.dropwinsystem.app.domain.Favorite;
import com.dropwinsystem.app.domain.Item;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FavoriteMapper {
    void addFavorite(Favorite favorite);
    void removeFavorite(@Param("userId") String userId, @Param("itemId") int itemId);
    List<Item> selectFavoriteItemsByUserId(String userId);
    int isItemFavorited(@Param("userId") String userId, @Param("itemId") int itemId);
}
