package com.asiainfo.biapp.si.loc;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
@EnableCaching
public class LocApplication {

	public static void main(String[] args) {
		new SpringApplicationBuilder(LocApplication.class).web(true).run(args);
	}

}