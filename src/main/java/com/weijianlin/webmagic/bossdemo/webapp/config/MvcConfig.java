package com.weijianlin.webmagic.bossdemo.webapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.freemarker.FreeMarkerView;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

@Configuration
@EnableWebMvc   //开启springMVC支持,若无此句,重写WebMvcConfigurerAdapter方法无效
@ComponentScan("com.weijianlin.webmagic.bossdemo")
@EnableScheduling
//继承WebMvcConfigurerAdapter类,重写其方法可对Spring MVC进行配置
public class MvcConfig implements WebMvcConfigurer {

    @Bean
    public FreeMarkerViewResolver viewResolver(){
        //配置视图解析器
        FreeMarkerViewResolver viewResolver = new FreeMarkerViewResolver();
        viewResolver.setPrefix("/WEB-INF/classes/templates/");
        viewResolver.setSuffix(".ftl");
        viewResolver.setViewClass(FreeMarkerView.class);
        return viewResolver;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //addResourceLocations指的是文件放置的目录,addResourceHandler指的是对外暴露的访问路径
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
    }

    // 这个方法用来注册拦截器，我们自己写好的拦截器需要通过这里添加注册才能生效
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // addPathPatterns("/**") 表示拦截所有的请求，
        // excludePathPatterns("/login", "/register") 表示除了登陆与注册之外，因为登陆注册不需要登陆也可以访问
        //registry.addInterceptor(loginInterceptor).addPathPatterns("/**").excludePathPatterns("/login", "/static/**");
    }
    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        //不忽略url中.后面的参数
        configurer.setUseSuffixPatternMatch(false);
    }

    @Bean
    public MultipartResolver multipartResolver(){
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        resolver.setMaxUploadSize(1024 * 1024 * 2);
        return resolver;
    }
}
