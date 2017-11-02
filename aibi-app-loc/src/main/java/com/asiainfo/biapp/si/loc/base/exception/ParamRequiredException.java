package com.asiainfo.biapp.si.loc.base.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST,reason="请求参数有误")
public class ParamRequiredException {

}
