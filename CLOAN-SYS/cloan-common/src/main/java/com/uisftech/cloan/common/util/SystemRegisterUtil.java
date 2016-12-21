package com.uisftech.cloan.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class SystemRegisterUtil {

	//对key进行编码
	public static String convertKey(String mac){
		//去掉特殊字符
		mac = mac.replaceAll("\\W", "").trim();
		String k = SystemRegisterUtil.getkey(mac);
		k = SystemRegisterUtil.change(k);
		return k;
	}

	//获得注册码
	private static String getSerial(String key , Date endDate){

		key = SystemRegisterUtil.change(key); //先将key进行一次编码
		key = SystemRegisterUtil.getkey(key); //保证key的长度在8位

		/*因为一开始key的长度为8位，后来发现必须要有12位才有唯一性，所以后来
		 * 又增加到了12位，为了不影响原先的操作，这里先将后四位截取，等所有
		 * 操作完成以后在加入序列号
		 */
		String otherKey = key.substring(8, 12);
		key = key.substring(0,8);

		//获得当前时间
		Calendar startCalendar = Calendar.getInstance();
		startCalendar.set(Calendar.HOUR_OF_DAY, 0);
		startCalendar.set(Calendar.MINUTE, 0);
		startCalendar.set(Calendar.SECOND, 0);
		startCalendar.set(Calendar.MILLISECOND , 0);
		int startYear = startCalendar.get(Calendar.YEAR);
		int startMonth = startCalendar.get(Calendar.MONTH)+1;
		int startDay = startCalendar.get(Calendar.DAY_OF_MONTH);


		Calendar endCalendar = Calendar.getInstance();
		endCalendar.setTime(endDate);
		endCalendar.set(Calendar.HOUR_OF_DAY, 0);
		endCalendar.set(Calendar.MINUTE, 0);
		endCalendar.set(Calendar.SECOND, 0);
		endCalendar.set(Calendar.MILLISECOND , 0);

		int disDay = 0 ;
		long millOffset = endCalendar.getTime().getTime() -startCalendar.getTime().getTime();
		disDay = Integer.parseInt(Long.toString(millOffset/(1000*60*60*24)));

		int endYear = endCalendar.get(Calendar.YEAR);
		int endMonth = endCalendar.get(Calendar.MONTH)+1;
		int endDay = endCalendar.get(Calendar.DAY_OF_MONTH);
		int index = Double.valueOf(Math.random()*10).intValue();
		String need = new StringBuffer()
						.append(key)
						.append(startYear)
						.append(startMonth <10? ("0"+startMonth) : startMonth)
						.append(startDay <10? ("0"+startDay) : startDay)
						.append(endYear)
						.append(endMonth <10? ("0"+endMonth) : endMonth)
						.append(endDay <10? ("0"+endDay) : endDay)
						.append(index)
						.toString();

		String result = "";
		for(int i = 1 ; i<=need.length();i++){
			if( i>=5 && i%5 ==0){
				result = result + need.substring(i-5, i)+"-";
			}
		}
		result = result.substring(0, result.length()-1);

		result = SystemRegisterUtil.singleStringConvert(result);

		//将相差的天数编入序列码
		result = SystemRegisterUtil.addDisDay(disDay, result);

		result = SystemRegisterUtil.changePosition(index, result);

		//最后将额外增加的4位key加到注册码中
		result = result + otherKey;

		System.out.println("\n注册码为:"+result);

		return result;
	}

	/**
	 * 解析注册码
	 * RegisterBean.analySuccess 注册码符合格式
	 * @param serial
	 * @return
	 */
	public static RegisterInfo analys(String serial){
		/**
		 * 因为后来又加了4位key，加在了最后，所以为了
		 * 不影响原先的操作，先将注册码中的后四位截取
		 */
		String otherKey = serial.substring(serial.length()-4, serial.length());
		serial = serial.substring(0, serial.length()-4);
		RegisterInfo regInfo = new RegisterInfo();
		try {
			//先查看是按哪种组合排列的
			int index = Integer.parseInt(SystemRegisterUtil.chars2Strings(serial.substring(serial.length()-1)));
			String zuHe = SystemRegisterUtil.getZuHe(index);
			String[] serials = serial.split("-");
			String result = new StringBuffer()
							.append(serials[zuHe.indexOf("1")].trim())
							.append("-")
							.append(serials[zuHe.indexOf("2")].trim())
							.append("-")
							.append(serials[zuHe.indexOf("3")].trim())
							.append("-")
							.append(serials[zuHe.indexOf("4")].trim())
							.append("-")
							.append(serials[zuHe.indexOf("5")].trim())
							.toString();
			String[] normalSerials = result.split("-");

			//后来在每一段注册码的开头又加了一个数字
			String innerKey = normalSerials[0].substring(1) + normalSerials[1].substring(1).substring(0, 3);
			String startDate = normalSerials[2].substring(1)
				                +normalSerials[3].substring(1).substring(0,1);
			startDate =normalSerials[1].substring(1).substring(3, 5) + SystemRegisterUtil.chars2Strings(startDate);
			String endDate = normalSerials[3].substring(1).substring(1, 5)
								+normalSerials[4].substring(1).substring(0, 4);
			endDate = SystemRegisterUtil.chars2Strings(endDate);

			//将后四位key加入
			regInfo.setKey(innerKey+otherKey);



			int innerDistanceDay =
				Integer.parseInt(
					SystemRegisterUtil.convertFirstIndex2String(0,normalSerials[0].substring(0, 1))+
					SystemRegisterUtil.convertFirstIndex2String(1,normalSerials[1].substring(0, 1))+
					SystemRegisterUtil.convertFirstIndex2String(2,normalSerials[2].substring(0, 1))+
					SystemRegisterUtil.convertFirstIndex2String(3,normalSerials[3].substring(0, 1))+
					SystemRegisterUtil.convertFirstIndex2String(4,normalSerials[4].substring(0, 1))
				);



			SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");

			Calendar calendar = Calendar.getInstance();
			calendar.setTime(format.parse(startDate));
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			regInfo.setStartDate(calendar);

			Calendar calendar2 = Calendar.getInstance();
			calendar2.setTime(format.parse(endDate));
			calendar2.set(Calendar.HOUR_OF_DAY, 0);
			calendar2.set(Calendar.MINUTE, 0);
			calendar2.set(Calendar.SECOND, 0);
			regInfo.setEndDate(calendar2);

			regInfo.setDisDay(innerDistanceDay);
			regInfo.setAnalySuccess(true);
		} catch (Exception e) {
			regInfo.setAnalySuccess(false);
		}
		return regInfo;
	}


	public static RegisterResult isOk(String mac,String serial){
		return SystemRegisterUtil.serialIsOk(mac.trim(),SystemRegisterUtil.analys(serial.trim()));
	}
	public static RegisterResult isOk(String serial){
		return SystemRegisterUtil.serialIsOk("",SystemRegisterUtil.analys(serial));
	}

	private static RegisterResult serialIsOk(String key ,RegisterInfo regInfo){

		RegisterResult registerResult = new RegisterResult();
		if(!regInfo.isAnalySuccess()){
			registerResult.setSuccess(false);
			registerResult.setMessage("注册码格式错误");
			return registerResult;
		}
		else {
			String convertKey = SystemRegisterUtil.convertKey(regInfo.getKey());
			if(!key.equals(convertKey)){
				registerResult.setSuccess(false);
				registerResult.setMessage("系统序列号不正确");
				return registerResult;
			}
			//两个时间点相差的天数

			long millOffset = regInfo.getEndDate().getTime().getTime() - regInfo.getStartDate().getTime().getTime();
			int disDay = Integer.parseInt(Long.toString(millOffset/(1000*60*60*24)));
			if(disDay != regInfo.getDisDay()){
				registerResult.setSuccess(false);
				registerResult.setMessage("注册码不正确");
				return registerResult;
			}
			else if(disDay == 9999){//这个注册码是无期限的
				registerResult.setSuccess(true);
				registerResult.setMessage("注册码正确,注册码类型：没有限制");
				return registerResult;
			}
			else if(disDay != 9999){
				if(new Date().compareTo(regInfo.getEndDate().getTime()) > 0){
					registerResult.setSuccess(false);
					registerResult.setMessage("注册码已经过期");
					return registerResult;
				}
				registerResult.setSuccess(true);
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				registerResult.setMessage("注册码正确,注册码类型：到期时间："+format.format(regInfo.getEndDate().getTime()));
				return registerResult;
			}
		}
		registerResult.setSuccess(false);
		registerResult.setMessage("未知错误");
		return registerResult;
	}



	//将key的位数保证在12位
	public static String getkey(String key){
		//确保key只有12位数
		int keyLength = 12;

		String[] keys = key.split("\\W");
		String result = "";
		for(String v : keys){
			result += v;
		}
		key = result;
		if(key.length()>keyLength){
			key = key.substring(0,keyLength-1);
		}
		if(key.length()<keyLength){
			for(int i = key.length();i<keyLength;i++){
				key = key + 0;
			}
		}
		return key;
	}


	private static String getZuHe(int index){
		List<String> zhHe = new ArrayList<String>();
		zhHe.add("13425");//0
		zhHe.add("24315");//1
		zhHe.add("23145");//2
		zhHe.add("14325");//3
		zhHe.add("34215");//4
		zhHe.add("32415");//5
		zhHe.add("43215");//6
		zhHe.add("42315");//7
		zhHe.add("31245");//8
		zhHe.add("32145");//9
		String zuHe = zhHe.get(index);
		return zuHe;
	}




	private static String convertFirstNum2Index(int index,int num){
		String num0 = "ABCDEFGHIJKLY";
		String num1 = "MNOPQRSTUVWXZ";
		String num2 = "OWEVCURSDFJNX";
		String num3 = "QWERTYUIOPLKD";
		String num4 = "ZXSADFCVGHBNM";
		if(index==0){
			return num0.substring(num, num+1);
		}
		if(index==1){
			return num1.substring(num, num+1);
		}
		if(index==2){
			return num2.substring(num, num+1);
		}
		if(index==3){
			return num3.substring(num, num+1);
		}
		if(index==4){
			return num4.substring(num, num+1);
		}
		return "";
	}

	private static String convertFirstIndex2String(int index,String s){
		String num0 = "ABCDEFGHIJKLY";
		String num1 = "MNOPQRSTUVWXZ";
		String num2 = "OWEVCURSDFJNX";
		String num3 = "QWERTYUIOPLKD";
		String num4 = "ZXSADFCVGHBNM";
		if(index==0){
			return String.valueOf(num0.indexOf(s));
		}
		if(index==1){
			return String.valueOf(num1.indexOf(s));
		}
		if(index==2){
			return String.valueOf(num2.indexOf(s));
		}
		if(index==3){
			return String.valueOf(num3.indexOf(s));
		}
		if(index==4){
			return String.valueOf(num4.indexOf(s));
		}
		return "";
	}

	private static String addDisDay(int disDay,String serial){
		String a = "";
		String result = "";
		if(disDay<=9){
			a="0000"+disDay;
		}else if(disDay<=99){
			a="000"+disDay;
		}else if(disDay<=999){
			a="00"+disDay;
		}else if(disDay<=9999){
			a="0"+disDay;
		}else{
			return null;
		}

		String[] se = serial.split("-");
		for(int i = 0 ; i < se.length;i++){
			se[i] = SystemRegisterUtil.convertFirstNum2Index(i,Integer.parseInt(a.substring(i, i+1)))+se[i];
			result += se[i]+"-";
		}
		result = result.substring(0, result.length()-1);
		return result;
	}

	private static String singleStringConvert(String source){
		String[] values = source.split("-");
		String result = new StringBuffer()
							.append(values[0])
							.append("-")
							.append(values[1])
							.append("-")
							.append(SystemRegisterUtil.strings2Chars(values[2]))
							.append("-")
							.append(SystemRegisterUtil.strings2Chars(values[3]))
							.append("-")
							.append(SystemRegisterUtil.strings2Chars(values[4]))
							.toString();
		return result;
	}

	private static String strings2Chars(String numString){
		byte[] abyte = numString.getBytes();
		String chars = "" ;
		for(int i = 0 ; i < abyte.length;i++){
			char c = (char)(abyte[i]+20);
			chars = chars + String.valueOf(c);
		}
		return chars;
	}
	private static String chars2Strings(String ch){
		byte[] abyte = ch.getBytes();
		String string = "" ;
		for(int i = 0 ; i < abyte.length;i++){
			char c = (char)(abyte[i]-20);
			string =string + String.valueOf(c);
		}
		return string;
	}

	private static String changePosition(int index,String source){
		String sort = SystemRegisterUtil.getZuHe(index);
		String result = "";
		String[] a = source.split("-");
		for(int i = 0 ; i < sort.length();i++ ){
			result = result+"-"+a[Integer.parseInt(sort.substring(i, i+1))-1];
		}
		result = result.substring(1);
		return result;
	}


	/**
	 * 变化原则是
	 * 吧数字（10个）或者字母表（大小写26个）折半对调（两个相互改变），也即
	 * 0与5对调（0变5,5变0）
	 * A与N，a与n对调
	 * @param s
	 * @return
	 */
	private static String change(String s ){
		byte[] abyte0;
		char[] ac = null;
		int i,k,j;
		abyte0 = s.getBytes();
		ac = new char[abyte0.length];
		i=0;
		k=abyte0.length;
		while(i<k){
			j = abyte0[i];
			//0-9,实现01234和56789相应的相互对调
			if((j>=48)&&(j<=57)){
				j=(((j-48)+5)%10)+48;
			}
			else if((j>=65)&&(j<=90)){//A-Z
				j = (((j-65)+13)%26)+65;
			}
			else if((j>=97)&&(j<=122)){//a-z
				j = (((j-97)+13)%26)+97;
			}
			ac[i] = (char)j;
			i++;
		}
		return String.valueOf(ac);
	}

	/**
	 * Get System Reg SN.
	 * @return
	 */
	public static String getRegSn(){

		String sn=System.getProperty("os.version")+System.getProperty("user.name")+System.getProperty("java.version");

		StringBuffer key = new StringBuffer();
		byte[] src =sn.getBytes();
		for(int idx=0;idx<src.length/2;idx++){
			key.append(src[idx]);
			key.append(src[src.length-1-idx]);
		}
		return key.toString();
	}

	public static void main(String[] args) throws IOException {

	    System.out.println("----------------------------------------");

		System.out.println("输入服务器序列号[090591449465]");
		BufferedReader keys = new BufferedReader(new InputStreamReader(System.in));
		String key = keys.readLine();

		System.out.println("选择有效期[1-永久,2-试用]");
		BufferedReader types = new BufferedReader(new InputStreamReader(System.in));
		String type = types.readLine();

		Date date = new Date();

		if(type.equals("1")){
				Calendar calendar  = Calendar.getInstance();
				calendar.add(Calendar.DAY_OF_MONTH, 9999);
				date = calendar.getTime();
		}
		else {
			System.out.println("\n请输入有效期[Example:2012-6-12]:");

			BufferedReader dates = new BufferedReader(new InputStreamReader(System.in));
			String dateString = dates.readLine();


			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			try {
				date = format.parse(dateString);
			} catch (ParseException e) {
				System.out.println("失败");
			}
		}
		SystemRegisterUtil.getSerial(key,date);


	    System.out.println("----------------------------------------");

		/*RegisterResult result = register.isOk("OEFDIG-M5EF20-QDFDEF-A14FEB-XDIGEIB45B");
		System.out.println(result.isSuccess());*/


	}
}
