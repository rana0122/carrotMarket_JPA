package miniproject.carrotmarket1.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {


    @Value("${file.upload-dir}")
    private String uploadDir;

    @Value("${file.upload-dir-item}")
    private String uploadDirItem;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 프로필 이미지 정적 리소스 경로 설정
        registry.addResourceHandler("/profileImages/**")
                .addResourceLocations("file:" + uploadDir);

        // 상품 이미지 정적 리소스 경로 설정
        registry.addResourceHandler("/itemimages/**")
                .addResourceLocations("file:" + uploadDirItem);
    }
}