package com.maple.spider.controller;

import com.alibaba.fastjson.JSONObject;
import com.maple.spider.util.chat.OpenAiUtils;
import com.theokanning.openai.completion.chat.ChatMessage;
import okhttp3.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


/**
 * @author zhangfuzeng
 * @date 2023/4/6
 */
@RestController
public class ChatGptController {

    @PostMapping("/chatTest")
    public List<String> chatTest(@RequestBody String content) {
        return OpenAiUtils.createChatCompletion(content, "testUser");
    }

    private static final String API_ENDPOINT = "https://openai.com/v1/completions";
    private static final String API_CHAT_ENDPOINT = "https://openai.com/v1/chat/completions";
    
    private static final String API_KEY = "sk-xxxx";

    @PostMapping(value = "/getChatInfo3", produces = "application/json;charset=UTF-8")
    public String getChatInfo3(@RequestBody String messages) throws IOException {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(180, TimeUnit.SECONDS)
                .readTimeout(180, TimeUnit.SECONDS)
                .build();

        List<ChatMessage> messageList = new ArrayList<>();
        ChatMessage message = new ChatMessage();
        message.setRole("user");
        message.setContent("接下来我会给你指令，生成相应的图片，我希望你用Markdown语言生成，不要用反引号，不要用代码框，你需要用Unsplash API，遵循以下的格式：https://source.unsplash.com/1600x900/?< PUT YOUR QUERY HERE >。你明白了吗？");
        messageList.add(message);

        ChatMessage message2 = new ChatMessage();
        message2.setRole("assistant");
        message2.setContent("是的，我明白了。请告诉我您需要生成的图片指令和查询内容。");
        messageList.add(message2);


        ChatMessage message3 = new ChatMessage();
        message3.setRole("user");
        message3.setContent(messages);
        messageList.add(message3);
        
        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
        JSONObject json = new JSONObject();
        json.put("messages", messageList);
        json.put("model", "gpt-3.5-turbo");
        json.put("max_tokens", 1024);
        json.put("temperature", 0.9);
        json.put("top_p", 1);
        json.put("frequency_penalty", 0.0);
        json.put("presence_penalty", 0.6);
        Request request = new Request.Builder()
                .url(API_CHAT_ENDPOINT)
                .header("Authorization", "Bearer " + API_KEY)
                .header("Content-Type", "application/json")
                .post(okhttp3.RequestBody.create(mediaType, json.toJSONString()))
                .build();

        Call call = client.newCall(request);
        call.timeout().timeout(180, TimeUnit.SECONDS);
        Response response = call.execute();

        String responseBody = response.body().string();
        System.out.println(responseBody);

        return responseBody;
    }

    @PostMapping(value = "/getChatInfo2", produces = "application/json;charset=UTF-8")
    public String getChatInfo2(@RequestBody List<ChatMessage> messages) throws IOException {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(180, TimeUnit.SECONDS)
                .readTimeout(180, TimeUnit.SECONDS)
                .build();
        
        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
        JSONObject json = new JSONObject();
        json.put("messages", messages);
        json.put("model", "gpt-3.5-turbo");
        json.put("max_tokens", 1024);
        json.put("temperature", 0.9);
        json.put("top_p", 1);
        json.put("frequency_penalty", 0.0);
        json.put("presence_penalty", 0.6);
        Request request = new Request.Builder()
                .url(API_CHAT_ENDPOINT)
                .header("Authorization", "Bearer " + API_KEY)
                .header("Content-Type", "application/json")
                .post(okhttp3.RequestBody.create(mediaType, json.toJSONString()))
                .build();

        Call call = client.newCall(request);
        call.timeout().timeout(180, TimeUnit.SECONDS);
        Response response = call.execute();

        String responseBody = response.body().string();
        System.out.println(responseBody);

        return responseBody;
    }

    @PostMapping(value = "/getChatInfo", produces = "application/json;charset=UTF-8")
    public String getChatInfo(@RequestBody String prompt) throws IOException {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(180, TimeUnit.SECONDS)
                .readTimeout(180, TimeUnit.SECONDS)
                .build();

        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
        JSONObject json = new JSONObject();
        json.put("prompt", prompt);
        json.put("model", "text-davinci-003");
        json.put("max_tokens", 1024);
        json.put("temperature", 0.9);
        json.put("top_p", 1);
        json.put("frequency_penalty", 0.0);
        json.put("presence_penalty", 0.6);
        Request request = new Request.Builder()
                .url(API_ENDPOINT)
                .header("Authorization", "Bearer " + API_KEY)
                .header("Content-Type", "application/json")
                .post(okhttp3.RequestBody.create(mediaType, json.toJSONString()))
                .build();

        Call call = client.newCall(request);
        call.timeout().timeout(180, TimeUnit.SECONDS);
        Response response = call.execute();

        String responseBody = response.body().string();
        System.out.println(responseBody);

        return responseBody;
    }
}