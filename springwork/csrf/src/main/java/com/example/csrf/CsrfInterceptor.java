package com.example.csrf;

import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CsrfInterceptor implements HandlerInterceptor {
	@Override
	public boolean preHandle(HttpServletRequest req, 
			HttpServletResponse res,
			Object handler) throws Exception {
		if ("POST".equalsIgnoreCase(req.getMethod())) {
			String sessionToken = (String) req.getSession().getAttribute("CSRF_TOKEN");
			String formToken = req.getParameter("_csrf");
			
			if (sessionToken == null || formToken == null || !sessionToken.equals(formToken)) {
				res.sendError(HttpServletResponse.SC_FORBIDDEN, "Invalid CSRF token");
				return false;
			}
			
			// 검증이 끝나면 토큰 교체 (1회용)
			req.getSession().setAttribute("CSRF_TOKEN", Csrf.newToken());
		}
		return true;
	}
}