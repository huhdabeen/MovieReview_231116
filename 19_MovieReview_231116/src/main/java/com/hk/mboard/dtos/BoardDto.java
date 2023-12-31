package com.hk.mboard.dtos;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.type.Alias;

import lombok.Data;

//@Data
@Alias(value = "boardDto") //mapper.xml에서 사용하는 변수명 설정
public class BoardDto {
	
	private int review_seq;
	private int board_seq;
	private String genre_nm;
	private String movie_nm;
	private String title;
	private String content;
	private String name;
	private Date regdate;
	private String delflag;

	
	//Join용 멤버필드 
	private List<MovieInfoDto> miBoardDto;
	
	//Join용 멤버필드 
	private List<MemberDto> memberDto;

	public BoardDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BoardDto(int review_seq, int board_seq, String genre_nm, String movie_nm, String title, String content,
			String name, Date regdate, String delflag, List<MovieInfoDto> miBoardDto, List<MemberDto> memberDto) {
		super();
		this.review_seq = review_seq;
		this.board_seq = board_seq;
		this.genre_nm = genre_nm;
		this.movie_nm = movie_nm;
		this.title = title;
		this.content = content;
		this.name = name;
		this.regdate = regdate;
		this.delflag = delflag;
		this.miBoardDto = miBoardDto;
		this.memberDto = memberDto;
	}

	public int getReview_seq() {
		return review_seq;
	}

	public void setReview_seq(int review_seq) {
		this.review_seq = review_seq;
	}

	public int getBoard_seq() {
		return board_seq;
	}

	public void setBoard_seq(int board_seq) {
		this.board_seq = board_seq;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getRegdate() {
		return regdate;
	}

	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}

	public String getDelflag() {
		return delflag;
	}

	public void setDelflag(String delflag) {
		this.delflag = delflag;
	}

	public List<MovieInfoDto> getMiBoardDto() {
		return miBoardDto;
	}

	public void setMiBoardDto(List<MovieInfoDto> miBoardDto) {
		this.miBoardDto = miBoardDto;
	}

	public List<MemberDto> getMemberDto() {
		return memberDto;
	}

	public void setMemberDto(List<MemberDto> memberDto) {
		this.memberDto = memberDto;
	}

	@Override
	public String toString() {
		return "BoardDto [review_seq=" + review_seq + ", board_seq=" + board_seq + ", genre_nm=" + genre_nm
				+ ", movie_nm=" + movie_nm + ", title=" + title + ", content=" + content + ", name=" + name
				+ ", regdate=" + regdate + ", delflag=" + delflag + ", miBoardDto=" + miBoardDto + ", memberDto="
				+ memberDto + "]";
	}

	
}