package com.hk.mboard.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import com.hk.mboard.command.AddUserCommand;
import com.hk.mboard.command.LoginCommand;
import com.hk.mboard.dtos.FileDto;
import com.hk.mboard.dtos.MemberDto;
import com.hk.mboard.mapper.FileMapper;
import com.hk.mboard.mapper.MemberMapper;
import com.hk.mboard.status.RoleStatus;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class MemberService {

	@Autowired
	private MemberMapper memberMapper;
	@Autowired
	private FileMapper fileMapper;
	@Autowired
	private FileService fileService;
	@Autowired
	private PasswordEncoder passwordEncoder;

//	@Transactional
	public boolean addUser(AddUserCommand addUserCommand, 
						   MultipartRequest multipartRequest,
						   HttpServletRequest request) 
						   throws IllegalStateException, IOException {
		MemberDto mdto=new MemberDto();
		mdto.setId(addUserCommand.getId());
		mdto.setName(addUserCommand.getName());

		//password암호화하여 저장하자
		mdto.setPassword(passwordEncoder.encode(addUserCommand.getPassword()));

		mdto.setEmail(addUserCommand.getEmail());
		mdto.setRole(RoleStatus.USER+""); //등급추가

		memberMapper.addUser(mdto);//새글 추가
		System.out.println("파일첨부여부:"
		+multipartRequest.getFiles("filename").get(0).isEmpty());
		//첨부된 파일들이 있는 경우
		if(!multipartRequest.getFiles("filename").get(0).isEmpty()) {
			//파일 저장경로 설정: 절대경로, 상대경로
			String filepath=request.getSession().getServletContext()
					       .getRealPath("upload");
			System.out.println("파일저장경로:"+filepath);
			//파일업로드 작업은 FileService쪽에서 업로드하고 업로드된 파일정보 반환
			List<FileDto>uploadFileList
			      =fileService.uploadFiles(filepath, multipartRequest);
			//파일정보를 DB에 추가
			//글추가할때 board_seq 증가된 값---> file정보를 추가할때 사용
			//Testboard: board_seq PK       board_seq FK
			for (FileDto fDto : uploadFileList) {
				fileMapper.insertFileBoard(
				 new FileDto(0, mdto.getMemberId(),//증가된 board_seq값을 넣는다 
						             fDto.getOrigin_filename(),
						 			 fDto.getStored_filename())
				                          );
			}
		}
		return memberMapper.addUser(mdto);
}	
	

	public String idChk(String id) {
		return memberMapper.idChk(id);
	}
	
	public String nameChk(String name) {
		return memberMapper.nameChk(name);
	}
	
	public String login(LoginCommand loginCommand
						,HttpServletRequest request
						,Model model) {
		MemberDto dto=memberMapper.loginUser(loginCommand.getId());
		String path="home";
		if(dto!=null) {
			//로그인 폼에서 입력받은 패스워드값과 DB에 암호화된 패스워드 비교
			if(passwordEncoder.matches(loginCommand.getPassword(),dto.getPassword())) {
				System.out.println("패스워드 같음: 회원이 맞음");
				request.getSession().setAttribute("mdto", dto);
				return path;
			}else {
				System.out.println("패스워드 틀림");
				model.addAttribute("msg","패스워드를 확인하세요.");
				path="member/login";
			}
		}else {
			System.out.println("회원이 아닙니다.");
			model.addAttribute("msg","아이디를 확인하세요.");
			path="member/login";
		}
		return path;
	}
}

