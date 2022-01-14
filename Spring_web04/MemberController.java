package kr.co.controller;

import java.util.HashMap;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.co.service.MemberService;
import kr.co.vo.MemberVO;

@Controller
@RequestMapping("/member/*")
public class MemberController {

	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);
	
	@Inject
	MemberService service;
	
	@Inject
	BCryptPasswordEncoder pwdEncoder;

	// 회원가입 페이지로 이동
	@RequestMapping(value = "/reg")
	public String reg() throws Exception{
		return "member/register";
	}
	
	// 회원가입 get
	/*
	 * @RequestMapping(value = "/register", method = RequestMethod.GET) public void
	 * getRegister() throws Exception { logger.info("get register"); }
	 */
	
	// 회원가입 post
	@RequestMapping(value = "/registery", method = RequestMethod.POST)
	public String postRegister(MemberVO vo) throws Exception {
		logger.info("post register");
		//int result = service.idChk(vo);
		System.out.println(vo.getUserId());
		System.out.println(vo.getUserPass());
		System.out.println(vo.getUserName());
		try {
/*			if(result == 1) {
				return "/member/register";
			} else if(result == 0) {*/
				String inputPass = vo.getUserPass();
				String pwd = pwdEncoder.encode(inputPass);
				vo.setUserPass(pwd);
				
				service.register(vo);
				/* } */
			// 요기에서~ 입력된 아이디가 존재한다면 -> 다시 회원가입 페이지로 돌아가기 
			// 존재하지 않는다면 -> register
		} catch (Exception e) {
			throw new RuntimeException();
		}
		return "redirect:/";
	}
	
	// 로그인 화면으로 이동
	@RequestMapping("/loginForm")
	public String loginForm() throws Exception{
		return "member/loginForm";
	}	
	
	// 로그인 처리 post
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(MemberVO vo, HttpSession session, RedirectAttributes rttr) throws Exception{
		logger.info("post login");
		
		session.getAttribute("member");
		MemberVO login = service.login(vo);
		
		boolean pwdMatch;
		if(login != null) {
			pwdMatch = pwdEncoder.matches(vo.getUserPass(), login.getUserPass());
		} else {
			pwdMatch = false;
		}

		if(login != null && pwdMatch == true) {
			session.setAttribute("member", login);
			rttr.addFlashAttribute("msg", "로그인 성공");
			return "redirect:/";
		} else {
			session.setAttribute("member", null);
			rttr.addFlashAttribute("msg", "아이디 또는 비밀번호가 올바르지 않습니다.");
			return "redirect:/member/loginForm";
		}
	}
	
	// 로그아웃 post
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpSession session) throws Exception{
		session.invalidate();		
		return "redirect:/";
	}
	
	// 회원정보 수정 get
	@RequestMapping(value="/memberUpdateView", method = RequestMethod.GET)
	public String registerUpdateView() throws Exception{
		return "member/memberUpdateView";
	}
	
	// 회원정보 수정  post
	@RequestMapping(value="/memberUpdate", method = RequestMethod.POST)
	public String registerUpdate(MemberVO vo, HttpSession session) throws Exception{
		
/*		MemberVO login = service.login(vo);
		
		boolean pwdMatch = pwdEncoder.matches(vo.getUserPass(), login.getUserPass());
		if(pwdMatch) {
			service.memberUpdate(vo);
			session.invalidate();
		}else {
			return "member/memberUpdateView";
		}*/
		service.memberUpdate(vo);
		session.invalidate();
		return "redirect:/";
	}
	
	// 회원 탈퇴 get
	@RequestMapping(value="/memberDeleteView", method = RequestMethod.GET)
	public String memberDeleteView() throws Exception{
		return "member/memberDeleteView";
	}
	
	// 회원 탈퇴 post
	@RequestMapping(value="/memberDelete", method = RequestMethod.POST)
	public String memberDelete(MemberVO vo, HttpSession session, RedirectAttributes rttr) throws Exception{
		
		service.memberDelete(vo);
		session.invalidate();
		
		return "redirect:/";
	}
	
	// 패스워드 체크
	@ResponseBody
	@RequestMapping(value="/passChk", method = RequestMethod.POST)
	public boolean passChk(MemberVO vo) throws Exception {

		MemberVO login = service.login(vo);
		boolean pwdChk = pwdEncoder.matches(vo.getUserPass(), login.getUserPass());
		return pwdChk;
	}
	
	int result = 0;
	
	// 아이디 중복 체크 POST
	@ResponseBody
	@RequestMapping(value="/idChk", method = RequestMethod.POST)
	public int idChk(MemberVO vo, HttpSession ses) throws Exception {
		result = service.idChk(vo);
		System.out.println("반환결과 : "+result);
		if(result==0) {
			ses.setAttribute("msg", "ok");
		} else {
			ses.setAttribute("msg", "no");
		}
		return result;
	}
	
	/*
	@RequestMapping(value="/test", produces="application/json;charset=UTF-8", method=RequestMethod.POST)
	public @ResponseBody HashMap<String, Object> test(@RequestBody HashMap<String, Object> params) {
		System.out.println((int)params.get("age")*9);
		
		return params;
	}
	
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
	<script type="text/javascript">
		var data = {};
		data.name = "name";
		data.age = 19;
		$(document).ready(function(){
			$.ajax({
				url: "testtest",
				data: JSON.stringify(data),
				type: "POST",
				dataType: "json",
				contentType: "application/json; charset=utf-8",
				success: function(data){
					console.log(data);
				}
			});
		});
	</script>
	*/
	
	// 아이디 중복 체크 GET
	@ResponseBody
	@RequestMapping(value="/idChk", method = RequestMethod.GET)
	public int idChk2(@RequestParam("userId") String userid, HttpSession ses) throws Exception {
		MemberVO mem = new MemberVO(); 
		mem.setUserId(userid);
		result = (int) service.idChk(mem);
		System.out.println("결과 : "+result);
		if(result==0) {
			ses.setAttribute("msg", "ok");
		} else {
			ses.setAttribute("msg", "no");
		}
		return result;
	}
}