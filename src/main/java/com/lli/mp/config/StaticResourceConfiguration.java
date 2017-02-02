package com.lli.mp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class StaticResourceConfiguration extends WebMvcConfigurerAdapter {

	private String resourceDirAudio;

	@Autowired
	public StaticResourceConfiguration(@Value("${RESOURCE_DIR_AUDIO}") String resourceDirAudio) {
		this.resourceDirAudio = resourceDirAudio;
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/audio/**").addResourceLocations("file:"+ resourceDirAudio);
	}
}
