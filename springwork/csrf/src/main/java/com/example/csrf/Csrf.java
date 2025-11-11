package com.example.csrf;

import java.security.SecureRandom;
import java.util.Base64;

public class Csrf {
	// 내부적으로 강력한 random seed 사용, 지정할 수 없음
	private static final SecureRandom RND = new SecureRandom();
	
	public static String newToken() {
		byte[] buf = new byte[32];
		RND.nextBytes(buf);   // -128~127  범위의 랜덤 바이트값
		// URL safe ASCII 문자열로 변환
		return Base64.getUrlEncoder().withoutPadding().encodeToString(buf);
	}
}