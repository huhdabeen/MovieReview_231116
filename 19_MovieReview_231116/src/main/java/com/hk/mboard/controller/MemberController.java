package com.hk.mboard.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	        if (loggedInUser != null && loggedInUser.getMemberId() != 0) {
	            FileDto userImage = fileService.getFileInfo(loggedInUser.getMemberId());
	            model.addAttribute("userImage", userImage);
	            System.out.println("프로필 사진내놔");
	        }
	    }

	    return path;
	}
	
	@GetMapping(value = "/logout")
	public String logout(HttpServletRequest request) {
		System.out.println("로그아웃");
		request.getSession().invalidate();
		return "redirect:/";
	}

	//내정보조회	
	@GetMapping(value = "/userDetail")
	public String userDetail(@RequestParam(name = "memberId", defaultValue = "1") int memberId, Model model) {
	    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

	    if (principal instanceof MemberDto) {
	        memberId = ((MemberDto) principal).getMemberId();
	    } else if (principal instanceof String) {
	        // String 타입인 경우, 적절한 로직으로 MemberDto를 조회
	        // 예: memberId를 사용하여 MemberDto를 데이터베이스에서 조회
	        // memberId가 실제로는 String 타입이고, 문자열로 변환해서 사용
	        // 예: memberId = Integer.parseInt((String) principal);
	    }

	    MemberDto dto = memberService.getUserInfo(memberId);

	    // 유효값처리용
	    model.addAttribute("UpdateUserCommand", new UpdateUserCommand());
	    // 출력용
	    model.addAttribute("dto", dto);
	    System.out.println("Received memberId: " + memberId); // 로그 추가
//	    System.out.println(dto);
	    return "member/userDetail";
	}

	// 내정보수정
	@PostMapping(value = "/userUpdate")
	public String userUpdate(@Validated UpdateUserCommand updateUserCommand,
	                         BindingResult result,
	                         HttpServletRequest request,
	                         Model model) {
	    if (result.hasErrors()) {
	        System.out.println("수정할 내용을 모두 입력하세요");
	        model.addAttribute("dto", updateUserCommand);
	        return "member/userDetail";
	    }

	    System.out.println("Received UpdateUserCommand: " + updateUserCommand);

	    memberService.updateUser(updateUserCommand);
	    // 수정: memberId를 쿼리스트링으로 넘기는 대신, 세션에서 직접 읽어오도록 수정
	    MemberDto loggedInUser = (MemberDto) request.getSession().getAttribute("mdto");
	    if (loggedInUser != null) {
	        return "redirect:/userUpdatedInfo?memberId=" + updateUserCommand.getMemberId();
	    } else {
	        // 세션에 사용자 정보가 없는 경우의 예외 처리
	        return "redirect:/"; // 또는 적절한 경로로 이동
	    }
	}
	
	// 회원 정보 수정 후 수정된 정보를 조회하는 메서드
	@GetMapping("/userUpdatedInfo")
	public String userUpdatedInfo(@RequestParam("memberId") int memberId, Model model) {
	    MemberDto updatedInfo = memberService.getUserInfo(memberId);
	    model.addAttribute("updatedInfo", updatedInfo);
	    return "member/updatedInfo"; // 수정된 정보를 보여줄 뷰 페이지
	}
	
	
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