
package com.dropwinsystem.app.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dropwinsystem.app.domain.Board;
import com.dropwinsystem.app.domain.Notice;
import com.dropwinsystem.app.mapper.BoardMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BoardService {
	@Autowired
	private BoardMapper boardMapper;

	@Autowired
	private static final int PAGE_SIZE = 15;

	private static final int PAGE_GROUP = 5;
	
	public Map<String, Object> boardList(int pageNum, String type, String keyword) {
		log.info("boardList(int pageNum, String type, String keyword)");
		int currentPage = pageNum;
		int startRow = (currentPage - 1) * PAGE_SIZE;
		log.info("startRow : " + startRow);
		
		boolean searchOption = (type.equals("null")
				|| keyword.equals("null")) ? false : true;
		
		int listCount = boardMapper.getBoardCount(type, keyword);
		
		List<Board> impList = boardMapper.getIsBoards();
		log.info("BoardService: Fetched {} notice boards.", impList.size());


		List<Board> boardList = boardMapper.boardList(startRow, PAGE_SIZE, type, keyword);
		log.info("BoardService: boardMapper.boardList returned {} items for page {}.", boardList.size(), pageNum);
		int pageCount = listCount / PAGE_SIZE + (listCount % PAGE_SIZE == 0 ? 0 : 1);
		int startPage = (currentPage / PAGE_GROUP) * PAGE_GROUP + 1 - (currentPage % PAGE_GROUP == 0 ? PAGE_GROUP : 0);
		int endPage = startPage + PAGE_GROUP - 1;

		if (endPage > pageCount) {
			endPage = pageCount;
		}

		Map<String, Object> modelMap = new HashMap<String, Object>();

		modelMap.put("iList", impList);
		modelMap.put("bList", boardList);
		modelMap.put("pageCount", pageCount);
		modelMap.put("startPage", startPage);
		modelMap.put("endPage", endPage);
		modelMap.put("currentPage", currentPage);
		modelMap.put("listCount", listCount);
		modelMap.put("pageGroup", PAGE_GROUP);
		modelMap.put("searchOption", searchOption);
		
		if(searchOption) {
			modelMap.put("type", type);
			modelMap.put("keyword", keyword);
		}

		return modelMap;
	}
	
	public void updateBoard(Board board) {
		log.info("BoardService: updateBoard(Board board)");
		boardMapper.updateBoard(board);
	}

	public Board getBoard(int no) {
		log.info("BoardService: getBoard(int no)");
		return boardMapper.getBoard(no);
	}

	public void addBoard(Board board) {
		log.info("BoardService: addBoard(Board board)");
		boardMapper.insertBoard(board);
	}
	
	public Board getPrevBoard(int no) {
		log.info("BoardService: getPrevBoard called for no: {}", no);
		return boardMapper.getPrevBoard(no);
	}
	
	public Board getNextBoard(int no) {
		log.info("BoardService: getNextBoard called for no: {}", no);
		return boardMapper.getNextBoard(no);
	}
	
	public void updateReadCount(int no) {
		log.info("BoardService: updateReadCount called for no: {}", no);
		boardMapper.updateReadCount(no);
	}
	
	public void deleteBoard(int no) {
        boardMapper.deleteBoard(no);
    }
	
	public Map<String, Integer> recommend(int no, String recommend) {
		
		boardMapper.updateRecommend(no, recommend);
		Board board = boardMapper.getRecommend(no);
		
		Map<String, Integer> map = new HashMap<String, Integer>(); 
		map.put("recommend", board.getRecommend());
		
		return map;
	}
	
}
