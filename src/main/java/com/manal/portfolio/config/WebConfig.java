package com.manal.portfolio.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/uploads/certifications/**")
                .addResourceLocations("file:src/main/resources/static/certifications/");

        // Images (si tu en as dans uploads/images)
        registry.addResourceHandler("/uploads/images/**")
                .addResourceLocations("classpath:/static/uploads/images/");
    }
}
