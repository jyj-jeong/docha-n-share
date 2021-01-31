package com.ohdocha.admin.config;

import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.sun.media.jfxmedia.logging.Logger;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	@Value("${com.ohdocha.admin.temp-folder-path}")
	private String LOCATION_FILE;
	
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/favicon.ico", "/login",
                        "/api/v1.0/**",
                        "/webjars/**",
                        "/img/**",
                        "/css/**",
                        "/js/**",
                        "/vendor/**",
                        "/uploadImg/**"
                );
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
    	
    
        registry.addResourceHandler(
                "/webjars/**",
                "/assets/**",
               // "/img/**",
                "/css/**",
                "/js/**",
                "/vendor/**",
                "/uploadImg/**"
     //           ,"/summernotelocal/**"
                            ) 
                .addResourceLocations(
                        "classpath:/META-INF/resources/webjars/",
                        "classpath:/static/assets/",
                        "classpath:/static/img/",
                        "classpath:/static/css/",
                        "classpath:/static/js/",
                        "classpath:/static/vendor/",
                        LOCATION_FILE
                        //"file:///C:/ohdocha/data/temp/"
         //               ,"classpath:/upload/summersummer/summernotelocal/"
                                                    );
    }

}
