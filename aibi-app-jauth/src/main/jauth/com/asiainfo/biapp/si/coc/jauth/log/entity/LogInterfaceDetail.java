
package com.asiainfo.biapp.si.coc.jauth.log.entity;

import javax.persistence.Entity;
import javax.persistence.Id;


import com.asiainfo.biapp.si.coc.jauth.frame.entity.BaseEntity;

@Entity
public class LogInterfaceDetail extends BaseEntity{
	
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

}
