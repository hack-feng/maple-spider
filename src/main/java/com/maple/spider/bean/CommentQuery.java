package com.maple.spider.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhangfuzeng
 * @date 2022/11/25
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentQuery {

    /**
     * 评论地址
     */
    private String url;

    /**
     * 评论次数
     * 根据评论地址的展开爬虫，自动评论
     */
    private Integer commentNum;
}
