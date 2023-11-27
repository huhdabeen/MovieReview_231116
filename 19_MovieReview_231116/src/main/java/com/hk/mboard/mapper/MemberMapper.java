package com.hk.mboard.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.hk.mboard.dtos.MemberDto;

@Mapper
public interface MemberMapper {

	//회원가입
	public boolean addUser(MemberDto dto);
	//아이디중복체크
	public String idChk(String id);
	//닉네임중복체크
	public String nameChk(String name);
	//로그인
	public MemberDto loginUser(String id);
	//회원정보
	public MemberDto getUserInfo(int memberId);
	//회원정보수정
	public boolean updateUser(MemberDto dto);
	//회원탈퇴
	public boolean delUser(MemberDto dto);
}
