package com.maple.spider.util.chat.enums;


import com.maple.spider.util.chat.exception.ChatGPTException;

import java.util.Objects;

/**
 * @Author: asleepyfish
 * @Date: 2023-02-08 22:19
 * @Description: ModelEnum
 */
public enum ModelEnum {
    /**
     * gpt-3.5-turbo
     */
    GPT_35_TURBO("gpt-3.5-turbo", 4096),

    GPT_35_TURBO_0301("gpt-3.5-turbo-0301", 4096),
    /**
     * TEXT_DAVINCI_003
     */
    TEXT_DAVINCI_003("text-davinci-003", 4000),
    TEXT_CURIE_001("text-curie-001", 2048),
    TEXT_BABBAGE_001("text-babbage-001", 2048),
    TEXT_ADA_001("text-ada-001", 2048);
    private final String modelName;

    private final Integer maxTokens;

    ModelEnum(String modelName, Integer maxTokens) {
        this.modelName = modelName;
        this.maxTokens = maxTokens;
    }

    public String getModelName() {
        return modelName;
    }

    public Integer getMaxTokens() {
        return maxTokens;
    }

    public static Integer getMaxTokens(String modelName) {
        for (ModelEnum modelEnum : values()) {
            if (Objects.equals(modelEnum.getModelName(), modelName)) {
                return modelEnum.getMaxTokens();
            }
        }
        throw new ChatGPTException(ChatGPTErrorEnum.MODEL_SELECTION_ERROR);
    }
}
