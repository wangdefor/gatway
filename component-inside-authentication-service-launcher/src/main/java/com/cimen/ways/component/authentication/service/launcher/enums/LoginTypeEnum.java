package com.cimen.ways.component.authentication.service.launcher.enums;

import lombok.*;

/**
 * 登录类型
 *
 * @author 登陆类型 目前通过密码登陆
 * @date 2019/07/02 09:45
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum LoginTypeEnum {

    /**
     * 账号密码登录
     */
    PWD("PWD", "账号密码登录", "/oauth/token");

    /**
     * 类型
     */
    private String type;

    /**
     * 描述
     */
    private String description;

    /**
     * 接口uri
     */
    private String uri;

}
