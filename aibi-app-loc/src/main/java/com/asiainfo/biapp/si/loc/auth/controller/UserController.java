package com.asiainfo.biapp.si.loc.auth.controller;

import com.asiainfo.biapp.si.loc.auth.model.User;
import com.asiainfo.biapp.si.loc.base.controller.BaseController;
import com.asiainfo.biapp.si.loc.base.exception.AuthException;

public class UserController extends BaseController<User>{
	
	
	public void a (){
		try {
			User user = this.getLoginUser();
		} catch (AuthException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
