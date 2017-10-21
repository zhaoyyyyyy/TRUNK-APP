package com.asiainfo.biapp.si.loc.core;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class LocApplication {

	public static void main(String[] args) {
		new SpringApplicationBuilder(LocApplication.class).web(true).run(args);
	}

}