package com.maple.spider.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * @author zhangfuzeng
 * @date 2022/7/25
 */
@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class ArticleQuery {

    private String template;

    private String url;

    private String titleSelector;
    
    private String descSelector;

    private String authorSelector;

    private String contentSelector;
    
    private String tabSelector;
}
