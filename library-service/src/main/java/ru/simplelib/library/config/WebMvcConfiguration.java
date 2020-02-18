package ru.simplelib.library.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import ru.simplelib.library.config.auth.VerifyAccessInterceptor;

@Configuration
public class WebMvcConfiguration extends WebMvcConfigurationSupport {

    @Autowired
    private VerifyAccessInterceptor verifyAccessInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(verifyAccessInterceptor).addPathPatterns("/**");
    }


}
