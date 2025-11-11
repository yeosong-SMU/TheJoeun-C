package com.example.product;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AppConfig implements WebMvcConfigurer {
	@Value("${app.upload-dir}")
	private String uploadDir;
	
	@Bean
	public CorsFilter corsFilter() {
		CorsConfiguration cfg = new CorsConfiguration();
		cfg.addAllowedOriginPattern("*");
		cfg.addAllowedHeader("*");
		cfg.addAllowedMethod("*");
		cfg.setAllowCredentials(false);
		// 모든 경로에 대해 cors 허용
		UrlBasedCorsConfigurationSource src = new UrlBasedCorsConfigurationSource();
		src.registerCorsConfiguration("/**", cfg);
		
		return new CorsFilter(src);
	}
	
	// 정적 리소스 핸들러 (파일 업로드 경로 매핑)
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/files/**").addResourceLocations("file:" + uploadDir + "/");
	}
}