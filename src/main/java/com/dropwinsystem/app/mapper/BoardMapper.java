package com.dropwinsystem.app.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.dropwinsystem.app.domain.Board;
@Mapper
public interface BoardMapper {
	
	public List<Board> boardList(
		@Param("startRow") int startRow, @Param("num") int num,
		@Param("type") String type, @Param("keyword") String keyword);
	
	public int getBoardCount(
			@Param("type") String type, @Param("keyword") String keyword);
	
	public Board getBoard(int no);
	
	public void insertBoard(Board board);
	
//	필독란 메서드
	public List<Board> getIsBoards();
	
	public int getNoticeBoardCount();
	
	public Board getPrevBoard(@Param("no") int no);
	
	public Board getNextBoard(@Param("no") int no);
	
	public void updateReadCount(int no);
	
	public void updateBoard(Board board);
	
	public void deleteBoard(int no);
	
	public void updateRecommend(
			@Param("no") int no, @Param("recommend") String recommend);
	
	// 게시글 번호에 해당하는 추천/땡큐 정보를 읽어와 반환하는 메서드
	public Board getRecommend(int no);
	
}
