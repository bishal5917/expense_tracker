package com.example.expense_tracker;

import com.example.expense_tracker.filters.AuthFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

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
}
