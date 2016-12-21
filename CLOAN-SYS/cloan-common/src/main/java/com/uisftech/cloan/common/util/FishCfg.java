package com.uisftech.cloan.common.util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/***
 * 配置参数
 *
 * @author YiHua
 *
 */
public class FishCfg {

    private static Map<String, String> entities = new HashMap<String, String>();

    public static String fileSep = System.getProperty("file.separator");

    public static String hostIp = "127.0.0.1";

    public FishCfg() {

    }

    /**
     * 初始化文件夹目录
     */
    public static void initDirs() {
        File rootDoc = new File(FishCfg.getFishHome());
        if (!rootDoc.isDirectory()) {
            rootDoc.mkdirs();
        }

        File tempDir = new File(FishCfg.getTempDir());
        if (!tempDir.isDirectory()) {
            tempDir.mkdir();
        }
    }

    /**
     * 根据参数获取配置信息
     *
     * @param paramName
     * @return
     */
    public static String getParamValue(String paramName) {
        return FishCfg.entities.get(paramName);
    }

    /**
     * 根据参数获取配置信息
     *
     * @param paramName
     *            参数名
     * @param defaults
     *            默认值
     * @return
     */
    public static String getParamValue(String paramName, String defaults) {

        String paramValue = FishCfg.entities.get(paramName);

        if (StringUtils.isEmpty(paramValue)) {
            return defaults;
        }

        return paramValue;

    }

    public static void initFishExpiry(String registerSerial) {

        RegisterInfo reg = SystemRegisterUtil.analys(registerSerial);
        String systemKey = SystemRegisterUtil.getkey(SystemRegisterUtil.getRegSn());
        if (reg != null && reg.getKey().equals(systemKey)) {
            String expiryDate = new SimpleDateFormat(DateUtils.STANDARD_DATE).format(new Date(reg.getEndDate()
                    .getTimeInMillis()));
            FishCfg.setFishCfg("expiry_date", expiryDate);
        }
    }

    public static boolean isFishExpiry() {
        String expiryDate = FishCfg.entities.get("expiry_date");
        String today = DateUtils.getDate(new Date());
        if (expiryDate != null && expiryDate.compareTo(today) > 0) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 获取帮助文件存放路径
     *
     * @return
     */
    public static String getFishHelpDoc() {
        return FishCfg.getFishHome() + FishCfg.fileSep + "help";
    }

    /**
     * 设置参数值
     *
     * @param key
     * @param value
     */
    public static void setFishCfg(String key, String value) {
        if (key.equals("register_serial")) {
            FishCfg.initFishExpiry(value);
        }
        FishCfg.entities.put(key, value);
    }

    /**
     * 获取系统根路径 默认路径c:\\fish_home
     *
     * @return
     */
    public static String getFishHome() {
        String fileRoot = FishCfg.entities.get("fish_home");
        if (fileRoot != null) {
            return fileRoot;
        } else {
            return "c:" + FishCfg.fileSep + "fish_home";
        }
    }

    /**
     * 获得临时文件存放路径
     *
     * @return
     */
    public static String getTempDir() {
        return FishCfg.getFishHome() + FishCfg.fileSep + "temp";
    }

    /**
     * 获得公告存放路径
     *
     * @return
     */
    public static String getAnnouncementDir() {
        return FishCfg.getFishHome() + FishCfg.fileSep + "announcement";
    }

    public static String getVersionDir() {
        return FishCfg.getFishHome() + FishCfg.fileSep + "version";
    }
}
