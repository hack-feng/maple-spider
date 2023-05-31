package com.maple.spider.util;

import com.upyun.RestManager;
import com.upyun.UpException;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import okhttp3.Response;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
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
    private static final String SHOW_URL = "";


    /**
     * 根据url上传文件到又拍云
     */
    public static String uploadUpy(String url) {
        if (!url.contains(".png") && !url.contains(".jpg") && !url.contains(".gif") && !url.contains(".jpeg")) {
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
        try (InputStream inputStream = u.toURL().openStream();
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

            URL urlFile = new URL(url);
            URLConnection conn = urlFile.openConnection();
            int size = conn.getContentLength();

            if (size > 1000 * 1024) {
                Thumbnails.of(inputStream).scale(0.8f).outputFormat("jpg").outputQuality(0.6).toOutputStream(outputStream);
            } else if (size > 500 * 1024) {
                Thumbnails.of(inputStream).scale(0.8f).outputFormat("jpg").outputQuality(0.8).toOutputStream(outputStream);
            } else if (size > 100 * 1024) {
                Thumbnails.of(inputStream).scale(0.9f).outputFormat("jpg").outputQuality(0.9).toOutputStream(outputStream);
            } else {
                Thumbnails.of(inputStream).scale(1f).outputQuality(1).toOutputStream(outputStream);
            }
            Response response = restManager.writeFile(fileName, outputStream.toByteArray(), null);
            if (response.isSuccessful()) {
                return SHOW_URL + fileName;
            } else {
                return url;
            }
        } catch (IOException | UpException e) {
            e.printStackTrace();
        }
        return url;
    }

    private static String getUrlName(String url) {
        if (url.equals(".png") || url.equals(".jpg") || url.equals(".gif") || url.equals(".jpeg")) {
            return url;
        }
        return ".jpg";
    }
}