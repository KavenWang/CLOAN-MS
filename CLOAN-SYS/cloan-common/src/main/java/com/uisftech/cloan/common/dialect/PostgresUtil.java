package com.uisftech.cloan.common.dialect;

import com.uisftech.cloan.common.util.PageResult;


public class PostgresUtil {

	public static String getPageSql(String sql, PageResult pageResult) {
		// 设置分页参数
		int pageSize = pageResult.getPageSize();
		int pageNo = pageResult.getPageIndex();
		StringBuffer sb = new StringBuffer(sql);

		sb.append(" limit ").append(pageSize).append(" offset ")
				.append(pageSize * (pageNo - 1));
		return sb.toString();
	}

	public String getDataDaseType() {
		return "postgresql";
	}

}
