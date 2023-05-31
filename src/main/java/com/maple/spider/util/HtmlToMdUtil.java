package com.maple.spider.util;

import com.maple.spider.bean.ArticleModel;
import com.maple.spider.bean.ArticleQuery;
import com.maple.spider.bean.SourceTemplateEnum;
import com.overzealous.remark.Options;
import com.overzealous.remark.Remark;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.overzealous.remark.Options.FencedCodeBlocks.ENABLED_TILDE;

/**
 * @author xiaoxiaofeng
 * @date 2022/7/25
 */
public class HtmlToMdUtil {


    public static ArticleModel getArticle(ArticleQuery query) {
        if (StringUtils.isNotBlank(query.getTemplate())) {
            query = SourceTemplateEnum.transQuery(query);
        }
        return readArticle(query);
    }

    public static ArticleModel readArticle(ArticleQuery query) {
        Document doc = getDocument(query.getUrl());
        if (Objects.isNull(doc)) {
            throw new RuntimeException("获取文章信息失败，请检查url是否正确");
        }
        ArticleModel model = new ArticleModel();
        model.setUrl(query.getUrl());
        //获取文章标题
        if (StringUtils.isNotBlank(query.getTitleSelector())) {
            Elements title = doc.select(query.getTitleSelector());
            if (Objects.nonNull(title) && !title.isEmpty()) {
                model.setTitle(title.get(0).text());
            }
        }

        // 获取文章作者
        if (StringUtils.isNotBlank(query.getAuthorSelector())) {
            Elements author = doc.select(query.getAuthorSelector());
            if (Objects.nonNull(author) && !author.isEmpty()) {
                model.setAuthor(author.get(0).text());
            }
        }

        // 获取文章描述
        if (StringUtils.isNotBlank(query.getDescSelector())) {
            Elements desc = doc.select(query.getDescSelector());
            if (Objects.nonNull(desc) && !desc.isEmpty()) {
                model.setDesc(desc.get(0).attr("content"));
            }
        }

        // 获取文章内容
        if (StringUtils.isNotBlank(query.getContentSelector())) {
            Elements content = doc.select(query.getContentSelector());
            if (Objects.nonNull(content) && !content.isEmpty()) {
                if ("BOOKSTACK".equals(query.getTemplate())) {
                    Elements toc = doc.select(".markdown-toc.editormd-markdown-toc");
                    toc.remove();
                    for (Element element : content) {
                        Elements aHref = element.getElementsByTag("a");
                        for (Element aElement : aHref) {
                            Elements img = aElement.getElementsByTag("img");
                            if (!img.isEmpty()) {
                                //获得img标签里面的src这个属性。也就是获取到了这个图片的地址
                                String src = img.attr("data-original");
                                img.attr("src", src);
                                aElement.attr("href", null);
                            }
                        }
                    }
                }

                model.setContentHtml(content.html());
                // 将获取到的内容从HTML格式转换为Markdown格式
                Options options = Options.github();
                Remark remark = new Remark(options);
//                model.setContentMd((remark.convert(content.html())));
                model.setContentMd(handleImage(remark.convert(content.html())));
            }
        }

        // 获取文章标签
        if (StringUtils.isNotBlank(query.getTabSelector())) {
            Elements tag = doc.select(query.getTabSelector());
            if (Objects.nonNull(tag) && !tag.isEmpty()) {
                List<String> tagList = new ArrayList<>();
                for (Element element : tag) {
                    tagList.add(element.text());
                }
                model.setTab(String.join(",", tagList));
                // 取mate标签下的内容
                if (StringUtils.isBlank(model.getTab())) {
                    model.setTab(tag.get(0).attr("content"));
                }
            }
        }
        System.out.println("获取文章信息完成");
        return model;
    }

    /**
     * @param url 访问路径
     */
    public static Document getDocument(String url) {
        try {
            //5000是设置连接超时时间，单位ms
            return Jsoup.connect(url).timeout(5000).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    static final Pattern PATTERN = Pattern.compile("\\[.*]: [http|https]+[://]+[0-9A-Za-z:/[-]_#[?][=][.][&]]*[png|jpg|gif|jpeg]");

    /**
     * 处理图片
     *
     * @param mdContent
     * @return
     */
    public static String handleImage(String mdContent) {
        Matcher m = PATTERN.matcher(mdContent);

        int index = 0;
        while (m.find() && index < 50) {
            index++;
            String url = m.group(0);
            String[] urlArray = url.split(": ");
            String a = "(!" + urlArray[0] + "[.*])|(![.*]" + urlArray[0] + ")";
            a = a.replace("[", "\\[");
            String imageUrl = UpyOssUtil.uploadUpy(urlArray[1].split("\\?")[0]);
            System.out.println(imageUrl);
            String b = "![笑小枫-www.xiaoxiaofeng.com](" + imageUrl + ")";
            mdContent = mdContent.replaceAll(a, b);
        }
        return mdContent;

    }


    public static void main(String[] args) {
        String html = "";
        // 将获取到的内容从HTML格式转换为Markdown格式
        Options options = Options.github();
        Remark remark = new Remark(options);
        System.out.println(remark.convert(html));
    }
}
