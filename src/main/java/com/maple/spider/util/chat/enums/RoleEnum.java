package com.maple.spider.util.chat.enums;

/**
 * @Author: asleepyfish
 * @Date: 2023/3/3 14:44
 * @Description: RoleEnum
 */
public enum RoleEnum {
    /**
     * system
     */
    SYSTEM("system"),
    ASSISTANT("assistant"),
    USER("user");
    private final String roleName;

    RoleEnum(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }
}
