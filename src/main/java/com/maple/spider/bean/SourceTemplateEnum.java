package com.maple.spider.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 笑小枫
 * @date 2022/7/25
 */
@AllArgsConstructor
@Getter
public enum SourceTemplateEnum {

    /**
     *
     */
    CSDN("csdn", "title", ".follow-nickName", "article", ".tag-link", "meta[name=description]"),
    WOSHIPM("woshipm", "title", ".author.u-flex", ".article--content.grap", "meta[name=keywords]", "meta[name=description]"),
    BOOKSTACK("bookstack", "#article-title", "a[title=内容来源]", "article", "meta[name=keywords]", "meta[name=description]"),
    GITEE("gitee", "title", "", ".theme-default-content", "", "")
    ;

    private final String source;

    private final String titleSelector;

    private final String authorSelector;

    private final String contentSelector;

    private final String tabSelector;

    private final String descSelector;

    public static ArticleQuery transQuery(ArticleQuery query) {
        SourceTemplateEnum templateEnum = SourceTemplateEnum.valueOf(query.getTemplate());
        return ArticleQuery.builder()
                .url(query.getUrl())
                .template(query.getTemplate())
                .titleSelector(templateEnum.titleSelector)
                .authorSelector(templateEnum.authorSelector)
                .tabSelector(templateEnum.tabSelector)
                .contentSelector(templateEnum.contentSelector)
                .descSelector(templateEnum.descSelector)
                .build();

    }
}
