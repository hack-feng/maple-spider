package com.maple.spider.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * @author 笑小枫
 * @date 2022/7/25
 */
@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class ArticleModel {

    private String title;
    
    private String desc;

    private String url;

    private String author;

    private String contentHtml;
    
    private String contentMd;

    private String tab;
}
