package com.dropwinsystem.app.service;

import java.util.Map;

import com.dropwinsystem.app.domain.Notice;

public interface NoticeService {
	
	Map<String, Object>noticeList(int pageNum, String type, String keyword);

    Notice getNotice(int noticeId, boolean isCount);

    void addNotice(Notice notice);

    void updateNotice(Notice notice);

    void deleteNotice(int noticeId);
    
    Notice getPrevNotice(int noticeId);
	
    Notice getNextNotice(int noticeId);

    Map<String, Integer> likeCount(int noticeId, String likeCount);
}
