package com.hk.mboard.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartRequest;

import com.hk.mboard.command.DelBoardCommand;
import com.hk.mboard.command.InsertBoardCommand;
import com.hk.mboard.command.UpdateBoardCommand;
import com.hk.mboard.dtos.BoardDto;
import com.hk.mboard.dtos.FileDto;
import com.hk.mboard.dtos.MovieInfoDto;
import com.hk.mboard.service.BoardService;
import com.hk.mboard.service.FileService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping(value = "/board")
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	@Autowired
	private FileService fileService;
	
	@GetMapping(value = "/boardList")
	public String boardList(Model model) {
		System.out.println("글목록 보기");
		
		List<BoardDto> list=boardService.getAllList();
		model.addAttribute("list", list);
		model.addAttribute("delBoardCommand", new DelBoardCommand());
		return "board/boardList";// forward 기능, "redirect:board/boardList"
	}

	@RequestMapping(value="/movieList", method={RequestMethod.POST,RequestMethod.GET})
	@ResponseBody 
	public Map<String, List<MovieInfoDto>> movieList(String movie_nm) {
		System.out.println("영화리스트:"+movie_nm);
		
		Map<String, List<MovieInfoDto>> map=new HashMap<>();
		List<MovieInfoDto> mlist=boardService.movieAllList(movie_nm);
		map.put("mlist", mlist);
		System.out.println(mlist.size());
		return map;
	}
		
	@GetMapping(value = "/boardInsert")
	public String boardInsertForm(Model model) {
		model.addAttribute("insertBoardCommand", new InsertBoardCommand());
		return "board/boardInsertForm";
	}
	
	@PostMapping(value = "/boardInsert")
	public String boardInsert(@Validated InsertBoardCommand insertBoardCommand 
			                ,BindingResult result
			                ,MultipartRequest multipartRequest //multipart data를 처리할때 사용
							,HttpServletRequest request
			                ,Model model) throws IllegalStateException, IOException {
		if(result.hasErrors()) {
			System.out.println("글을 모두 입력하세요");
			return "board/boardInsertForm";
		}
		
		boardService.insertBoard(insertBoardCommand,multipartRequest
				                ,request);
		
		return "redirect:/board/boardList";
	}
	
	//상세보기
	@GetMapping(value = "/boardDetail" )
	public String boardDetail(int board_seq, Model model) {
		BoardDto dto=boardService.getBoard(board_seq);
		
		//유효값처리용
		model.addAttribute("updateBoardCommand", new UpdateBoardCommand());
		//출력용
		model.addAttribute("dto", dto);
		System.out.println(dto);
		return "board/boardDetail";
	}
	
	//수정하기
	@PostMapping(value = "/boardUpdate")
	public String boardUpdate(
				@Validated UpdateBoardCommand updateBoardCommand
				,BindingResult result) {
		
		if(result.hasErrors()) {
			System.out.println("수정내용을 모두 입력하세요");
			return "board/boardDetail";
		}
		
		boardService.updateBoard(updateBoardCommand);
		
		return "redirect:/board/boardDetail?board_seq="
				+ updateBoardCommand.getBoard_seq();
	}
	
	@GetMapping(value = "/download")
	public void download(int file_seq, HttpServletRequest request
			                         , HttpServletResponse response) throws UnsupportedEncodingException {
		
		FileDto fdto=fileService.getFileInfo(file_seq);//파일정보가져오기
		
		fileService.fileDownload(fdto.getOrigin_filename()
				                ,fdto.getStored_filename()
				                ,request,response);
	}
	
	@RequestMapping(value="mulDel",method = {RequestMethod.POST,RequestMethod.GET})
	public String mulDel(@Validated DelBoardCommand delBoardCommand
						 ,BindingResult result
			             , Model model) {
		if(result.hasErrors()) {
			System.out.println("최소하나 체크하기");
			List<BoardDto> list=boardService.getAllList();
			model.addAttribute("list", list);
			return "board/boardlist";
		}
		boardService.mulDel(delBoardCommand.getSeq());
		System.out.println("글삭제함");
		return "redirect:/board/boardList";
	}
}












