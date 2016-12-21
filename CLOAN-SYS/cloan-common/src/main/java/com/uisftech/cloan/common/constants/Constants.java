package com.uisftech.cloan.common.constants;

/**
 *
 * @File com.hoperun.scfs.common.constants.Constants.java
 * @author yin_changbao
 * @Date Oct 18, 2016
 *
 */
public final class Constants {

    public static final String LOGIN_REGEX = "^[_'.@A-Za-z0-9-]*$";
    public static final String SPRING_PROFILE_DEVELOPMENT = "dev";
    public static final String SPRING_PROFILE_PRODUCTION = "prod";
    public static final String SPRING_PROFILE_CLOUD = "cloud";
    public static final String SPRING_PROFILE_HEROKU = "heroku";
    public static final String SPRING_PROFILE_SWAGGER = "swagger";
    public static final String SPRING_PROFILE_NO_LIQUIBASE = "no-liquibase";

    public static final String SYSTEM_ACCOUNT = "system";

    public static final String RESPONSE_SUCCESS_CODE="0";
    public static final String RESPONSE_SUCCESS_MESSAGE="成功";

    private Constants() {
    }
}
