package com.maple.spider.util;

import com.upyun.RestManager;
import com.upyun.UpException;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.Calendar;


/**
 * 又拍云 对象存储工具类
 *
 * @author 笑小枫
 */
@Slf4j
public class UpyOssUtil {

    private static final String BUCKET_NAME = "";
    private static final String OPERATOR_NAME = "";
    private static final String OPERATOR_PWD = "";
    private static final String URL = "";


    /**
     * 根据url上传文件到七牛云
     */
    public static String uploadUpy(String url) {
        if (!url.contains(".png") && !url.contains(".jpg") && !url.contains(".gif") && !url.contains(".jepg")) {
            return url;
        }
        Calendar calendar = Calendar.getInstance();
        String flag = "/";
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DATE);
        String filePath = "/spider/" + year + flag + month + flag + day + flag;
        String fileName = filePath + "xxf-" + System.currentTimeMillis() + getUrlName(url.substring(url.lastIndexOf(".")));
        RestManager restManager = new RestManager(BUCKET_NAME, OPERATOR_NAME, OPERATOR_PWD);

        URI u = URI.create(url);
        try (InputStream inputStream = u.toURL().openStream()) {

            byte[] bytes = IOUtils.toByteArray(inputStream);
            Response response = restManager.writeFile(fileName, bytes, null);
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

    private static String getUrlName(String url) {
        if (url.equals(".png") || url.equals(".jpg") || url.equals(".gif") || url.equals(".jepg")) {
            return url;
        }
        return ".jpg";
    }

}