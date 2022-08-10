package com.maple.spider.util;

import com.upyun.RestManager;
import com.upyun.UpException;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


/**
 * 又拍云 对象存储工具类
 * @author lucifer
 */
@Slf4j
public class UpyOssUtil {

    private static final String BUCKET_NAME = "";
    private static final String OPERATOR_NAME = "";
    private static final String OPERATOR_PWD = "";
    private static final String URL = "";


    public static String uploadUpy(String url) {
        Calendar calendar = Calendar.getInstance();
        String flag = "/";
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DATE);
        String filePath = "/test/" + year + flag + month + flag + day + flag;
        String fileName = filePath + "xxf-" + System.currentTimeMillis() + url.substring(url.lastIndexOf("."));
        RestManager restManager = new RestManager(BUCKET_NAME, OPERATOR_NAME, OPERATOR_PWD);

        URI u = URI.create(url);
        try (InputStream inputStream = u.toURL().openStream()) {
            Response response = restManager.writeFile(fileName, inputStream, null);
            if (response.isSuccessful()) {
                return URL + fileName;
            } else {
                return url;
            }
        } catch (IOException | UpException e) {
            e.printStackTrace();
        }
        return url;
    }

}