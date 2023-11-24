package com.hk.mboard.dtos;

import org.apache.ibatis.type.Alias;

@Alias(value = "fileDto")
public class FileDto {
	
	private int file_seq;
	private int memberid;
	private String origin_filename;
	private String stored_filename;
	
	public FileDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public FileDto(int file_seq, int memberid, String origin_filename, String stored_filename) {
		super();
		this.file_seq = file_seq;
		this.memberid = memberid;
		this.origin_filename = origin_filename;
		this.stored_filename = stored_filename;
	}

	public int getFile_seq() {
		return file_seq;
	}

	public void setFile_seq(int file_seq) {
		this.file_seq = file_seq;
	}

	public int getMemberid() {
		return memberid;
	}

	public void setMemberid(int memberid) {
		this.memberid = memberid;
	}

	public String getOrigin_filename() {
		return origin_filename;
	}

	public void setOrigin_filename(String origin_filename) {
		this.origin_filename = origin_filename;
	}

	public String getStored_filename() {
		return stored_filename;
	}

	public void setStored_filename(String stored_filename) {
		this.stored_filename = stored_filename;
	}

	@Override
	public String toString() {
		return "FileDto [file_seq=" + file_seq + ", memberid=" + memberid + ", origin_filename=" + origin_filename
				+ ", stored_filename=" + stored_filename + "]";
	}

	
}
