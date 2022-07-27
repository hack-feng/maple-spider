package com.maple.spider.controller;

import com.maple.spider.bean.ArticleModel;
import com.maple.spider.bean.ArticleQuery;
import com.maple.spider.util.HtmlToMdUtil;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhangfuzeng
 * @date 2022/7/26
 */
@RestController
@RequestMapping("/md")
public class MdController {

    @RequestMapping("/transArticle")
    public ArticleModel transArticle(@RequestBody ArticleQuery query) {
        return HtmlToMdUtil.getArticle(query);
    }
}
