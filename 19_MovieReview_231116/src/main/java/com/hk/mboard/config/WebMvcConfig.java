package com.hk.mboard.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer{
	
	   //구현된 interceptor 객체를 등록한다.
	   @Override
	   public void addInterceptors(InterceptorRegistry registry) {
	      registry.addInterceptor(new LoginInterceptor())
	            .addPathPatterns("/**")   //전체 요청에 대해 적용
	            .excludePathPatterns("/error","/board/**","/","/user/**","/css/**","/js/**"); //제외
	   }

}
