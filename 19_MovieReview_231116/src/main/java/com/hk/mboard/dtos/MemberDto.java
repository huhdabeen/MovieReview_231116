package com.hk.mboard.dtos;

import java.util.List;

import org.apache.ibatis.type.Alias;

import com.hk.mboard.dtos.FileDto;

@Alias(value = "memberDto")
public class MemberDto {
	
	private int memberId;
	private String id; //아이디
	private String password; //비밀번호
	private String name; //닉네임
	private String email; //이메일
	private String role;
	private int file_seq;
	private String stored_name;
	
	//Join용 멤버필드 
	private List<FileDto> fileDto;
	
	public MemberDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MemberDto(int memberId, String id, String password, String name, String email, String role, int file_seq,
			String stored_name, List<FileDto> fileDto) {
		super();
		this.memberId = memberId;
		this.id = id;
		this.password = password;
		this.name = name;
		this.email = email;
		this.role = role;
		this.file_seq = file_seq;
		this.stored_name = stored_name;
		this.fileDto = fileDto;
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

	public int getFile_seq() {
		return file_seq;
	}

	public void setFile_seq(int file_seq) {
		this.file_seq = file_seq;
	}

	public String getStored_name() {
		return stored_name;
	}

	public void setStored_name(String stored_name) {
		this.stored_name = stored_name;
	}

	public List<FileDto> getFileDto() {
		return fileDto;
	}

	public void setFileDto(List<FileDto> fileDto) {
		this.fileDto = fileDto;
	}

	@Override
	public String toString() {
		return "MemberDto [memberId=" + memberId + ", id=" + id + ", password=" + password + ", name=" + name
				+ ", email=" + email + ", role=" + role + ", file_seq=" + file_seq + ", stored_name=" + stored_name
				+ ", fileDto=" + fileDto + "]";
	}

}

//package com.hk.mboard.dtos;
//
//import java.util.List;
//
//import org.apache.ibatis.type.Alias;
//
//import com.hk.mboard.dtos.FileDto;
//
//@Alias(value = "memberDto")
//public class MemberDto {
//	
//	private int memberId;
//	private String id; //아이디
//	private String password; //비밀번호
//	private String name; //닉네임
//	private String email; //이메일
//	private String role;
//	
//	//Join용 멤버필드 
//	private List<FileDto> fileDto;
//	
//	public MemberDto() {
//		super();
//		// TODO Auto-generated constructor stub
//	}
//
//	public MemberDto(int memberId, String id, String password, String name, String email, String role) {
//		super();
//		this.memberId = memberId;
//		this.id = id;
//		this.password = password;
//		this.name = name;
//		this.email = email;
//		this.role = role;
//	}
//
//	public int getMemberId() {
//		return memberId;
//	}
//
//	public void setMemberId(int memberId) {
//		this.memberId = memberId;
//	}
//
//	public String getId() {
//		return id;
//	}
//
//	public void setId(String id) {
//		this.id = id;
//	}
//
//	public String getPassword() {
//		return password;
//	}
//
//	public void setPassword(String password) {
//		this.password = password;
//	}
//
//	public String getName() {
//		return name;
//	}
//
//	public void setName(String name) {
//		this.name = name;
//	}
//
//	public String getEmail() {
//		return email;
//	}
//
//	public void setEmail(String email) {
//		this.email = email;
//	}
//
//	public String getRole() {
//		return role;
//	}
//
//	public void setRole(String role) {
//		this.role = role;
//	}
//
//	@Override
//	public String toString() {
//		return "MemberDto [memberId=" + memberId + ", id=" + id + ", password=" + password + ", name=" + name
//				+ ", email=" + email + ", role=" + role + "]";
//	}
//	
//}
