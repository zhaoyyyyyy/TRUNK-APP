package com.asiainfo.biapp.si.loc.base.page;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.asiainfo.biapp.si.loc.base.BaseConstants;

import io.swagger.annotations.ApiParam;

public class Page<T> implements Serializable {
	
	

	  public static Integer MAX_PAGE_SIZE = 100000;
	  
	  /** 开始查询的页数 */
	  @ApiParam(value="开始查询的页数")
	  private int pageStart;

	  /** 排序的列 */
	  @ApiParam(value="排序的列")
	  private String sortCol;

	  public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/** 排序的方向 */
	  @ApiParam(value="排序的方向")
	  private String sortOrder;

	  
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
	      return (this.pageStart - 1) * this.getPageSize() ;
	    }
	    return 0;
	  }
	  
	  public int getEnd(){
		  return this.getStart()+this.getPageSize();
	  }
	  
	  public int getCurrentPageNo() {
	    return this.pageStart;
	  }

	  public String toJSONStr(){
		  return null;
	  }
	  
	  
	  
	  
	private static final long serialVersionUID = 2442779466291470277L;
	@ApiParam(value="开始查询的条数")
	private int start;
	@ApiParam(value="每页的总条数")
	private int pageSize = BaseConstants.DEFAULT_PAGE_SIZE;
	@ApiParam(value="是否自动查询条数",defaultValue="true")
	private boolean autoCount = true;
	@ApiParam(value="",hidden=true)
	private List<T> data = new ArrayList<T>();
	@ApiParam(value="总行数")
	private int totalCount;

	public Page() {
		this(0, BaseConstants.DEFAULT_PAGE_SIZE);
	}

	public Page(int start, int pageSize) {
		this.pageSize = pageSize;
		this.start = start;
	}

	public Page(List<T> list) {
		if (list != null) {
			this.pageSize = 0;
			this.start = 0;
			this.totalCount = list.size();
			this.data = list;
		}
	}

	public int getTotalCount() {
		return this.totalCount;
	}


	public int getTotalPageCount() {
		if (this.totalCount == 0) {
			return 0;
		}
		if (this.totalCount % this.pageSize == 0) {
			return this.totalCount / this.pageSize;
		}
		return this.totalCount / this.pageSize + 1;
	}

	public int getPageSize() {
		return this.pageSize;
	}

	public List<T> getResult() {
		return this.data;
	}


	public boolean hasNextPage() {
		return getCurrentPageNo() < getTotalPageCount() - 1;
	}

	public boolean hasPreviousPage() {
		return getCurrentPageNo() > 1;
	}

	public List<T> getData() {
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


	public void setStart(int start) {
		this.start = start;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * add by zhougz3 20170730
	 * 
	 * @return
	 */

	public String toString() {
		return new ToStringBuilder(this).append("起始行", this.start)
				.append("每页行数", this.pageSize).append("总条数", this.totalCount)
				.append("当前列表size", this.data.size()).toString();
	}
}