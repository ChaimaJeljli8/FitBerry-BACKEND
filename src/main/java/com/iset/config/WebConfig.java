package com.iset.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import java.nio.file.Paths;

@Configuration
public class WebConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:4200")
                        .allowedMethods("*");
            }

            @Override
            public void addResourceHandlers(ResourceHandlerRegistry registry) {
                // Get the absolute path to uploads directory
                String uploadPath = Paths.get("uploads").toAbsolutePath().toString();
                String fileLocation = "file:///" + uploadPath.replace("\\", "/") + "/";
                
                System.out.println("Registering resource handler for /uploads/");
                System.out.println("Mapping to: " + fileLocation);
                
                // Map /uploads/** URLs to the uploads directory
                registry.addResourceHandler("/uploads/**")
                        .addResourceLocations(fileLocation)
                        .setCachePeriod(3600);
            }
        };
    }
}