package com.asiainfo.biapp.si.loc;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

//@EnableDiscoveryClient
@EnableEurekaClient
@SpringBootApplication
@EnableCaching
public class LocApplication extends SpringBootServletInitializer{

    @Override  
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {  
    		builder.sources(this.getClass());
        return super.configure(builder);  
    }  
    
	public static void main(String[] args) {
		new SpringApplicationBuilder(LocApplication.class).web(true).run(args);
	}

}