package com.dropwinsystem.app.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.dropwinsystem.app.domain.Board;
import com.dropwinsystem.app.domain.Notice;

@Mapper
public interface NoticeMapper {
	
	public List<Notice> noticeList(
			@Param("startRow") int startRow, @Param("num") int num,
			@Param("type") String type, @Param("keyword") String keyword);

	public int getNoticeCount(
			@Param("type") String type, @Param("keyword") String keyword);

	public Notice getNotice(@Param("noticeId") int noticeId);
	
	public List<Notice> getIsNotices();
	
	public int getNoticesCount();

	public void insertNotice(Notice notice);

	public void updateNotice(Notice notice);	

	public void deleteNotice(int noticeId);
	
	public Notice getPrevNotice(@Param("noticeId") int noticeId);
	
	public Notice getNextNotice(@Param("noticeId") int noticeId);

	public void increaseViewCount(int noticeId);

	public void updateLikeCount(
			@Param("noticeId") int noticeId, @Param("likeCount") String likeCount);

	public Notice getLikeCount(int noticeId);
}