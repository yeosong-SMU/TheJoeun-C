package com.example.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Bean
	protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
		http.csrf(AbstractHttpConfigurer::disable)
			.authorizeHttpRequests(request -> request.requestMatchers("/admin/**")
				.hasAnyRole("ADMIN")
				.requestMatchers("/**")
				.permitAll()
			)
			.formLogin(form -> form.loginPage("/user/login.do")
				.loginProcessingUrl("/user/login_check.do")
				.usernameParameter("userid")
				.passwordParameter("passwd")
				.successHandler(new SuccessHandler())
				.failureHandler(new FailureHandler())
			);
		return http.build();
	}
	
	@Bean
	PasswordEncoder pwdEncoder() {
		return new BCryptPasswordEncoder();
	}
}