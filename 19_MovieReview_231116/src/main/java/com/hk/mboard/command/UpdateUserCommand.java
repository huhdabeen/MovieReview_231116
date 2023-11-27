package com.hk.mboard.command;

import jakarta.validation.constraints.NotBlank;

public class UpdateUserCommand {

	private int memberId;
	@NotBlank(message = "변경할 닉네임을 입력해주세요.")
	private String name;	
	@NotBlank(message = "변경할 이메일을 입력해주세요.")
	private String email;

	public UpdateUserCommand() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UpdateUserCommand(int memberId, @NotBlank(message = "변경할 닉네임을 입력해주세요.") String name,
			@NotBlank(message = "변경할 이메일을 입력해주세요.") String email) {
		super();
		this.memberId = memberId;
		this.name = name;
		this.email = email;
	}

	public int getMemberId() {
		return memberId;
	}

	public void setMemberId(int memberId) {
		this.memberId = memberId;
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

	@Override
	public String toString() {
		return "UpdateUserCommand [memberId=" + memberId + ", name=" + name + ", email=" + email + "]";
	}

	
}
