package com.maple.spider.util;

import com.maple.spider.bean.CommentQuery;
import com.maple.spider.bean.HttpReqInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * csdn 自动评论
 *
 * @author zhangfuzeng
 * @date 2022/11/25
 */
@Slf4j
public class CsdnCommentUtil {
    private static String cookie = "";

    public static void submitComment(CommentQuery query) {
        int stopFlag = 0;
        Document doc = getDocument(query.getUrl());
        if (Objects.isNull(doc)) {
            throw new RuntimeException("获取文章信息失败，请检查url是否正确");
        }

        // 过滤掉指定域名，正常一次评论，一个作者只评论一次
        List<String> authUrl = new ArrayList<>(Arrays.asList("zhangfz.blog.csdn.net", "qq_34988304"));
        Elements elements = doc.select(".Community-item-active.blog");
        for (Element e : elements) {
            if (stopFlag >= query.getCommentNum()) {
                break;
            }
            String url = e.getElementsByTag("a").get(0).attr("href");
            likeAndComment(url, authUrl);
            try {
                Thread.sleep(15000);
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
            stopFlag++;
        }
    }

    private static void likeAndComment(String url, List<String> authUrl) {
        log.info(url);
        Document doc = getDocument(url);
        // 点赞过的文章，代表已评论，不重复评论
        Elements elements = doc.select("#is-like-img");
        if (!elements.isEmpty()) {
            // 获取文章Id
            String[] urlArray = url.split("\\?")[0].split("/");
            String articleId = urlArray[urlArray.length - 1];
            if ("article".equals(urlArray[3])) {
                String auth = urlArray[2];
                if (authUrl.contains(auth)) {
                    return;
                }
                authUrl.add(auth);
            } else {
                String auth = urlArray[3];
                if (authUrl.contains(auth)) {
                    return;
                }
                authUrl.add(auth);
            }


            String a = elements.get(0).attr("style").replace(" ", "");
            // 未点赞，点赞+评论
            if (StringUtils.isNotBlank(a) && a.contains("display:block")) {
                String urlLike = String.format("https://blog.csdn.net//phoenix/web/v1/article/like?articleId=%s", articleId);
                List<HttpReqInfo> reqInfos = new ArrayList<>();
                reqInfos.add(new HttpReqInfo("Cookie", cookie, true));
                reqInfos.add(new HttpReqInfo("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8", true));
                HttpClientUtil.sendJsonPostHeader(HttpClientUtil.getSslHttpsClient(), urlLike, "", reqInfos);


                List<HttpReqInfo> reqInfos1 = new ArrayList<>();
                reqInfos1.add(new HttpReqInfo("Cookie", cookie, true));
                String urlComment = String.format("https://blog.csdn.net/phoenix/web/v1/comment/submit?commentId=&content=%s&articleId=%s", getComment(), articleId);
                HttpClientUtil.sendJsonPostHeader(HttpClientUtil.getSslHttpsClient(), urlComment, "", reqInfos1);
                log.info(url + "点赞+评论成功");
//                String followUrl = "https://mp-action.csdn.net/interact/wrapper/pc/fans/v1/api/follow";
//                List<HttpReqInfo> reqInfos2 = new ArrayList<>();
//                reqInfos2.add(new HttpReqInfo("Cookie", cookie, true));
//                JSONObject json = new JSONObject();
//                json.put("detailSourceName", "个人主页");
//                json.put("follow", "");
//                json.put("fromType", "pc");
//                json.put("source", "ME");
//                json.put("username", "qq_34988304");
//                HttpClientUtil.sendJsonPostHeader(HttpClientUtil.getSslHttpsClient(), followUrl, json.toJSONString(), reqInfos2);

            }
        }
    }


    /**
     * @param url 访问路径
     */
    public static Document getDocument(String url) {
        try {
            //5000是设置连接超时时间，单位ms
            return Jsoup.connect(url).header("Cookie", cookie).timeout(5000).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        submitComment(CommentQuery.builder().commentNum(10).url("https://blog.csdn.net/nav/java").build());
    }

    private static String getComment() {
        String[] commentArray = {
                "博主写的很好，逻辑比较清晰，学习到了。欢迎大佬来我这参观~",
                "找了很多篇博文，还是博主这篇比较优秀，逻辑比较清晰！欢迎大佬来我这参观~",
                "好文，欢迎大佬来我这参观~",
                "看了文章，我对大佬的膜拜犹如滔滔江水，连绵不绝！欢迎大佬来我这参观~",
                "文章内容丰富，条理清晰，赞一波，希望作者也指点我一番！",
                "最近整理了Java面试相关的面试题，欢迎大佬前往我的博客指导。",
                "读完本文后，感觉大侠文采飞扬，才华过人，在下对你的敬佩之意有如滔滔江水连绵不绝。在下笑小枫，欢迎大佬来我这参观~",
                "大佬，我是笑小枫，想和你交个朋友！",
                "我为了看你这篇文章乘坐歼十专机，路上和恐怖分子打了一场世界大战，终于来到了这里，说了一声：“你的文章写那么好干啥？弄的那些恐怖分子都跟我打架。”",
                "天马行空，我从此过；阅你文摘，心悦诚服；便留此言，希望友至；若能指导，它日必谢。"
                };
        return commentArray[RandomUtils.nextInt(0, 9)];
    }


}
