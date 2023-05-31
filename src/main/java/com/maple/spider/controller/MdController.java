package com.maple.spider.controller;

import com.maple.spider.bean.ArticleModel;
import com.maple.spider.bean.ArticleQuery;
import com.maple.spider.util.HtmlToMdUtil;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;

/**
 * @author xiaoxiaofeng
 * @date 2022/7/26
 */
@RestController
@RequestMapping("/md")
public class MdController {

    @RequestMapping("/transArticle")
    public ArticleModel transArticle(@RequestBody ArticleQuery query) {
        System.out.println("获取文章开始" + query.getUrl());
        return HtmlToMdUtil.getArticle(query);
    }

    @RequestMapping("/spiderLocal")
    public void spiderLocal(String url) {
        List<String> list = Arrays.asList("https://www.bookstack.cn/read/mysql-basic-note/0.md",
                "https://www.bookstack.cn/read/mysql-basic-note/1.md",
                "https://www.bookstack.cn/read/mysql-basic-note/2.md",
                "https://www.bookstack.cn/read/mysql-basic-note/3.md",
                "https://www.bookstack.cn/read/mysql-basic-note/4.md",
                "https://www.bookstack.cn/read/mysql-basic-note/5.md",
                "https://www.bookstack.cn/read/mysql-basic-note/6.md",
                "https://www.bookstack.cn/read/mysql-basic-note/7.md",
                "https://www.bookstack.cn/read/mysql-basic-note/8.md",
                "https://www.bookstack.cn/read/mysql-basic-note/9.md",
                "https://www.bookstack.cn/read/mysql-basic-note/10.md",
                "https://www.bookstack.cn/read/mysql-basic-note/11.md",
                "https://www.bookstack.cn/read/mysql-basic-note/12.md",
                "https://www.bookstack.cn/read/mysql-basic-note/13.md",
                "https://www.bookstack.cn/read/mysql-basic-note/14.md",
                "https://www.bookstack.cn/read/mysql-basic-note/15.md",
                "https://www.bookstack.cn/read/mysql-basic-note/16.md",
                "https://www.bookstack.cn/read/mysql-basic-note/17.md",
                "https://www.bookstack.cn/read/mysql-basic-note/18.md",
                "https://www.bookstack.cn/read/mysql-basic-note/19.md"
        );

        ArticleQuery query = new ArticleQuery();
        query.setTemplate("BOOKSTACK");


        for (String s : list) {
            query.setUrl(s);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ArticleModel articleModel = HtmlToMdUtil.getArticle(query);
            String path = "D:\\zhangfuzeng\\article\\MySQL基础笔记";
            createFile(path, articleModel);
        }
    }

    private static void createFile(String path, ArticleModel articleModel) {
        File pathFile = new File(path);
        if (pathFile.mkdirs()) {
            System.out.println(path + "is not exsit, this is auto create.");
        }
        File file = new File(path + File.separator + articleModel.getTitle() + ".md");
        try (OutputStream outputStream = new FileOutputStream(file);) {
            byte[] bytes = articleModel.getContentMd().getBytes();
            outputStream.write(bytes);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
