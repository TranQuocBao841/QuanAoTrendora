package com.example.Trendora.DuAn.Config;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // ánh xạ /uploads/** ra thư mục thực ngoài ổ D
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:/D:/ShopImages/");
    }
}
