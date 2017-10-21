
package com.asiainfo.biapp.si.coc.jauth.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.asiainfo.biapp.si.coc.jauth.filter.ApiAuthFilter;

@Configuration
public class ApiAuthConfig {
	
	@Bean
	public FilterRegistrationBean filterRegistrationBean(ApiAuthFilter myFilter) {
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
		filterRegistrationBean.setFilter(myFilter);
		filterRegistrationBean.setEnabled(true);
		filterRegistrationBean.addUrlPatterns("/api/scene/*","/api/label/*","/api/custom/*");
		return filterRegistrationBean;
	}
}
