package com.hk.mboard.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.hk.mboard.dtos.MemberDto;

@Mapper
public interface MemberMapper {

	public boolean addUser(MemberDto dto);
	
	public String idChk(String id);
	
	public String nameChk(String name);
	
	public MemberDto loginUser(String id);
}
