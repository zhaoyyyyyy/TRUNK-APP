package com.asiainfo.biapp.si.loc.base.page;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.asiainfo.biapp.si.loc.base.exception.BaseException;
import com.asiainfo.biapp.si.loc.base.utils.WebResult;
import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiParam;

/**
 * 
 * Title : 分页器Page
 * <p/>
 * Description :
 * <p/>
 * CopyRight : CopyRight (c) 2017
 * <p/>
 * Company : 北京亚信智慧数据科技有限公司
 * <p/>
 * JDK Version Used : JDK 1.8 +
 * <p/>
 * Modification History :
 * <p/>
 * 
 * <pre>
 * NO.    Date    Modified By    Why & What is modified
 * </pre>
 * 
 * <pre>
 * 1    2017年11月5日    Administrator        Created
 * </pre>
 * <p/>
 *
 * @author zhougz3
 * @version 1.0.0.2017年11月5日
 */
public class Page<T> implements Serializable {
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	/** 总条数 */
	public static Integer MAX_PAGE_SIZE = 100000;
	
	/** 每页条数 */
	public static final int DEFAULT_PAGE_SIZE = 10;
	
	/** 每页条数 */
	@ApiParam(value = "每页多少条")
	private int pageSize = DEFAULT_PAGE_SIZE;
	

	/** 开始查询的页数 */
	@ApiParam(value = "开始查询的页数")
	private int pageStart;

	//@ApiParam(value = "开始查询的条数")
	//private int start;
	
	/** 排序的列 */
	@ApiParam(value = "排序的列")
	private String sortCol;

	/** 排序的方向 */
	@ApiParam(value = "排序的方向")
	private String sortOrder;
	
	@ApiParam(value = "是否自动查询条数", defaultValue = "true")
	private boolean autoCount = true;
	
	@ApiParam(value="不必填写")
	private List<T> data = new ArrayList<T>();
	
	@ApiParam(value = "总行数")
	private int totalCount;

	private int totalPageCount;
	
	
	
	public int getPageStart() {
		return pageStart;
	}

	public void setPageStart(int pageStart) {
		this.pageStart = pageStart;
	}

	public String getSortCol() {
		return sortCol;
	}

	public void setSortCol(String sortCol) {
		this.sortCol = sortCol;
	}

	public String getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}

	public void setTotalCount(int totalCount) {
		if (this.getStart() >= totalCount && this.getPageSize() > 0) {
			this.pageStart = totalCount / this.getPageSize();
		}
		this.totalCount = totalCount;
	}

	public int getStart() {
		if (this.pageStart > 0) {
			return (this.pageStart - 1) * this.getPageSize();
		}
		return 0;
	}

	public int getEnd() {
		return this.getStart() + this.getPageSize();
	}

	public int getCurrentPageNo() {
		this.pageStart = this.getEnd() / this.getPageSize();
		return this.pageStart;
	}

	public String toJSONStr() {
		return null;
	}

	private static final long serialVersionUID = 2442779466291470277L;


	public Page() {
		this(0, DEFAULT_PAGE_SIZE);
	}

	public Page(int pageStart, int pageSize) {
		this.pageSize = pageSize;
		this.pageStart = pageStart;
	}

	public Page(List<T> list) {
		if (list != null) {
			this.pageSize = 0;
			this.pageStart = 0;
			this.totalCount = list.size();
			this.data = list;
		}
	}

	public int getTotalCount() {
		return this.totalCount;
	}

	public int getTotalPageCount() {
		if (this.totalCount == 0) {
			this.totalPageCount = 0;
		}
		if (this.totalCount % this.pageSize == 0) {
			this.totalPageCount =  this.totalCount / this.pageSize;
		}
		this.totalPageCount = this.totalCount / this.pageSize + 1;
		return totalPageCount;
	}

	public int getPageSize() {
		return this.pageSize;
	}

	public boolean hasNextPage() {
		return getCurrentPageNo() < getTotalPageCount() - 1;
	}

	public boolean hasPreviousPage() {
		return getCurrentPageNo() > 1;
	}
	@JsonIgnore  
	public List<T> getData() {
		return this.data;
	}
	public List<T> getRows() {
		return this.data;
	}
	public boolean isAutoCount() {
		return this.autoCount;
	}

	public void setAutoCount(boolean autoCount) {
		this.autoCount = autoCount;
	}

	public void setData(List<T> data) {
		this.data = data;
	}


	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	
	
	/**
	 *  框架返回参数
	 */
	// 状态码
	@ApiParam(value="不必填写")
	private String status = WebResult.Code.OK + "";
	// 信息
	@ApiParam(value="不必填写")
	private String msg = "分页查询成功";

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	/**
	 * 
	 * Description: 出现异常的时候把信息及异常放进去
	 *
	 * @param baseException
	 */
	public void fail(BaseException baseException) {
		setMsg(baseException.getMessage());
		setStatus(baseException.getErrorCode());
	}

	/**
	 * add by zhougz3 20170730
	 * 
	 * @return
	 */
	public String toString() {
		return new ToStringBuilder(this).append("起始行", this.getStart()).append("每页行数", this.pageSize)
				.append("总条数", this.totalCount).append("当前列表size", this.data.size()).toString();
	}
}