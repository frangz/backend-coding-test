package com.alchemytec.web;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;

import com.alchemytec.Application;

/**
 * More Spring magic. This is the new web.xml.
 * 
 * @author frangz
 */
public class WebXml extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(
			SpringApplicationBuilder application) {
		return application.sources(Application.class);
	}

}
