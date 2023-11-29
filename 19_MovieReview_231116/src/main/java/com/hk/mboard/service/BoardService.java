package com.hk.mboard.service;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartRequest;

import com.hk.mboard.command.InsertBoardCommand;
import com.hk.mboard.command.UpdateBoardCommand;
import com.hk.mboard.dtos.BoardDto;
import com.hk.mboard.dtos.MovieInfoDto;
import com.hk.mboard.mapper.BoardMapper;
import com.hk.mboard.mapper.MemberMapper;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class BoardService {
	
	@Autowired
	private BoardMapper boardMapper;
	@Autowired
	private MemberMapper memberMapper;

	
	//글목록 조회
	public List<BoardDto> getAllList(){
		return boardMapper.getAllList();
	}
	
	//영화정보 조회
	public List<MovieInfoDto> movieAllList(String movie_nm){
		return boardMapper.movieAllList(movie_nm);
	}

	//글 추가, 파일업로드및 파일정보 추가
	@Transactional
	public void insertBoard(InsertBoardCommand insertBoardCommand
			              , MultipartRequest multipartRequest
			              , HttpServletRequest request) 
			              throws IllegalStateException, IOException {
		//command:UI -> dto:DB 데이터 옮겨담기
		BoardDto boardDto=new BoardDto();
		boardDto.setName(insertBoardCommand.getId());
		boardDto.setTitle(insertBoardCommand.getTitle());
		boardDto.setContent(insertBoardCommand.getContent());
		// 위에 거,,,
//		> 여기서 스플릿으로 나눠서 0번째 장르, 1번째 영화로 담기
		
		//새글을 추가할때 파라미터로 전달된 boardDto객체에 자동으로,
		//증가된 board_seq값이 저장
		boardMapper.insertBoard(boardDto);//새글 추가	
	}
	
	//상세내용조회
	public BoardDto getBoard(int board_seq) {
		return boardMapper.getBoard(board_seq);
	}
	
	//수정하기
	public boolean updateBoard(UpdateBoardCommand updateBoardCommand) {
		//command:UI ---> DTO:DB 
		BoardDto dto=new BoardDto();
		dto.setBoard_seq(updateBoardCommand.getBoard_seq());
		dto.setTitle(updateBoardCommand.getTitle());
		dto.setContent(updateBoardCommand.getContent());
		return boardMapper.updateBoard(dto);
	}

	public boolean mulDel(String[] seqs) {
		return boardMapper.mulDel(seqs);
	}
}



