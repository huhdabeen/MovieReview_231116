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
import com.hk.mboard.command.UpdateUserCommand;
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

//		memberMapper.addUser(mdto);//새글 추가
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
						new FileDto(0, fDto.getOrigin_filename(), fDto.getStored_filename())
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
	
	
	public String login(LoginCommand loginCommand, HttpServletRequest request, Model model) {
        MemberDto dto = memberMapper.loginUser(loginCommand.getId());
        String path = "home";

        if (dto != null) {
            if ("Y".equals(dto.getDelflag())) {
                System.out.println("탈퇴한 회원입니다.");
                model.addAttribute("msg", "탈퇴한 회원입니다.");
                path = "member/login";
            } else {
                if (passwordEncoder.matches(loginCommand.getPassword(), dto.getPassword())) {
                    System.out.println("패스워드 같음: 회원이 맞음");

                    // 세션에 사용자 정보 저장
                    request.getSession().setAttribute("mdto", dto);
                    System.out.println(dto);
                    // 사용자가 등록한 프로필 이미지 정보 조회
                    if (dto.getFile_seq() != 0) {
                        FileDto userImage = fileService.getFileInfo(dto.getFile_seq());
                        model.addAttribute("userImage", userImage);
                    }

                    return path;
                } else {
                    System.out.println("패스워드 틀림");
                    model.addAttribute("msg", "패스워드를 확인하세요.");
                    path = "member/login";
                }
            }
        } else {
            System.out.println("회원이 아닙니다.");
            model.addAttribute("msg", "아이디를 확인하세요.");
            path = "member/login";
        }

        return path;
    }


    //회원상세
	public MemberDto getUserInfo(int memberId) {
		return memberMapper.getUserInfo(memberId);
	}
    
    //회원정보수정하기
	@Transactional
	public boolean updateUser(UpdateUserCommand updateUserCommand) {
	    MemberDto dto = new MemberDto();
	    dto.setMemberId(updateUserCommand.getMemberId());
	    dto.setName(updateUserCommand.getName());
	    dto.setEmail(updateUserCommand.getEmail());

	    try {
	        // 실제로 회원 정보를 수정하는 쿼리 호출
	        boolean updateResult = memberMapper.updateUser(dto);

	        if (updateResult) {
	            System.out.println("회원 정보가 성공적으로 업데이트되었습니다.");
	            
	        } else {
	            System.out.println("회원 정보 업데이트에 실패했습니다.");
	        }

	        return updateResult;
	    } catch (Exception e) {
	        // 예외 발생 시 로그에 출력
	        System.out.println("회원 정보 업데이트 중 예외 발생: " + e.getMessage());
	        e.printStackTrace();
	        return false;
	    }
	}
	
	//회원탈퇴하기
	@Transactional
	public boolean delUser(int memberId) {
        try {
            // 회원 탈퇴를 위해 MemberMapper의 delUser 메서드 호출
            memberMapper.delUser(memberId);

            // 탈퇴한 회원의 파일 정보도 삭제 (예: 파일 정보는 삭제되지 않도록 주의)
            // fileMapper.deleteFileBoardByMemberId(memberId);

            return true;
        } catch (Exception e) {
            // 예외 발생 시 로그 출력
            System.out.println("회원 탈퇴 중 예외 발생: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}

	
    
	


