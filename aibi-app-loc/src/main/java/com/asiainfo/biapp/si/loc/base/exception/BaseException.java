package com.asiainfo.biapp.si.loc.base.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR,reason="请求出现问题")
public class BaseException {

}
