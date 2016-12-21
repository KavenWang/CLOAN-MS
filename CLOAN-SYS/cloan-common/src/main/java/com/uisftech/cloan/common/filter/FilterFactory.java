package com.uisftech.cloan.common.filter;

import java.util.Collection;

import com.uisftech.cloan.common.Operator;


/**
 * 查询条目
 * @author liuwei
 * @version
 * @since
 * @date 2010-8-8
 */
public class FilterFactory {

	public static FilterEntry eq(String propertyName, Object value) {
		return new FilterEntry(propertyName,value,Operator.EQ);
	}

	public static FilterEntry ne(String propertyName, Object value) {
		return new FilterEntry(propertyName,value,Operator.NE);
	}

	public static FilterEntry lt(String propertyName, Object value) {
		return new FilterEntry(propertyName,value,Operator.LT);
	}

	public static FilterEntry le(String propertyName, Object value) {
		return new FilterEntry(propertyName,value,Operator.LE);
	}

	public static FilterEntry gt(String propertyName, Object value) {
		return new FilterEntry(propertyName,value,Operator.GT);
	}

	public static FilterEntry ge(String propertyName, Object value) {
		return new FilterEntry(propertyName,value,Operator.GE);
	}

	public static FilterEntry like(String propertyName, String value) {
		return new FilterEntry(propertyName,value,Operator.LIKE);
	}

	public static FilterEntry rlike(String propertyName, String value) {
		return new FilterEntry(propertyName,value,Operator.RLIKE);
	}

	public static FilterEntry llike(String propertyName, String value) {
		return new FilterEntry(propertyName,value,Operator.LLIKE);
	}

	public static FilterEntry in(String propertyName, Collection<?> value) {
		return new FilterEntry(propertyName,value,Operator.IN);
	}

	public static FilterEntry or(String propertyName, Collection<?> value) {
		return new FilterEntry(propertyName,value,Operator.OR);
	}

}
