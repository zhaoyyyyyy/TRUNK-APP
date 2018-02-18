package com.asiainfo.biapp.si.loc.base;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component  
@ConfigurationProperties(prefix="spring")
public class LocDataSource {
	
	@Value("${spring.datasource.driver-class-name}")
	private String  driverClass;
	
	@Value("${spring.datasource.url}")
	private String  url;
	
	@Value("${spring.datasource.username}")
	private String  username;
	
	@Value("${spring.datasource.password}")
	private String  password;

	public String getDriverClass() {
		return driverClass;
	}

	public String getUrl() {
		return url;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}
	
	
	


}
