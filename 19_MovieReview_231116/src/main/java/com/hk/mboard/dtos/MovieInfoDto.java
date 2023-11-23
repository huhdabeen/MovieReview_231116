package com.hk.mboard.dtos;

import org.apache.ibatis.type.Alias;

@Alias(value = "miBoardDto")
public class MovieInfoDto {

	private int movie_seq;
	private String movie_nm;
	private String opn;
	private String nlty_nm;
	private String genre_nm;
	private String grad_nm;
	
	public MovieInfoDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MovieInfoDto(int movie_seq, String movie_nm, String opn, String nlty_nm, String genre_nm, String grad_nm) {
		super();
		this.movie_seq = movie_seq;
		this.movie_nm = movie_nm;
		this.opn = opn;
		this.nlty_nm = nlty_nm;
		this.genre_nm = genre_nm;
		this.grad_nm = grad_nm;
	}

	@Override
	public String toString() {
		return "MovieInfoDto [movie_seq=" + movie_seq + ", movie_nm=" + movie_nm + ", opn=" + opn + ", nlty_nm="
				+ nlty_nm + ", genre_nm=" + genre_nm + ", grad_nm=" + grad_nm + "]";
	}

	public int getMovie_seq() {
		return movie_seq;
	}

	public void setMovie_seq(int movie_seq) {
		this.movie_seq = movie_seq;
	}

	public String getMovie_nm() {
		return movie_nm;
	}

	public void setMovie_nm(String movie_nm) {
		this.movie_nm = movie_nm;
	}

	public String getOpn() {
		return opn;
	}

	public void setOpn(String opn) {
		this.opn = opn;
	}

	public String getNlty_nm() {
		return nlty_nm;
	}

	public void setNlty_nm(String nlty_nm) {
		this.nlty_nm = nlty_nm;
	}

	public String getGenre_nm() {
		return genre_nm;
	}

	public void setGenre_nm(String genre_nm) {
		this.genre_nm = genre_nm;
	}

	public String getGrad_nm() {
		return grad_nm;
	}

	public void setGrad_nm(String grad_nm) {
		this.grad_nm = grad_nm;
	}
	
	
	
}
