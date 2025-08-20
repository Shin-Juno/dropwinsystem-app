package com.dropwinsystem.app.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dropwinsystem.app.domain.Member;
import com.dropwinsystem.app.service.MemberService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Controller
@SessionAttributes("loginUser")
@Slf4j
public class MemberController {
	
	@Autowired
	private MemberService memberService;
	
    @GetMapping("/loginForm")
    public String loginForm(@RequestParam(value = "error", required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("errorMessage", error);
        }
        return "member/loginForm";
    }
	
	@PostMapping("/login")	
	public String login(Model model, @RequestParam("userId") String id, 
			@RequestParam("pass") String pass, 
			HttpSession session, RedirectAttributes redirectAttributes)
					throws ServletException, IOException {

		log.info("MemberController.login()");

		Member loggedInMember = memberService.login(id, pass);
		
		if(loggedInMember == null) {
			redirectAttributes.addAttribute("error", "아이디 또는 비밀번호가 일치하지 않습니다.");
			return "redirect:/loginForm";
		}		

		session.setAttribute("isLogin", true);
		model.addAttribute("loginUser", loggedInMember);
		System.out.println("member.name : " + loggedInMember.getName());
	
		return "redirect:/home";
	}

	@GetMapping("/memberLogout")
	public String logout(HttpSession session, SessionStatus sessionStatus) {	
		log.info("MemberController.logout(HttpSession session)");

		session.invalidate();
		sessionStatus.setComplete();
		
		return "redirect:/loginForm";
	}

	@RequestMapping("/overlapIdCheck")
	public String overlapIdCheck(Model model, @RequestParam("id") String id) {		

		boolean overlap = memberService.overlapIdCheck(id);

		model.addAttribute("id", id);
		model.addAttribute("overlap", overlap);

		return "member/overlapIdCheck.html";
	}

	@PostMapping("/joinResult")
	public String joinResult(Model model, Member member,
			@RequestParam("pass1") String pass1, 
			@RequestParam("emailId") String emailId, 
			@RequestParam("emailDomain") String emailDomain,
			@RequestParam("mobile1") String mobile1, 
			@RequestParam("mobile2") String mobile2, 
			@RequestParam("mobile3") String mobile3,
			@RequestParam(value="essOption", required=false, defaultValue="false") boolean essOption,
	        @RequestParam(value="unOption", required=false, defaultValue="false") boolean unOption) {		
		
		member.setPass(pass1);
		member.setEmail(emailId + "@" + emailDomain);
		member.setMobile(mobile1 + "-" + mobile2 + "-" + mobile3);
		member.setEssOption(essOption);
	    member.setUnOption(unOption);

		memberService.addMember(member);
		log.info("joinResult : " + member.getName());

		return "redirect:loginForm";
	}

	@GetMapping("/memberUpdate")
	public String updateForm(Model model, HttpSession session) {
		Member loginUser = (Member) session.getAttribute("loginUser");
		if (loginUser == null) {
			return "redirect:/loginForm";
		}
		model.addAttribute("loginUser", loginUser);
		return "member/memberUpdate";
	}	

	@PostMapping("/memberUpdateResult")
	public String memberUpdateInfo(@RequestParam("name") String name,
	                               @RequestParam("email") String email,
	                               @RequestParam("mobile") String mobile,
	                               @RequestParam("currentPassword") String currentPassword,
	                               @RequestParam(value = "newPassword", required = false) String newPassword,
	                               HttpSession session,
	                               Model model,
	                               RedirectAttributes redirectAttributes) {
		
		Member loginUser = (Member) session.getAttribute("loginUser");
		if (loginUser == null) {
		    return "redirect:/loginForm";
		}
		
		try {
			Member updatedUser = memberService.updateMember(loginUser.getId(), name, email, mobile, currentPassword, newPassword);
			
			model.addAttribute("loginUser", updatedUser);
			redirectAttributes.addFlashAttribute("message", "회원 정보가 성공적으로 수정되었습니다.");
			log.info("memberUpdateResult : " + updatedUser.getId());
			
			return "redirect:/home";
		} catch (IllegalArgumentException e) {
			redirectAttributes.addFlashAttribute("error", e.getMessage());
			return "redirect:/memberUpdateForm";
		}
	}
	
	@GetMapping("/findIdForm")
	public String showFindIdForm() {
		return "member/findIdForm";
	}

	@PostMapping("/findId")
	public String findId(@RequestParam("userName") String name, @RequestParam("userPhone") String mobile, RedirectAttributes redirectAttributes) {
		String foundId = memberService.findIdByNameAndMobile(name, mobile);

		if (foundId != null) {
			redirectAttributes.addFlashAttribute("message", "찾으신 아이디는 " + foundId + " 입니다.");
		} else {
			redirectAttributes.addFlashAttribute("error", "입력하신 정보와 일치하는 아이디가 없습니다.");
		}
		
		return "redirect:/findIdForm";
	}
	
	@GetMapping("/findPasswordForm")
	public String showFindPasswordForm() {
		return "member/findPasswordForm";
	}
	
	@PostMapping("/findPassword")
	public String findPassword(@RequestParam("userId") String userId, @RequestParam("userEmail") String userEmail, RedirectAttributes redirectAttributes) {
		boolean success = memberService.findPasswordAndSendEmail(userId, userEmail);
		
		if (success) {
			redirectAttributes.addFlashAttribute("message", "임시 비밀번호를 이메일로 전송했습니다. 이메일을 확인해 주세요.");
		} else {
			redirectAttributes.addFlashAttribute("error", "입력하신 아이디와 이메일 정보가 일치하지 않습니다.");
		}
		
		return "redirect:/findPasswordForm";
	}

	@GetMapping("/memberDelete")
	public String showMemberDeleteForm(HttpSession session) {
		Member loginUser = (Member) session.getAttribute("loginUser");
		if (loginUser == null) {
			return "redirect:/loginForm";
		}
		return "member/memberDelete";
	}

	@PostMapping("/memberDelete")
	public String deleteMember(@RequestParam("password") String password, HttpSession session, RedirectAttributes redirectAttributes, SessionStatus sessionStatus) {
		Member loginUser = (Member) session.getAttribute("loginUser");
		if (loginUser == null) {
			return "redirect:/loginForm";
		}
		
		try {
			memberService.deleteMember(loginUser.getId(), password);
			session.invalidate();
			sessionStatus.setComplete();
			redirectAttributes.addFlashAttribute("message", "회원 탈퇴가 성공적으로 완료되었습니다.");
			log.info("회원 탈퇴 완료: " + loginUser.getId());
			
			return "redirect:/loginForm";
		} catch (IllegalArgumentException e) {
			redirectAttributes.addFlashAttribute("error", e.getMessage());
			return "redirect:/memberDelete";
		}
	}
}
