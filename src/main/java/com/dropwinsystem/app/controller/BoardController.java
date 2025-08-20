package com.dropwinsystem.app.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.dropwinsystem.app.domain.Board;
import com.dropwinsystem.app.domain.Member;
import com.dropwinsystem.app.service.BoardService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class BoardController {

	@Autowired
	private BoardService boardService;

	@Value("${app.upload.dir}")
	private String uploadDir;

	@PostMapping("/addBoard")
	public String addBoard(Board board, @RequestParam(value = "addFile", required = false) MultipartFile multipartFile,
			HttpSession session) throws IOException {

		log.info("addBoard - originName : " + multipartFile.getOriginalFilename());
		log.info("addBoard - name : " + multipartFile.getName());

		Member loginUser = (Member) session.getAttribute("loginUser");
		if (loginUser != null) {
			board.setName(loginUser.getName());
			log.info("게시글 작성자 설정: {}", loginUser.getName());
		} else {
			board.setName("익명");
			log.warn("로그인된 사용자 정보(loginUser)가 세션에 없습니다. 작성자를 '익명'으로 설정합니다.");
		}

		if (multipartFile != null && !multipartFile.isEmpty()) {
			File parent = new File(uploadDir);

			if (!parent.isDirectory() && !parent.exists()) {
				parent.mkdirs();
				log.info("파일 업로드 디렉토리 생성: {}", uploadDir);
			}

			UUID uid = UUID.randomUUID();
			String extension = StringUtils.getFilenameExtension(multipartFile.getOriginalFilename());
			String saveName = uid.toString() + "." + extension;

			File file = new File(parent.getAbsolutePath(), saveName);
			log.info("addBoard - file abs path : " + file.getAbsolutePath());
			log.info("addBoard - file path : " + file.getPath());

			try {
				multipartFile.transferTo(file);
				board.setFile1(saveName);
			} catch (IOException e) {
				log.error("addBoard 파일 업로드 중 오류 발생: {}", e.getMessage());
				board.setFile1(null);
			}
		} else {
			log.info("addBoard - No file uploaded - 파일이 업로드 되지 않음");
		}
		boardService.addBoard(board);
		return "redirect:board";
	}

	@GetMapping("/fileDownload")
	public void download(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String fileName = request.getParameter("fileName");
		log.info("fileDownload - fileName : " + fileName);

		File parent = new File(uploadDir);
		File file = new File(parent.getAbsolutePath(), fileName);
		log.info("fileDownload - file.getName() : " + file.getName());

		if (!file.exists()) {
			log.warn("fileDownload - 다운로드할 파일이 존재하지 않습니다: {}", fileName);
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "File not found.");
			return;
		}

		response.setContentType("application/download; charset=UTF-8");
		response.setContentLength((int) file.length());

		fileName = URLEncoder.encode(file.getName(), "UTF-8");
		log.info("fileDownload - 다운로드 fileName : " + fileName);

		response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\";");
		response.setHeader("Content-Transfer-Encoding", "binary");

		OutputStream out = response.getOutputStream();
		FileInputStream fis = null;

		try {
			fis = new FileInputStream(file);
			FileCopyUtils.copy(fis, out);
		} finally {
			if (fis != null) {
				fis.close();
			}
			out.flush();
		}
	}

	@GetMapping("/updateBoard")
	public String updateBoardForm(@RequestParam("no") int no, Model model) {
		log.info("BoardController: updateBoardForm request for no: {}", no);
		Board board = boardService.getBoard(no);
		model.addAttribute("board", board);
		return "views/updateForm";
	}

	@PostMapping("/updateBoard")
	public String updateBoard(Board board, @RequestParam(value = "addFile", required = false) MultipartFile addFile,
			HttpSession session) throws IOException {

		log.info("게시글 수정 요청: {}", board.getTitle());
		log.info("수정할 게시글 번호: {}", board.getNo());
		log.info("폼에서 받은 작성자: {}", board.getName());
		log.info("폼에서 받은 비밀번호: {}", board.getPass() != null && !board.getPass().isEmpty() ? "입력됨" : "없음");
		log.info("새 첨부파일 여부: {}", (addFile != null && !addFile.isEmpty()));

		Board existingBoard = boardService.getBoard(board.getNo());
		String existingFile1 = existingBoard.getFile1();
		String existingPass = existingBoard.getPass();
		String existingName = existingBoard.getName();

		if (addFile != null && !addFile.isEmpty()) {
			File parent = new File(uploadDir);
			if (!parent.isDirectory() && !parent.exists()) {
				parent.mkdirs();
				log.info("파일 업로드 디렉토리 생성: {}", uploadDir);
			}

			if (existingFile1 != null && !existingFile1.isEmpty()) {
				File oldFile = new File(parent, existingFile1);
				if (oldFile.exists()) {
					if (oldFile.delete()) {
						log.info("기존 첨부파일 삭제 성공: {}", existingFile1);
					} else {
						log.warn("기존 첨부파일 삭제 실패: {}", existingFile1);
					}
				} else {
					log.info("삭제할 기존 첨부파일이 존재하지 않음: {}", existingFile1);
				}
			}

			UUID uid = UUID.randomUUID();
			String extension = StringUtils.getFilenameExtension(addFile.getOriginalFilename());
			String saveName = uid.toString() + "." + extension;
			File file = new File(parent, saveName);

			try {
				addFile.transferTo(file);
				board.setFile1(saveName);
				log.info("새 파일 저장 및 board.file1 업데이트: {}", saveName);
			} catch (IOException e) {
				log.error("updateBoard 파일 업로드 중 오류 발생: {}", e.getMessage());
				board.setFile1(existingFile1);
			}
		} else {
			board.setFile1(existingFile1);
			log.info("새 파일 없음. 기존 파일명({}) 유지.", existingFile1);
		}

		if (board.getPass() == null || board.getPass().isEmpty()) {
			board.setPass(existingPass);
			log.info("새 비밀번호 없음. 기존 비밀번호 유지.");
		}
		board.setName(existingName);
		log.info("게시글 수정 시 기존 작성자({}) 유지.", existingName);

		boardService.updateBoard(board);
		return "redirect:board";
	}

	@PostMapping("/deleteBoard")
	public ResponseEntity<Map<String, Object>> deleteBoard(@RequestParam("no") int no) {

		log.info("게시글 삭제 요청: No={}", no);
		Map<String, Object> response = new HashMap<>();

		try {
			Board board = boardService.getBoard(no);

			if (board == null) {
				response.put("success", false);
				response.put("message", "삭제할 게시글을 찾을 수 없습니다.");
				return ResponseEntity.badRequest().body(response);
			}

			boardService.deleteBoard(no);
			log.info("게시글(no: {})이 DB에서 삭제되었습니다.", no);

			if (board.getFile1() != null && !board.getFile1().isEmpty()) {
				File fileToDelete = new File(uploadDir, board.getFile1());
				if (fileToDelete.exists()) {
					if (fileToDelete.delete()) {
						log.info("첨부파일 삭제 성공: {}", board.getFile1());
					} else {
						log.warn("첨부파일 삭제 실패: {}", board.getFile1());
					}
				} else {
					log.info("삭제할 첨부파일이 존재하지 않음: {}", board.getFile1());
				}
			}

			response.put("success", true);
			response.put("message", "게시글이 성공적으로 삭제되었습니다.");
			return ResponseEntity.ok(response);

		} catch (Exception e) {
			log.error("게시글 삭제 중 오류 발생: No={}, Error={}", no, e.getMessage(), e);
			response.put("success", false);
			response.put("message", "서버 오류로 인해 삭제에 실패했습니다.");
			return ResponseEntity.status(500).body(response);
		}
	}

	@GetMapping("/board")
	public String boardList(Model model,
			@RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
			@RequestParam(value = "type", required = false, defaultValue = "null") String type,
			@RequestParam(value = "keyword", required = false, defaultValue = "null") String keyword) {
		log.info("BoardController: boardList request with pageNum: {}", pageNum);

		Map<String, Object> modelMap = boardService.boardList(pageNum, type, keyword);

		List<Board> iList = (List<Board>) modelMap.get("iList");
		if (iList != null) {
			log.info("필독 게시글 수: {}", iList.size());
		} else {
			log.info("필독 게시글 목록이 없습니다.");
		}

		model.addAllAttributes(modelMap);

		return "views/board";
	}

    @GetMapping("/boardDetail")
    public String board(Model model, @RequestParam("no") int no, HttpSession session) { // HttpSession session 파라미터 추가
        log.info("BoardController: boardDetail request for no: {}", no);

        boardService.updateReadCount(no);

        Board board = boardService.getBoard(no);
        model.addAttribute("board", board);

        model.addAttribute("prevBoard", boardService.getPrevBoard(no));
        model.addAttribute("nextBoard", boardService.getNextBoard(no));

        Member loginUser = (Member) session.getAttribute("loginUser");
        if (loginUser != null) {
            model.addAttribute("loginUser", loginUser);
            log.info("로그인된 사용자 이름(loginUser.name): {}", loginUser.getName());
        } else {
            log.info("로그인된 사용자가 없습니다. loginUser는 null로 설정됩니다.");
        }
        return "views/boardDetail";
    }

	@GetMapping("/addBoard")
	public String addBoard(HttpSession session, HttpServletResponse response) throws IOException {
		Member loginUser = (Member) session.getAttribute("loginUser");

		if (loginUser == null) {
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("    alert('로그인시 글작성이 가능합니다.');");
			out.println("    setTimeout(function() {");
			out.println("        location.href='/loginForm';");
			out.println("    }, 10); // 10ms 정도면 대부분의 경우 충분합니다.");
			out.println("</script>");
			out.flush();
			log.warn("비로그인 사용자가 글쓰기 페이지에 접근 시도. 로그인 폼으로 리다이렉트.");
			return null;
		} else {
			log.info("로그인된 사용자({})가 글쓰기 페이지에 접근.", loginUser.getName());
			return "views/writeForm";
		}
	}
}