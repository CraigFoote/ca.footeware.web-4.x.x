/*******************************************************************************
 * Copyright (c) 2016 Footeware.ca
 *******************************************************************************/
package ca.footeware.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/home").setViewName("home");
		registry.addViewController("/").setViewName("home");
		registry.addViewController("/photos").setViewName("photos");
		registry.addViewController("/login").setViewName("login");
		registry.addViewController("/gear").setViewName("gear");
		registry.addViewController("/webcam").setViewName("webcam");
		registry.addViewController("/jokes").setViewName("jokes");
		registry.addViewController("/joke").setViewName("joke");
		registry.addViewController("/addjoke").setViewName("addjoke");
	}

}