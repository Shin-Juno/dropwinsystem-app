package com.dropwinsystem.app.service;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dropwinsystem.app.domain.Member;
import com.dropwinsystem.app.mapper.MemberMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MemberService {

	@Autowired
	private MemberMapper memberMapper;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private EmailService emailService;

	public Member login(String id, String pass) { 
		Member m = memberMapper.getMember(id);

		if(m == null) {
			log.info("로그인 시도: ID [{}] - 사용자 없음", id);
			return null;
		}

		if(passwordEncoder.matches(pass, m.getPass())) {
			log.info("로그인 성공: ID [{}]", id);
			return m; 
		} else {
			log.info("로그인 실패: ID [{}] - 비밀번호 불일치", id);
			return null; 
		}
	}
	
	public Member getMember(String id) {
		return memberMapper.getMember(id);
	}
	
	public boolean overlapIdCheck(String id) {
		Member member = memberMapper.getMember(id);
		log.info("overlapIdCheck - member : " + member);
		return member != null;
	}

	public void addMember(Member member) {
		member.setPass(passwordEncoder.encode(member.getPass()));
		log.info("회원가입 비밀번호 암호화: " + member.getPass());
		memberMapper.addMember(member);
	}

	public boolean memberPassCheck(String id, String pass) {		
		
		String dbPass = memberMapper.memberPassCheck(id);		
		boolean result = false;		
	
		if(passwordEncoder.matches(pass, dbPass)) {
			result = true;	
		}
		return result;	
	}

	public void updateMember(Member member) {
		if (member.getPass() != null && !member.getPass().isEmpty()) {
		    member.setPass(passwordEncoder.encode(member.getPass()));
		}
		log.info("회원정보 수정 비밀번호 암호화: " + member.getPass());
		
		memberMapper.updateMember(member);
	}
	
	public String findIdByNameAndMobile(String name, String mobile) {
		log.info("아이디 찾기 시도: 이름 [{}], 전화번호 [{}]", name, mobile);
		Member member = memberMapper.findByNameAndMobile(name, mobile);
		
		if (member != null) {
			log.info("아이디 찾기 성공: 아이디 [{}]", member.getId());
			return member.getId();
		} else {
			log.info("아이디 찾기 실패: 일치하는 회원 정보 없음");
			return null;
		}
	}
	
	@Transactional
	public Member updateMember(String id, String name, String email, String mobile, String currentPassword, String newPassword) {
		log.info("회원 정보 수정 시도: ID [{}]", id);
		
		Member member = memberMapper.getMember(id);
		if (member == null) {
			throw new IllegalArgumentException("사용자를 찾을 수 없습니다.");
		}
		
		if (!passwordEncoder.matches(currentPassword, member.getPass())) {
			throw new IllegalArgumentException("현재 비밀번호가 일치하지 않습니다.");
		}
		
		member.setName(name);
		member.setEmail(email);
		member.setMobile(mobile);
		
		if (newPassword != null && !newPassword.isEmpty()) {
			member.setPass(passwordEncoder.encode(newPassword));
			log.info("새 비밀번호 암호화 후 업데이트: ID [{}]", id);
		}
		
		memberMapper.updateMember(member);
		log.info("회원 정보 수정 성공: ID [{}]", id);
		
		return member;
	}

	public boolean findPasswordAndSendEmail(String userId, String userEmail) {
		log.info("비밀번호 찾기 시도: 아이디 [{}], 이메일 [{}]", userId, userEmail);
		Member member = memberMapper.findByIdAndEmail(userId, userEmail);
		
		if (member == null) {
			log.info("비밀번호 찾기 실패: 일치하는 회원 정보 없음");
			return false;
		}
		
		String tempPassword = RandomStringUtils.randomAlphanumeric(10);
		
		member.setPass(passwordEncoder.encode(tempPassword));
		memberMapper.updateMember(member);
		log.info("임시 비밀번호로 회원 정보 업데이트 성공: 아이디 [{}]", userId);
		
		String subject = "DropWin - 임시 비밀번호 발급 안내";
		String text = String.format("안녕하세요, %s님.\n\n요청하신 임시 비밀번호는 **%s** 입니다.\n\n로그인 후 반드시 비밀번호를 변경해 주세요.", member.getName(), tempPassword);
		
		try {
			emailService.sendSimpleEmail(userEmail, subject, text);
			log.info("임시 비밀번호 이메일 전송 성공: 아이디 [{}]", userId);
			return true;
		} catch (Exception e) {
			log.error("임시 비밀번호 이메일 전송 실패: 아이디 [{}]", userId, e);
			return false;
		}
	}

	@Transactional
	public void deleteMember(String id, String password) {
		log.info("회원 탈퇴 시도: ID [{}]", id);
		
		Member member = memberMapper.getMember(id);
		if (member == null) {
			throw new IllegalArgumentException("사용자를 찾을 수 없습니다.");
		}
		
		if (!passwordEncoder.matches(password, member.getPass())) {
			log.warn("회원 탈퇴 실패: ID [{}] - 비밀번호 불일치", id);
			throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
		}
		
		memberMapper.deleteBidsByMemberId(id);
		log.info("회원 ID [{}]에 해당하는 입찰 정보 삭제 성공", id);

		memberMapper.deleteMember(id);
		log.info("회원 탈퇴 성공: ID [{}]", id);
	}
}

