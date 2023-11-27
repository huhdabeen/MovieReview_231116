package com.hk.mboard.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartRequest;

import com.hk.mboard.dtos.FileDto;
import com.hk.mboard.dtos.MemberDto;
import com.hk.mboard.service.FileService;
import com.hk.mboard.command.AddUserCommand;
import com.hk.mboard.command.LoginCommand;
import com.hk.mboard.command.UpdateUserCommand;
import com.hk.mboard.service.MemberService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.websocket.Session;

@Controller
@RequestMapping(value = "/user")
public class MemberController {
	
	@Autowired
	private MemberService memberService;
	@Autowired
	private FileService fileService;

	@GetMapping(value = "/addUser")
	public String addUserForm(Model model) {
		System.out.println("회원가입폼 이동");

		//회원가입폼에서 addUserCommand객체를 사용하는 코드가 작성되어 있어서 
		//null일경우 오류가 발생하기때문에 보내줘야 함
		model.addAttribute("addUserCommand", new AddUserCommand());

		return "member/addUserForm";
	}
	
	
	@PostMapping(value = "/addUser")
	public String addUser(@Validated AddUserCommand addUserCommand
						  ,BindingResult result
						  ,MultipartRequest multipartRequest //multipart data를 처리할때 사용
						  ,HttpServletRequest request
						  ,Model model) throws IllegalStateException, IOException {
		System.out.println("회원가입하기");
		
		if(result.hasErrors()) {
			System.out.println("회원가입 유효값 오류");
			return "member/addUserForm";
		}
		
		try {
			memberService.addUser(addUserCommand,multipartRequest,request);
			System.out.println("회원가입 성공");
			return "redirect:/";
		} catch (Exception e) {
			System.out.println("회원가입 실패");
			e.printStackTrace();
			return "redirect:addUser";
		}
	}
	
	@ResponseBody
	@GetMapping(value = "/idChk")
	public Map<String, String> idChk(String id){
		System.out.println("ID중복체크");
		
		String resultId=memberService.idChk(id);
		//json객체로 보내기 위해 Map에 담아서 응답
		//text라면 그냥 String으로 보내도 됨
		Map<String, String> map=new HashMap<>();
		map.put("id", resultId);
		return map;
	}
	
	@ResponseBody
	@GetMapping(value = "/nameChk")
	public Map<String, String> nameChk(String name){
		System.out.println("닉네임 중복체크");
		
		String resultName=memberService.nameChk(name);
		//json객체로 보내기 위해 Map에 담아서 응답
		//text라면 그냥 String으로 보내도 됨
		Map<String, String> map=new HashMap<>();
		map.put("name", resultName);
		return map;
	}
	
	@GetMapping(value = "/download")
	public void download(int file_seq, HttpServletRequest request
			                         , HttpServletResponse response) throws UnsupportedEncodingException {
		
		FileDto fdto=fileService.getFileInfo(file_seq);//파일정보가져오기
		
		fileService.fileDownload(fdto.getOrigin_filename()
				                ,fdto.getStored_filename()
				                ,request,response);
	}
	//
	//로그인 폼 이동
	@GetMapping(value = "/login")
	public String loginForm(Model model) {
		model.addAttribute("loginCommand",new LoginCommand());
		return "member/login";
	}
	
	
	@PostMapping(value = "/login")
	public String login(@Validated LoginCommand loginCommand,
	                    BindingResult result,
	                    Model model,
	                    HttpServletRequest request) {
	    if (result.hasErrors()) {
	        System.out.println("로그인 유효값 오류");
	        return "member/login";
	    }

	    String path = memberService.login(loginCommand, request, model);

	    if ("home".equals(path)) {
	        MemberDto loggedInUser = (MemberDto) request.getSession().getAttribute("mdto");
	        if (loggedInUser != null && loggedInUser.getFile_seq() != 0) {
	            FileDto userImage = fileService.getFileInfo(loggedInUser.getFile_seq());
	            model.addAttribute("userImage", userImage);
	        }
	    }

	    return path;
	}
	//
	
	//로그인 폼 이동
//	@GetMapping(value = "/login")
//	public String loginForm(Model model) {
//		model.addAttribute("loginCommand",new LoginCommand());
//		return "member/login";
//	}
//	
//	//로그인 실행
//	@PostMapping(value = "/login")
//	public String login(@Validated LoginCommand loginCommand
//						,BindingResult result
//						,Model model
//						,HttpServletRequest request) {
//		if(result.hasErrors()) {
//			System.out.println("로그인 유효값 오류");
//			return "member/login";
//		}
//		
//		String path=memberService.login(loginCommand, request, model);
//		
//		return path;
//	}
	
	@GetMapping(value = "/logout")
	public String logout(HttpServletRequest request) {
		System.out.println("로그아웃");
		request.getSession().invalidate();
		return "redirect:/";
	}

	//내정보조회
	@GetMapping(value = "/UserDetail")
	public String updateUserForm(int memberId, Model model) {
//		System.out.println("회원정보 수정폼");
		MemberDto dto=memberService.getUser(memberId);
		
		//유효값처리용
		model.addAttribute("UpdateUserCommand",new UpdateUserCommand());
		//출력용
		model.addAttribute(dto);		
		System.out.println(dto);		
		return "member/userDetail";
	}
	
	//내정보수정
	@PostMapping("/edit")
	public String updateUser(@Validated UpdateUserCommand updateUserCommand,
						   BindingResult result) {
		if(result.hasErrors()) {
			System.out.println("수정할 정보를 모두 입력하세요");
			return "member/userDetail";
		}
		memberService.updateUser(updateUserCommand);
		
		return "redirect:/member/userDetail?memberId="+updateUserCommand.getMemberId();
	}
	
//	@GetMapping("/edit") // 내정보보기
//	public void updateUserForm(HttpServletRequest request, Model model) {
//		session.removeAttribute("msg"); // 로그인성공 메시지 초기화
//		String id = (String) session.getAttribute("loginId");
//		MemberVo vo = service.getMember(id);
//		map.addAttribute("vo", vo);
//	}
//
//	@PostMapping("/edit") // 수정
//	public String edit(MemberVo vo) {
//		service.editMember(vo);
//		return "redirect:/member/edit";
//	}
	
//	 탈퇴
//	@RequestMapping("/out")
//	public String out(HttpSession session) {
//		String id = (String) session.getAttribute("loginId");
//		service.delMember(id);
//		session.invalidate();
//		//return "redirect:/member/logout";
//		return "index";
//	}
	
}