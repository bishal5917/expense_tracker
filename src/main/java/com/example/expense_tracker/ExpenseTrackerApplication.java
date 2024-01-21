package com.example.expense_tracker;

import com.example.expense_tracker.filters.AuthFilter;
import org.apache.catalina.filters.CorsFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@SpringBootApplication
public class ExpenseTrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExpenseTrackerApplication.class, args);
	}

	@Bean
	public FilterRegistrationBean<AuthFilter> filterRegistrationBean(){
		FilterRegistrationBean<AuthFilter> regBean = new FilterRegistrationBean<>();
		AuthFilter authFilter=new AuthFilter();
		regBean.setFilter(authFilter);
		regBean.addUrlPatterns("/api/categories/*");
		return regBean;
	}

	@Bean
	public FilterRegistrationBean<CorsFilter> corsFilter(){
		FilterRegistrationBean<CorsFilter> registrationBean = new FilterRegistrationBean<>();
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.addAllowedOrigin("*");
		config.addAllowedHeader("*");
		source.registerCorsConfiguration("/**",config);
		registrationBean.setFilter(new CorsFilter());
		registrationBean.setOrder(0);
		return registrationBean;
	}
}
