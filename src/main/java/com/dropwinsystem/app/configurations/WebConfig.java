package com.dropwinsystem.app.configurations;

import java.io.File;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.springbootstudy.bbs.interceptor.LoginCheckInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {

		registry.addViewController("/writeForm").setViewName("views/writeForm");
		registry.addViewController("/writeBoard").setViewName("views/writeForm");
		
		registry.addViewController("/noWriteForm").setViewName("views/noWriteForm");
		registry.addViewController("/noWriteNoticeList").setViewName("views/noWriteForm");

		registry.addViewController("/loginForm").setViewName("member/loginForm");

		registry.addViewController("/joinForm").setViewName("member/memberJoinForm");

	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {

		registry.addResourceHandler("/resources/files/**")
				.addResourceLocations("file:./src/main/resources/static/files/").setCachePeriod(1);

		String uploadDirPath = System.getProperty("user.home") + File.separator + "auction_uploads" + File.separator;
		registry.addResourceHandler("/auction_uploads/**").addResourceLocations("file:" + uploadDirPath);

		registry.addResourceHandler("/item/js/**").addResourceLocations("classpath:/static/js/");

		registry.addResourceHandler("/item/img/**").addResourceLocations("classpath:/static/img/");


	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new LoginCheckInterceptor())

			.addPathPatterns("/noticeDetail", "/noUpdateForm", "/noWriteForm", 
					"/noticeDetail/delete", "/memberUpdate*")
			.excludePathPatterns("/noticeDetail");

	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowedOriginPatterns("*")
				.allowedMethods("GET", "POST", "PUT", "DELETE").allowCredentials(true);
	}

}
