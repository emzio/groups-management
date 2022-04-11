package pl.coderslab.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//import pl.coderslab.converter.CanceledClassesConverter;

@Configuration
public class AppConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/logout").setViewName("logout");
        registry.addViewController("/user/start").setViewName("user/userstart");
        registry.addViewController("/403").setViewName("403");
    }
//    @Override
//    public void addFormatters(FormatterRegistry registry) {
//        registry.addConverter(getCanceledClassesConverter());
//    }
//    @Bean
//    public CanceledClassesConverter getCanceledClassesConverter() {
//        return new CanceledClassesConverter();
//    }

}
