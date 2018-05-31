package com.asiainfo.biapp.si.loc.core.home.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.asiainfo.biapp.si.loc.base.entity.BaseEntity;

import io.swagger.annotations.ApiParam;

/**
 * Title : 
 * <p/>
 * Description :UserAttentionLabel
 * <p/>
 * CopyRight : CopyRight (c) 2018
 * <p/>
 * Company : 北京亚信智慧数据科技有限公司
 * <p/>
 * JDK Version Used : JDK 7.0 +
 * <p/>
 * Modification History	:
 * <p/>
 * <pre>NO.    Date    Modified By    Why & What is modified</pre>
 * <pre>1    2018年5月18日    zhaoyy5        Created</pre>
 * <p/>
 *
 * @author zhaoyy5
 * @version 1.0.0.2018年5月18日
 */
@Entity
@Table(name = "loc_user_attention_label")
public class UserAttentionLabel extends BaseEntity {
	
	
	@Id
    @Column(name = "USER_ID")
    @GenericGenerator(name="idGenerator", strategy="uuid") 
    @GeneratedValue(generator="idGenerator") //使用uuid的生成策略  
    @ApiParam(value = "用户ID ")
    private String user_id;

    @Column(name = "LABEL_ID")
    @ApiParam(value = "客户群ID")
    private String label_id;
    
    @Column(name = "ATTENTION_TIME")
    @ApiParam(value = "收藏时间")
    private String attention_time;

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getLabel_id() {
		return label_id;
	}

	public void setLabel_id(String label_id) {
		this.label_id = label_id;
	}

	public String getAttention_time() {
		return attention_time;
	}

	public void setAttention_time(String attention_time) {
		this.attention_time = attention_time;
	}
    
}
