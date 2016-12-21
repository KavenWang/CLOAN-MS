package com.uisftech.cloan.common.util;

import org.springframework.http.HttpHeaders;

/**
 * 
 * @File com.hoperun.scfs.common.util.HeaderUtil.java
 * @author yin_changbao
 * @Date Oct 18, 2016 
 *
 */
public class HeaderUtil {

    public static HttpHeaders createAlert(String message, String param) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-p3App-alert", message);
        headers.add("X-p3App-params", param);
        return headers;
    }

    public static HttpHeaders createEntityCreationAlert(String entityName, String param) {
        return createAlert("A new " + entityName + " is created with identifier " + param, param);
    }

    public static HttpHeaders createEntityUpdateAlert(String entityName, String param) {
        return createAlert("A " + entityName + " is updated with identifier " + param, param);
    }

    public static HttpHeaders createEntityDeletionAlert(String entityName, String param) {
        return createAlert("A " + entityName + " is deleted with identifier " + param, param);
    }

    public static HttpHeaders createFailureAlert(String entityName, String errorKey, String defaultMessage) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-p3App-error", defaultMessage);
        headers.add("X-p3App-params", entityName);
        return headers;
    }
}
