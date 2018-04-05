package com.asiainfo.biapp.si.loc.base.filter;

import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;

import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.stereotype.Component;

import com.alibaba.druid.support.http.WebStatFilter;

@Component
@WebFilter(filterName="druidWebStatFilter",urlPatterns="/api/*",initParams={
		@WebInitParam(name="exclusions",value="*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*"),
		@WebInitParam(name="sessionStatEnable",value="false")
	})
public class DruidWebStatFilter extends WebStatFilter {
}