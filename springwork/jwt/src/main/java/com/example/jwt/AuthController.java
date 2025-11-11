package com.example.jwt;

import java.time.Duration;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.groups.Default;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class AuthController {
	private final JwtUtil jwtUtil;
	private final MemberService memberService;
	
	@GetMapping("/")
	public String home() {
		return "redirect:/login";
	}
	
	@GetMapping("/login")
	public String loginForm(Model model) {
		if(!model.containsAttribute("form")) {
			model.addAttribute("form", new MemberDTO());
		}
		return "login";
	}
	
	// form 객체(MemberDTO)를 바인딩할 때 검증 수행
	// Login.class 그룹 : 로그인에 필요한 필드(아이디, 비밀번호 등)만 검증
	// Default.class 그룹 : 기본 검증 규칙
	// login 요청 : MemberDTO의 Login 그룹과 기본 그룹 제약을 동시에 적용
	
	// BindingResult br : 검증 결과를 담는 객체, 
	// 유효성 검증에서 에러가 나면 br.hasErrors()가 true
	@PostMapping("/login")
	public String login(HttpServletResponse res, 
			@Validated({MemberDTO.Login.class, Default.class}) @ModelAttribute("form") MemberDTO form,
			//로그인할 때 아이디가 빈값인지 체크(blank null)
			BindingResult br,   //검증결과
			Model model) {
		if(br.hasErrors()) {
			return "login";  //에러가 있으면(유효성 검증 통과x면) 로그인 화면으로
		}
		
		Member m = memberService.verifyLogin(form.getUsername(), form.getPassword());
		if(m == null) {
			model.addAttribute("error", "아이디 또는 비밀번호를 확인해 주세요.");
			return "login";
		}
		
		String token = jwtUtil.create(m.getUsername());  //로그인 성공. jwt 토큰 생성
		
		// 토큰은 암호화된 값이 아닌 인코딩된 값이므로 아래와 같이 디코딩 가능하다(log level : debug)
		Claims claims = Jwts.parser()
				.setSigningKey("THIS-IS-A-VERY-LONG-SECRET-KEY-AT-LEAST-32-BYTES-123456".getBytes())
				.parseClaimsJws(token).getBody();
		System.out.println("subject(username): " + claims.getSubject());
		System.out.println("issuedAt: " + claims.getIssuedAt());
		System.out.println("expiration: " + claims.getExpiration());
		
		Cookie cookie = new Cookie("ACCESS_TOKEN", token); //(쿠키이름, jwt토큰) 저장
		cookie.setHttpOnly(true);  // 자바스크립트 코드로 쿠키 접근 금지
		cookie.setPath("/");
		res.addCookie(cookie);
		
		return "admin".equalsIgnoreCase(m.getRole()) 
				? "redirect:/admin/members" : "redirect:/main";
	}
	
	@PostMapping("/logout")
	public String logout(HttpServletResponse res) {
		Cookie dead = new Cookie("ACCESS_TOKEN", null);  //쿠키 null값으로
		dead.setHttpOnly(true);
		dead.setPath("/");
		dead.setMaxAge(0);  //유효시간 0초 - 즉시 삭제
		res.addCookie(dead);
		return "redirect:/login";
	}
	
	@GetMapping("/join")
	public String joinForm(Model model) {
		if(!model.containsAttribute("form")) {
			model.addAttribute("form", new MemberDTO());
		}
		return "join";
	}
	
	@PostMapping("/join")
	public String join(
			@Validated({MemberDTO.Join.class, Default.class}) @ModelAttribute("form") MemberDTO form, 
			BindingResult br, Model model) {
		if(br.hasErrors())  //유효성검사
			return "join";
		try {
			memberService.join(form);
			return "redirect:/login";
		} catch (IllegalStateException e) {
			model.addAttribute("error", e.getMessage());
			return "join";
		}
	}
	
	@GetMapping("/main")
	public String main(HttpServletRequest req, 
			Model model) {
		Optional<String> tokenOpt = getTokenFromCookie(req);  //사용자 토큰 정보
		if(tokenOpt.isEmpty())
			return "redirect:/login";
		
		try {
			Claims claims = jwtUtil.parse(tokenOpt.get()).getBody();  //토큰 내용 검사
			String username = claims.getSubject();
			model.addAttribute("username", username);
			return "main";
		} catch (Exception e) {
			return "redirect:/login";
		}
	}
	
	private Optional<String> getTokenFromCookie(HttpServletRequest req) {
		Cookie[] cookies = req.getCookies();
		if(cookies == null)
			return Optional.empty();
		// 쿠키 배열을 순회하면서 access_token을 읽음(반복문 대체)
		return Arrays.stream(cookies).filter(c -> "ACCESS_TOKEN".equals(c.getName())).map(Cookie::getValue).findFirst();
	}
	
	//consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE : 폼데이터만 처리
	@PostMapping(value = "/api/login", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ResponseEntity<?> apiLogin(
			@Validated({MemberDTO.Login.class, Default.class}) @ModelAttribute("form") MemberDTO form, 
			BindingResult br, HttpServletResponse res) {
		if(br.hasErrors()) {
			return ResponseEntity.badRequest()
					.body(Map.of("ok", false, "code", "INVALID_FORM", "message", "입력값을 확인해 주세요."));
		}
		Member m = memberService.verifyLogin(form.getUsername(), form.getPassword());
		if(m == null) {
			return ResponseEntity.status(401)
					.body(Map.of("ok", false, "code", "BAD_CREDENTIALS", "message", "아이디 또는 비밀번호를 확인해 주세요."));
		}
		
		String token = jwtUtil.create(m.getUsername());
		
		// sameSite()
		// Strict : 같은 사이트에서만 쿠키 전송
		// Lax : 기본값. 외부사이트인 경우 get방식만 허용
		// None : 모두 허용
		// secure(false) : https만 허용
		
		ResponseCookie cookie = ResponseCookie.from("ACCESS_TOKEN", token).httpOnly(true).path("/")
				.maxAge(Duration.ofHours(1)).sameSite("Lax").secure(false).build();
		res.addHeader("Set-Cookie", cookie.toString());
		
		return ResponseEntity.ok(Map.of("ok", true, "username", m.getUsername(), "role", m.getRole()));
	}
	
	@PostMapping("/api/logout")
	public ResponseEntity<?> apiLogout(HttpServletResponse res) {
		ResponseCookie cookie = ResponseCookie.from("ACCESS_TOKEN", "").httpOnly(true)
				.secure(false).sameSite("Lax").path("/").maxAge(0).build();
		res.addHeader("Set-Cookie", cookie.toString());
		return ResponseEntity.ok(Map.of("ok", true));
	}
}