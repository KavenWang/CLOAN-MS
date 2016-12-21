package com.uisftech.cloan.common;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface IFilter {

	public Set<OrderBy> getOrders();

	/**
	 * 从1.0.0版本后，建议使用快捷增加查询条件的方法,如 public IFilter gt(String propertyName, Object value);等
	 * @param entry
	 */
	@Deprecated
	public void addFilterEntry(IFilterEntry entry);

	/**
	 * 大于操作
	 * @param propertyName
	 * @param value
	 * @since 1.0.0
	 * @return
	 */
	public IFilter gt(String propertyName, Object value);

	/**
	 * 大于等于操作
	 * @param propertyName
	 * @param value
	 * @since 1.0.0
	 * @return
	 */
	public IFilter ge(String propertyName, Object value);

	/**
	 * 小于操作
	 * @param propertyName
	 * @param value
	 * @since 1.0.0
	 * @return
	 */
	public IFilter lt(String propertyName, Object value);

	/**
	 * 小于等于操作
	 * @param propertyName
	 * @param value
	 * @since 1.0.0
	 * @return
	 */
	public IFilter le(String propertyName, Object value);

	/**
	 * 不等于操作
	 * @param propertyName
	 * @param value
	 * @since 1.0.0
	 * @return
	 */
	public IFilter ne(String propertyName, Object value);

	/**
	 * 等于操作
	 * @param propertyName
	 * @param value
	 * @since 1.0.0
	 * @return
	 */
	public IFilter eq(String propertyName, Object value);

	/**
	 * 全模糊匹配
	 * @param propertyName
	 * @param value
	 * @since 1.0.0
	 * @return
	 */
	public IFilter like(String propertyName, String value);

	/**
	 * 右模糊匹配
	 * @param propertyName
	 * @param value
	 * @since 1.0.0
	 * @return
	 */
	public IFilter rlike(String propertyName, String value);

	/**
	 * 左模糊匹配
	 * @param propertyName
	 * @param value
	 * @since 1.0.0
	 * @return
	 */
	public IFilter llike(String propertyName, String value);

	/**
	 * IN操作
	 * @param propertyName
	 * @param value
	 * @since 1.0.0
	 * @return
	 */
	public IFilter in(String propertyName, Collection<?> value);

	/**
	 * 或者操作
	 * @param propertyName
	 * @param value
	 * @since 1.0.0
	 * @return
	 */
	public IFilter or(String propertyName, Collection<?> value);


	@Deprecated
	public void addOrder(OrderBy order);

	/**
	 * 按照某个字段降序排列
	 * @param propertyName
	 * @since 1.0.0
	 * @return
	 */
	public IFilter descOrder(String propertyName);

	/**
	 * 按照某个字段升序排列
	 * @param propertyName
	 * @since 1.0.0
	 * @return
	 */
	public IFilter order(String propertyName);

	public boolean isEmpty();

	public Set<String> keys();

	public Set<IFilterEntry> entrySet();

	public Object getValue(String key);

	public boolean isMatch(Object object);

	public <T> List<T> filter(List<T> data);

	public int getEntrySize();

	public IFilterEntry getFilterEntry(String key);

}
