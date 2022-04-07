package pl.coderslab.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AppConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/user/login").setViewName("user/login");
        registry.addViewController("/user/logout").setViewName("user/logout");
        registry.addViewController("/start").setViewName("/user/userstart");
        registry.addViewController("/403").setViewName("403");
    }
}
