package toyprj.toyprj_noticeBoard.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    private String resourcePath = "/upload/**"; // view에서 접근 할 경로(detail에 upload 경로 부분)
    private String savePath = "file:///Users/hwangseon-yeong/springboot_img/"; // 실제 파일 저장 경로
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(resourcePath)
                .addResourceLocations(savePath);
    }

}

