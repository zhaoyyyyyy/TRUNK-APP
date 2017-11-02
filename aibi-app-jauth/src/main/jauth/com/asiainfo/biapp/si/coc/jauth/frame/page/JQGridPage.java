package com.asiainfo.biapp.si.coc.jauth.frame.page;

import io.swagger.annotations.ApiParam;

import com.asiainfo.biapp.si.coc.jauth.frame.Constants;

/**
 * 针对JQgrid的分页器
 */
public class JQGridPage<T> extends Page<T> {

	private static final long serialVersionUID = -8488719493119941270L;

	public static final Integer MAX_PAGE_SIZE = 100000;

	/** 开始查询的页数 */
	@ApiParam(value = "开始查询的页数")
	private int pageStart;

	/** 排序的列 */
	@ApiParam(value = "排序的列")
	private String sortCol;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/** 排序的方向 */
	@ApiParam(value = "排序的方向")
	private String sortOrder;

	public JQGridPage() {
		this(0, Constants.DEFAULT_PAGE_SIZE);
	}

	public JQGridPage(int start, int pageSize) {
		this.pageStart = start;
		this.setPageSize(pageSize);
	}

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

	@Override
	public void setTotalCount(int totalCount) {
		if (this.getStart() >= totalCount && this.getPageSize() > 0) {
			this.pageStart = totalCount / this.getPageSize();
		}
		super.setTotalCount(totalCount);
	}

	@Override
	public int getStart() {
		if (this.pageStart > 0) {
			return (this.pageStart - 1) * this.getPageSize();
		}
		return 0;
	}

	public int getEnd() {
		return this.getStart() + this.getPageSize();
	}

	@Override
	public int getCurrentPageNo() {

		return this.pageStart;
	}

	public String toJSONStr() {
		return null;
	}

}
