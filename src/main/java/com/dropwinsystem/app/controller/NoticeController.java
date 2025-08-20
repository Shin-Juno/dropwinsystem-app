package com.dropwinsystem.app.controller;

import com.dropwinsystem.app.domain.Member;
import com.dropwinsystem.app.domain.Notice;
import com.dropwinsystem.app.service.NoticeService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Map;
import java.util.UUID;

@Controller
@Slf4j
public class NoticeController {

	private static final String DEFAULT_PATH = "src/main/resources/static/files/notice/";
	
	@Value("${app.upload.dir}")
	private String uploadDir;
	
	@Autowired
	private NoticeService noticeService;

	@GetMapping("/noticeList")
	public String noticeList(Model model,
			@RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
			@RequestParam(value = "type", required = false, defaultValue = "null") String type,
			@RequestParam(value = "keyword", required = false, defaultValue = "null") String keyword) {

		Map<String, Object> modelMap = noticeService.noticeList(pageNum, type, keyword);
		model.addAllAttributes(modelMap);
		return "views/noticeList";
	}

	@GetMapping("/noticeDetail")
	public String noticeDetail(Model model,  HttpSession session, @RequestParam("noticeId") int noticeId,
			@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
			@RequestParam(value = "type", defaultValue = "null") String type,
			@RequestParam(value = "keyword", defaultValue = "null") String keyword) {

		boolean searchOption = (!"null".equals(type) && !"null".equals(keyword)) ? false : true;

		Notice notice = noticeService.getNotice(noticeId, true);
		
		model.addAttribute("notice", notice);
		model.addAttribute("pageNum", pageNum);
		model.addAttribute("prevNotice", noticeService.getPrevNotice(noticeId));
		model.addAttribute("nextNotice", noticeService.getNextNotice(noticeId));
		model.addAttribute("searchOption", searchOption);

		Member loginUser = (Member) session.getAttribute("loginUser");
		
		model.addAttribute("loginUser", session.getAttribute("loginUser"));
		
		if (loginUser != null) {
		    System.out.println("로그인한 사용자 ID: " + loginUser.getId());
		} else {
		    System.out.println("로그인하지 않은 사용자입니다.");
		}

		
		if (searchOption) {
			model.addAttribute("type", type);
			model.addAttribute("keyword", keyword);
		}

		return "views/noticeDetail";
	}

	@GetMapping("/addNotice")
	public String addBoard(HttpSession session, HttpServletResponse response) throws IOException {

		Member loginUser = (Member) session.getAttribute("loginUser");
		return "views/noWriteForm";
	}


	@PostMapping("/addNotice")
	public String addBoard(Notice notice, 
			@RequestParam(value = "addAttachFile", required = false) MultipartFile multipartFile,
			HttpSession session, HttpServletResponse response) throws IOException {

		Member loginUser = (Member) session.getAttribute("loginUser");

		notice.setWriter(loginUser.getName());

		if (multipartFile != null && !multipartFile.isEmpty()) {
			File parent = new File(DEFAULT_PATH);

			if (!parent.isDirectory() && !parent.exists()) {
				parent.mkdirs();
			}

			UUID uid = UUID.randomUUID();
			String extension = StringUtils.getFilenameExtension(multipartFile.getOriginalFilename());
			String saveName = uid.toString() + "." + extension;

			File file = new File(parent.getAbsolutePath(), saveName);

			log.info("file abs path : " + file.getAbsolutePath());
			log.info("file path : " + file.getPath());

			multipartFile.transferTo(file);
			notice.setAttachFile(saveName);
		} else {
			log.info("No file uploaded - 파일이 업로드 되지 않음");
		}

		noticeService.addNotice(notice);

		return "redirect:noticeList";
	}


	@PostMapping("/noUpdateForm")
	public String updateNotice(Model model, @RequestParam("noticeId") int noticeId,
			@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
			@RequestParam(value = "type", defaultValue = "null") String type,
			@RequestParam(value = "keyword", defaultValue = "null") String keyword,
			HttpSession session, RedirectAttributes reAttrs) {
		
		Member loginUser = (Member) session.getAttribute("loginUser");
		
		System.out.println("로그인한 사용자 ID: " + loginUser.getId());
		session.setAttribute("loginUser", loginUser);
		
	    if (loginUser == null || !"admin".equals(loginUser.getId())) {
	        reAttrs.addFlashAttribute("message", "접근 권한이 없습니다.");
	        return "redirect:/noticeList";
	    }

		Notice notice = noticeService.getNotice(noticeId, false);

		boolean searchOption = (type.equals("null") || keyword.equals("null")) ? false : true;

		model.addAttribute("notice", notice);
		model.addAttribute("pageNum", pageNum);
		model.addAttribute("searchOption", searchOption);

		if (searchOption) {
			model.addAttribute("type", type);
			model.addAttribute("keyword", keyword);
		}
		return "views/noUpdateForm";
	}

	@PostMapping("/noUpdate")
	public String updateNotice(Notice notice, RedirectAttributes reAttrs, 
			@RequestParam("noticeId") int noticeId,
			@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
			@RequestParam(value = "type", defaultValue = "null") String type,
			@RequestParam(value = "keyword", defaultValue = "null") String keyword) {

		noticeService.updateNotice(notice);
		
		boolean searchOption = (type.equals("null") || keyword.equals("null")) ? false : true;

		reAttrs.addAttribute("searchOption", searchOption);
		reAttrs.addAttribute("pageNum", pageNum);

		if (searchOption) {

			reAttrs.addAttribute("type", type);
			reAttrs.addAttribute("keyword", keyword);
		}

		return "redirect:noticeList";
	}

	@PostMapping("/delete")
	public String deletenotice(HttpSession session,
	                           RedirectAttributes reAttrs,
	                           @RequestParam("noticeId") int noticeId,
	                           @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
	                           @RequestParam(value = "type", defaultValue = "null") String type,
	                           @RequestParam(value = "keyword", defaultValue = "null") String keyword) {
		
		Member loginUser = (Member)session.getAttribute("loginUser");
		
		if (loginUser != null && "admin".equals(loginUser.getId())) {
			
			this.noticeService.deleteNotice(noticeId);
			boolean searchOption = !"null".equals(type) && !"null".equals(keyword);
	
			reAttrs.addAttribute("pageNum", pageNum);
			reAttrs.addAttribute("searchOption", searchOption);
	
			if (searchOption) {
				reAttrs.addAttribute("type", type);
				reAttrs.addAttribute("keyword", keyword);
			}
	
			reAttrs.addFlashAttribute("message", "삭제되었습니다.");
			
			return "redirect:/noticeList";
		} else {
			reAttrs.addFlashAttribute("message", "접근 권한이 없습니다.");
			
			return "redirect:/noticeList";
		}
	}




	@GetMapping("/notice/fileDownload")
	public void download(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String fileName = request.getParameter("fileName");
		log.info("fileName : " + fileName);

		File parent = new File(DEFAULT_PATH);
		File file = new File(parent.getAbsolutePath(), fileName);
		log.info("file.getName() : " + file.getName());

		response.setContentType("application/download; charset=UTF-8");
		response.setContentLength((int) file.length());

		fileName = URLEncoder.encode(file.getName(), "UTF-8");
		log.info("다운로드 fileName : " + fileName);

		response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\";");

		response.setHeader("Content-Transfer-Encoding", "binary");

		OutputStream out = response.getOutputStream();
		FileInputStream fis = null;

		fis = new FileInputStream(file);

		FileCopyUtils.copy(fis, out);

		if (fis != null) {
			fis.close();
		}

		out.flush();
	}
}
