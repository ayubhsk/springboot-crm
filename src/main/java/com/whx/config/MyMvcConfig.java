package com.whx.config;

import com.whx.settings.handler.LoginHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyMvcConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("login.html");
        registry.addViewController("/index.html").setViewName("login.html");
        registry.addViewController("/workbench/main/index.html").setViewName("workbench/main/index");
        registry.addViewController("/workbench/activity/index.html").setViewName("workbench/activity/index");
        registry.addViewController("/workbench/index.html").setViewName("workbench/index");
        registry.addViewController("/workbench/clue/index.html").setViewName("workbench/clue/index");
    }


    //使用登录拦截器

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginHandler()).
                addPathPatterns("/**").
                excludePathPatterns("/","/index.html","/settings/user/login.do","/ECharts/**","/image/**","/jquery/**");
    }


}
