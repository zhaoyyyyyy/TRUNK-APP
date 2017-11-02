/**
 * 
 */
package com.asiainfo.biapp.si.coc.jauth.sysmgr.vo;

import com.asiainfo.biapp.si.coc.jauth.sysmgr.entity.Group;

/**
 * @author liukai
 * @date 2013-6-27
 */
public class GroupVo extends Group {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String createTimeStart;
	private String createTimeEnd;

	public String getCreateTimeStart() {
		return createTimeStart;
	}

	public void setCreateTimeStart(String createTimeStart) {
		this.createTimeStart = createTimeStart;
	}

	public String getCreateTimeEnd() {
		return createTimeEnd;
	}

	public void setCreateTimeEnd(String createTimeEnd) {
		this.createTimeEnd = createTimeEnd;
	}

}
