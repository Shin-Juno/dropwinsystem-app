package com.dropwinsystem.app.ajax;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dropwinsystem.app.service.NoticeService;

@RestController
public class NoticeAjaxController {
	
	@Autowired
	private NoticeService noticeService;

	@PostMapping("/likeCount.ajax")
	public Map<String, Integer> likeCount(@RequestParam("noticeId") int noticeId,
			@RequestParam("likeCount") String likeCount) {
	
		return noticeService.likeCount(noticeId, likeCount);		
	}

}
