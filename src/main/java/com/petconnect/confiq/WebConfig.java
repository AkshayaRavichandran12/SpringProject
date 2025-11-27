package com.petconnect.confiq;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // serve files saved in 'uploads' directory at http://localhost:8000/uploads/{filename}
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:uploads/");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")                  // allow all endpoints
                .allowedOrigins("*")               // allow all origins (frontend can be local file or another port)
                .allowedMethods("GET","POST","PUT","DELETE","OPTIONS") // allowed HTTP methods
                .allowedHeaders("*");
    }
}
