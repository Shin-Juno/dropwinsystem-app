package com.dropwinsystem.app.social;

import com.dropwinsystem.app.domain.NaverUser;

public interface SocialOauth {
    String getOauthRedirectURL();
    String requestAccessToken(String code);
    NaverUser getUserInfo(String accessToken);
}