package com.example.jwt;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class MemberDTO {
	@NotBlank(groups = {Login.class, Join.class})
	private String username;
	
	@NotBlank(groups = {Login.class, Join.class})
	private String password;
	
	private String displayName;
	private String role;
	
	public interface Login {  //로그인 검증 그룹
	}
	
	public interface Join {}  //회원가입 검증 그룹
}