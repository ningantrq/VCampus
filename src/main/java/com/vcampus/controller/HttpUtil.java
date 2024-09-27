package com.vcampus.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

public class HttpUtil {
    public static String toParams(Map<String, Object> params) throws UnsupportedEncodingException {
        StringBuilder paramStr = new StringBuilder();
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            if (paramStr.length() > 0) {
                paramStr.append("&");
            }
            paramStr.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            paramStr.append("=");
            paramStr.append(URLEncoder.encode(entry.getValue().toString(), "UTF-8"));
        }
        return paramStr.toString();
    }
}
