package com.hk.mboard.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.hk.mboard.dtos.FileDto;

@Mapper
public interface FileMapper {
	   
	   //파일 정보 추가
	   public boolean insertFileBoard(FileDto dto);
	   //파일 정보 조회
	   public FileDto getFileInfo(int file_seq);
}
