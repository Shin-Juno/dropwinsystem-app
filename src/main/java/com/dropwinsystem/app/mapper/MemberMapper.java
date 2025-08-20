package com.dropwinsystem.app.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.dropwinsystem.app.domain.Member;

@Mapper
public interface MemberMapper {

	public Member getMember(String id);	

	public void addMember(Member member);

	public String memberPassCheck(String id);

	public void updateMember(Member member);

	public Member findById(String name);
	
	public Member findByNameAndMobile(@Param("name") String name, @Param("mobile") String mobile);
	
	public Member findByIdAndEmail(@Param("id") String id, @Param("email") String email);

	public void deleteMember(String id);

    public void deleteBidsByMemberId(String id);
}
