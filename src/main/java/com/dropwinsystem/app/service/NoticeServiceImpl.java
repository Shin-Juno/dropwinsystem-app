package com.dropwinsystem.app.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dropwinsystem.app.domain.Notice;
import com.dropwinsystem.app.mapper.NoticeMapper;

@Service
public class NoticeServiceImpl implements NoticeService {

	@Autowired
	private NoticeMapper noticeMapper;

	private static final int PAGE_SIZE = 15;

	private static final int PAGE_GROUP = 5;

	@Override
	public Map<String, Object> noticeList(int pageNum, String type, String keyword) {

		int currentPage = pageNum;

		int startRow = (currentPage - 1) * PAGE_SIZE;
		
		List<Notice> specialList = noticeMapper.getIsNotices();

		boolean searchOption = (type.equals("null") || keyword.equals("null")) ? false : true;

		int listCount = noticeMapper.getNoticeCount(type, keyword);

		List<Notice> noticeList = noticeMapper.noticeList(startRow, PAGE_SIZE, type, keyword);

		int pageCount = listCount / PAGE_SIZE + (listCount % PAGE_SIZE == 0 ? 0 : 1);

		int startPage = (currentPage / PAGE_GROUP) * PAGE_GROUP + 1 - (currentPage % PAGE_GROUP == 0 ? PAGE_GROUP : 0);

		int endPage = startPage + PAGE_GROUP - 1;

		if (endPage > pageCount) {
			endPage = pageCount;
		}

		Map<String, Object> result = new HashMap<>();
		result.put("sList", specialList);
		result.put("nList", noticeList);
		result.put("pageCount", pageCount);
		result.put("startPage", startPage);
		result.put("endPage", endPage);
		result.put("currentPage", currentPage);
		result.put("listCount", listCount);
		result.put("pageGroup", PAGE_GROUP);
		result.put("searchOption", searchOption);
		
		if(searchOption) {
			result.put("type", type);
			result.put("keyword", keyword);
		}

		return result;
	}
	
	public Notice getNotice(int noticeId, boolean isCount) {	
		if(isCount) {
			noticeMapper.increaseViewCount(noticeId);
		}
		return noticeMapper.getNotice(noticeId);
	}
	
	public void addNotice(Notice notice) {
		noticeMapper.insertNotice(notice);
	}

	public void updateNotice(Notice notice) {
		noticeMapper.updateNotice(notice);
	}
	
	public void deleteNotice(int noticeId) {
		noticeMapper.deleteNotice(noticeId);
	}
	
	public Notice getPrevNotice(int noticeId) {
		return noticeMapper.getPrevNotice(noticeId);
	}
	
	public Notice getNextNotice(int noticeId) {
		return noticeMapper.getNextNotice(noticeId);
	}
	
	public Map<String, Integer> likeCount(int noticeId, String likeCount) {
		
		noticeMapper.updateLikeCount(noticeId, likeCount);
		Notice notice = noticeMapper.getLikeCount(noticeId);
		
		Map<String, Integer> map = new HashMap<String, Integer>(); 
		map.put("likeCount", notice.getLikeCount());
		map.put("dislikeCount", notice.getDislikeCount());
		return map;
	}
	
}