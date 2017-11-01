
package com.asiainfo.biapp.si.coc.jauth.log.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

import com.asiainfo.biapp.si.coc.jauth.frame.entity.BaseEntity;

@Entity
public class LogInterfaceDetail extends BaseEntity{
	
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
