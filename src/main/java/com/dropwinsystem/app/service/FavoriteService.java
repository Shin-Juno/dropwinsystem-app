package com.dropwinsystem.app.service;

import com.dropwinsystem.app.domain.Favorite;
import com.dropwinsystem.app.domain.Item;
import com.dropwinsystem.app.mapper.FavoriteMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavoriteService {

    @Autowired
    private FavoriteMapper favoriteMapper;

    public void addFavorite(String userId, int itemId) {
        Favorite favorite = new Favorite();
        favorite.setUserId(userId);
        favorite.setItemId(itemId);
        favoriteMapper.addFavorite(favorite);
    }

    public void removeFavorite(String userId, int itemId) {
        favoriteMapper.removeFavorite(userId, itemId);
    }

    public List<Item> getFavoriteItemsByUserId(String userId) {
        return favoriteMapper.selectFavoriteItemsByUserId(userId);
    }

    public boolean isItemFavorited(String userId, int itemId) {
        return favoriteMapper.isItemFavorited(userId, itemId) > 0;
    }
}
