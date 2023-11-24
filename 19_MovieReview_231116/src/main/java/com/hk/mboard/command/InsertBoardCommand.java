package com.hk.mboard.command;

import jakarta.validation.constraints.NotBlank;

public class InsertBoardCommand {
	
	private String id;
	@NotBlank(message = "제목을 입력하세요")
	private String title;
	@NotBlank(message = "내용을 입력하세요")
	private String content;
	
	@NotBlank(message = "장르를 선택하세요")
	private String genre_nm;
	@NotBlank(message = "영화를 선택하세요")
	private String movie_nm;
	
	public InsertBoardCommand() {
		super();
		// TODO Auto-generated constructor stub
	}

	public InsertBoardCommand(String id, @NotBlank(message = "제목을 입력하세요") String title,
			@NotBlank(message = "내용을 입력하세요") String content, @NotBlank(message = "장르를 선택하세요") String genre_nm,
			@NotBlank(message = "영화를 선택하세요") String movie_nm) {
		super();
		this.id = id;
		this.title = title;
		this.content = content;
		this.genre_nm = genre_nm;
		this.movie_nm = movie_nm;
	}

	@Override
	public String toString() {
		return "InsertBoardCommand [id=" + id + ", title=" + title + ", content=" + content + ", genre_nm=" + genre_nm
				+ ", movie_nm=" + movie_nm + "]";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getGenre_nm() {
		return genre_nm;
	}

	public void setGenre_nm(String genre_nm) {
		this.genre_nm = genre_nm;
	}

	public String getMovie_nm() {
		return movie_nm;
	}

	public void setMovie_nm(String movie_nm) {
		this.movie_nm = movie_nm;
	}
	
	
	
	
}
