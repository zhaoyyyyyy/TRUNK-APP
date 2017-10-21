package com.asiainfo.biapp.si.coc.jauth.frame.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 
 * @author 
 *
 * @version 1.0, 2010-8-12
 */
public abstract class BaseEntity implements java.io.Serializable {
    
	private static final long serialVersionUID = 2035013017939483936L;
	
    private String id;
    public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}


	@Override
    public String toString() {
		return super.toString();
    }
}
