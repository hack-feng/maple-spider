package com.maple.spider.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 笑小枫
 * @date 2022/8/8
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpYunConf {

    private String nameSpace;

    private String userName;

    private String password;

    private String address;


}
