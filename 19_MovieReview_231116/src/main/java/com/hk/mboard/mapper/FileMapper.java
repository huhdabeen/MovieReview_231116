package com.hk.mboard.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.hk.mboard.dtos.FileBoardDto;

@Mapper
public interface FileMapper {
	   
	   //파일 정보 추가
	   public boolean insertFileBoard(FileBoardDto dto);
	   //팡리 정보 조회
	   public FileBoardDto getFileInfo(int file_seq);
}
