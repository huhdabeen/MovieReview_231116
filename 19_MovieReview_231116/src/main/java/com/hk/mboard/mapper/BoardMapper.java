package com.hk.mboard.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.hk.mboard.dtos.BoardDto;
import com.hk.mboard.dtos.MovieInfoDto;

@Mapper
public interface BoardMapper {
	
	//글목록
	public List<BoardDto> getAllList();
	//글상세소조회
	public BoardDto getBoard(int board_seq);
	//글추가
	public boolean insertBoard(BoardDto dto);
	//글수정
	public boolean updateBoard(BoardDto dto);
	//글삭제
	public boolean mulDel(String [] seqs);
	
	//영화 정보
	public List<MovieInfoDto> movieAllList();
}
