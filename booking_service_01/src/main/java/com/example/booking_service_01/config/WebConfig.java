package com.example.booking_service_01.config;

import javax.servlet.Filter;

import com.example.booking_service_01.fillter.AdminCheckFilter;
import com.example.booking_service_01.fillter.LoginCheckFilter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000", "https://prismatic-monstera-da90b2.netlify.app")
                .allowedMethods("*")
                .allowCredentials(true);
    }

    @Bean
    public FilterRegistrationBean adminFilter() {
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new AdminCheckFilter());
        filterRegistrationBean.setOrder(1);
        filterRegistrationBean.addUrlPatterns("/admin/*");
        filterRegistrationBean.addUrlPatterns("/manage/*");
        return filterRegistrationBean;
    }

	// @Bean
 	// public FilterRegistrationBean logFilter() {
    //     FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
	//  	filterRegistrationBean.setFilter(new LoginCheckFilter()); //내가 구현한 필터 넣기
 	//  	filterRegistrationBean.setOrder(2); //필터 체인할 때 가장 먼저 실행
    //     filterRegistrationBean.addUrlPatterns("/students/*"); //모든 요청 url에 대해 실행
    //     filterRegistrationBean.addUrlPatterns("/booking/*");
 	//  	return filterRegistrationBean;
 	// }
}
