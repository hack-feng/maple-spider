package com.maple.spider.util.chat;

import com.theokanning.openai.service.OpenAiService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class ChatGPTAutoConfigure {
    
    @Autowired
    private ChatGPTProperties properties;

    @Bean
    public OpenAiService openAiService() {
        return StringUtils.isBlank(properties.getProxyHost()) ? new OpenAiService(properties.getToken(), Duration.ZERO) :
                new OpenAiProxyService(properties.getToken(), Duration.ZERO, properties.getProxyHost(), properties.getProxyPort());
    }

    @Bean
    public OpenAiUtils openAiUtils(OpenAiService openAiService, ChatGPTProperties properties) {
        return new OpenAiUtils(openAiService, properties);
    }
}
