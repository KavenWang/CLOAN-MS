package com.uisftech.cloan.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串处理类
 *
 * @author YiHua
 *
 */
public class StringUtils {
	/**
	 * 对字符串进行匹配替换
	 *
	 * @param desMatcher
	 *            需要匹配的字符串
	 * @param srcPattern
	 *            需要替换的源变量
	 * @param compile
	 *            替换之后的内容
	 * */
	public static String replaceLogRule(String desMatcher, String srcPattern, String compile) {
		Pattern pattern = Pattern.compile(srcPattern);
		Matcher matcher = pattern.matcher(desMatcher);

		return matcher.replaceAll(compile);
	}

	public static boolean isEmpty(Object str) {
		return (str == null || "".equals(str));
	}


	 /**
     * 前置零补位
     * @return
     */
	public static String preZeroStr(String srcStr,int length){
		if(null==srcStr){
			srcStr="0";
		}
    	StringBuffer sb = new StringBuffer();
    	for(int index=0;index<length;index++){
    		sb.append("0");
    	}
    	String descStr = sb.append(srcStr).toString();
    	return descStr.substring(descStr.length()-length);
    }

    /**
     * 后置不空格
     * @return
     */
	public static String subBlankStr(String srcStr,int length){
		if(null==srcStr){
			srcStr="null";
		}
    	StringBuffer sb = new StringBuffer(srcStr);
    	for(int index=srcStr.length();index<length;index++){
    		sb.append(" ");
    	}
    	String descStr = sb.toString();
    	return descStr.substring(0,length);
    }
}
