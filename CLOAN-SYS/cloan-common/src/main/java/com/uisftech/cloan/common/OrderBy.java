package com.uisftech.cloan.common;

/**
 * 排序条件
 * @author xuxigang
 * @version 0.4
 * @since 0.4
 * @date 2010-12-16
 */
public class OrderBy {

	/**
	 * 对象属性名
	 */
	private String propertyName;
	/**
	 * 默认升序
	 */
	private boolean ascending = true;

	/**
	 * 升序
	 */
	public static final String ASC  = "asc";
	/**
	 * 降序
	 */
	public static final String DESC  = "desc";

	public OrderBy(String propertyName, String order) {
		this.propertyName = propertyName;
		this.ascending = order.equalsIgnoreCase(ASC) ? true : false;
	}

	public OrderBy(String propertyName){
		this.propertyName = propertyName;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public boolean isAscending() {
		return ascending;
	}

}
