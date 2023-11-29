package com.hk.mboard.command;

import jakarta.validation.constraints.NotBlank;

public class UpdateBoardCommand {
	
	private int review_seq;
	@NotBlank(message = "제목을 입력하세요")
	private String title;
	@NotBlank(message = "내용을 입력하세요")
	private String content;
	
	public UpdateBoardCommand() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UpdateBoardCommand(int review_seq, @NotBlank(message = "제목을 입력하세요") String title,
			@NotBlank(message = "내용을 입력하세요") String content) {
		super();
		this.review_seq = review_seq;
		this.title = title;
		this.content = content;
	}

	@Override
	public String toString() {
		return "UpdateBoardCommand [review_seq=" + review_seq + ", title=" + title + ", content=" + content + "]";
	}

	public int getReview_seq() {
		return review_seq;
	}

	public void setReview_seq(int review_seq) {
		this.review_seq = review_seq;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	
	
	
}
