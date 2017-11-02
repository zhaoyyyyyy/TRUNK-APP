package com.asiainfo.biapp.si.loc.core;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;



@RequestMapping("api")
@RestController
public class TestController{
	
	@RequestMapping(value="/test", method=RequestMethod.GET)
	public void findDicPageByParams(){
		System.out.println(1);
	}
}
