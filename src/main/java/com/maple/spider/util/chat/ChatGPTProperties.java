package com.maple.spider.util.chat;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: asleepyfish
 * @Date: 2023-02-07 22:15
 * @Description: ChatGPT request parameters
 */
@Data
@Configuration
public class ChatGPTProperties {
    /**
     * OpenAi token string "sk-XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"
     */
    @Value("${chatgpt.token}")
    private String token;

    /**
     * The name of the model to use.
     * Required if specifying a fine tuned model or if using the new v1/completions endpoint.
     */
    private String model = "text-davinci-003";

    /**
     * chatModel which use by createChatCompletion
     */
    private String chatModel = "gpt-3.5-turbo";

    /**
     * Timeout retries
     */
    private int retries = 5;

    /**
     * proxyHost
     */
    @Value("${chatgpt.proxyHost}")
    private String proxyHost;

    /**
     * proxyPort
     */
    @Value("${chatgpt.proxyPort}")
    private Integer proxyPort;

    /**
     * sessionExpirationTime
     */
    @Value("${chatgpt.sessionExpirationTime}")
    private Integer sessionExpirationTime;
}
