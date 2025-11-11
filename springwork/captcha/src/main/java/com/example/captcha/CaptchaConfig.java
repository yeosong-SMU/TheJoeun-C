package com.example.captcha;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;

@Configuration
public class CaptchaConfig {
	@Bean
	DefaultKaptcha captchaProducer() {
		Properties props = new Properties();
		props.put("kaptcha.image.width", "150");
		props.put("kaptcha.image.height", "50");
		props.put("kaptcha.textproducer.char.length", "5");
		props.put("kaptcha.textproducer.font.color", "blue");
		props.put("kaptcha.noise.color", "black");
		DefaultKaptcha kaptcha = new DefaultKaptcha();
		kaptcha.setConfig(new Config(props));
		return kaptcha;
	}
}