package com.uisftech.cloan.common.util;

import java.util.List;

public class PageResult<IT> {

	private Integer pageSize;
	private Integer pageIndex;
	private String order;
	private String sort;
	private Long totalCount;
	private Integer totalPage;
	private List<IT> data;

	public PageResult() {

	}

	/**
	 * pageSize.
	 *
	 * @param pageSize
	 *            the pageSize to set
	 */
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * pageIndex.
	 *
	 * @param pageIndex
	 *            the pageIndex to set
	 */
	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}

	/**
	 * data.
	 *
	 * @return the data
	 */
	public List<IT> getData() {
		return data;
	}

	/**
	 * data.
	 *
	 * @param data
	 *            the data to set
	 */
	public void setData(List<IT> data) {
		this.data = data;
	}

	/**
	 * totalPage.
	 *
	 * @param totalPage
	 *            the totalPage to set
	 */
	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}

	public PageResult(Integer pageSize, Integer pageIndex) {
		this.pageSize = pageSize;
		this.pageIndex = pageIndex;
	}

	public int getTotalPage() {

		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public Long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Long totalCount) {
		this.totalCount = totalCount;
		totalPage = (int) (totalCount / pageSize + (totalCount % pageSize == 0 ? 0 : 1));

	}

}
