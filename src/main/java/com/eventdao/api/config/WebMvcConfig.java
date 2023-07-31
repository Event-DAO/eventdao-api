package com.eventdao.api.config;

import com.eventdao.api.resolver.CustomerArgumentResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		// @formatter:off
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("HEAD", "OPTIONS", "GET", "POST", "PUT", "PATCH", "DELETE")
                .maxAge(3600);
        // @formatter:on
	}

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		resolvers.add(new CustomerArgumentResolver());
	}


	//	@Override
//	public void addArgumentResolvers(List<org.springframework.web.method.support.HandlerMethodArgumentResolver> resolvers) {
//		resolvers.add(new CustomerArgumentResolver());
//	}
}