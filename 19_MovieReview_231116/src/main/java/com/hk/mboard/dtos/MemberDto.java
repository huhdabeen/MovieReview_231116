package com.hk.mboard.dtos;

import org.apache.ibatis.type.Alias;

@Alias(value = "memberDto")
public class MemberDto {
	
	private int memberId;
	private String id; //아이디
	private String password; //비밀번호
	private String name; //닉네임
	private String email; //이메일
	private String role;
	
	public MemberDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MemberDto(int memberId, String id, String password, String name, String email, String role) {
		super();
		this.memberId = memberId;
		this.id = id;
		this.password = password;
		this.name = name;
		this.email = email;
		this.role = role;
	}

	public int getMemberId() {
		return memberId;
	}

	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "MemberDto [memberId=" + memberId + ", id=" + id + ", password=" + password + ", name=" + name
				+ ", email=" + email + ", role=" + role + "]";
	} 
	
	
	
}
