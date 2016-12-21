/*****************************************************************************
 *
 *                      HOPERUN PROPRIETARY INFORMATION
 *
 *          The information contained herein is proprietary to HopeRun
 *           and shall not be reproduced or disclosed in whole or in part
 *                    or used for any design or manufacture
 *              without direct written authorization from HopeRun.
 *
 *            Copyright (c) 2016 by HopeRun.  All rights reserved.
 *
 *****************************************************************************/
package com.uisftech.cloan.common.util;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.util.Formatter;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

/**
 * ClassName: ScfsgwUtil Function: TODO ADD FUNCTION. date: Nov 21, 2016 2:26:21
 * PM .
 * 
 * @author yin_changbao
 * @version
 */
public class ScfsgwUtil {

	private static final Logger logger = LoggerFactory.getLogger(ScfsgwUtil.class);

	public static boolean isPortOccupied(String ip, int port) {
		Socket cSocket = null;
		try {
			cSocket = new Socket(ip, port);
		} catch (Exception e) {
			return false;
		} finally {
			try {
				if (cSocket != null && !cSocket.isClosed())
					cSocket.close();
			} catch (IOException e) {
			}
		}
		return true;
	}

	/**
	 * 
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param port
	 * @return true when port is Occupied
	 */
	public static boolean isPortOccupied(int port) {
		return isPortOccupied("localhost", port);
	}

	public static String getMacAddr() {
		byte[] mac;
		try {
			EPlatform platform = osCoreVersion();
			if (platform.equals(EPlatform.Linux))
				mac = NetworkInterface.getByName("eth0").getHardwareAddress();
			else
				mac = NetworkInterface.getByInetAddress(InetAddress.getLocalHost()).getHardwareAddress();
			StringBuffer macAddr = new StringBuffer();
			if (mac == null || mac.length < 1)
				return null;
			for (int i = 0; i < mac.length; i++)
				macAddr.append(new Formatter()
						.format(Locale.getDefault(), "%02X%s", mac[i], (i < mac.length - 1) ? "-" : "").toString());
			return macAddr.toString();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	public static EPlatform osCoreVersion() {
		String os = System.getProperty("os.name").toLowerCase();
		if (os.indexOf("linux") >= 0)
			return EPlatform.Linux;
		else if (os.indexOf("windows") >= 0)
			return EPlatform.Windows;
		else if (os.indexOf("mac") >= 0 && os.indexOf("os") > 0 && os.indexOf("x") < 0)
			return EPlatform.Mac_OS;
		else if (os.indexOf("mac") >= 0 && os.indexOf("os") > 0 && os.indexOf("x") > 0)
			return EPlatform.Mac_OS_X;
		else if (os.indexOf("solaris") >= 0)
			return EPlatform.Solaris;
		else if (os.indexOf("digital") >= 0 && os.indexOf("unix") > 0)
			return EPlatform.Digital_Unix;
		else if (os.indexOf("aix") >= 0)
			return EPlatform.AIX;
		else
			return EPlatform.Any;
	}

	public enum EPlatform {
		Any("any"), Linux("Linux"), Mac_OS("Mac OS"), Mac_OS_X("Mac OS X"), Windows("Windows"), OS2("OS/2"), Solaris(
				"Solaris"), SunOS("SunOS"), MPEiX("MPE/iX"), HP_UX("HP-UX"), AIX("AIX"), OS390("OS/390"), FreeBSD(
						"FreeBSD"), Irix("Irix"), Digital_Unix("Digital Unix"), NetWare_411(
								"NetWare"), OSF1("OSF1"), OpenVMS("OpenVMS"), Others("Others");

		private EPlatform(String desc) {
			this.description = desc;
		}

		public String toString() {
			return description;
		}

		private String description;
	}
	
	
	public static String gengerateServiceId(String portStr){
		String mac = getMacAddr();
		logger.debug("Mac Of This Server Is: {} And MicoService Will Start At Port {}",mac,portStr);
		if(StringUtils.isEmpty(mac))
			mac = UUIDUtils.random().toString();
		return new MD5().getMD5ofStr(getMacAddr()+portStr);
	}

}
