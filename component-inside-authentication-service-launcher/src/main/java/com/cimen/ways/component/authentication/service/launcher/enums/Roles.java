package com.cimen.ways.component.authentication.service.launcher.enums;

import org.springframework.security.core.GrantedAuthority;

/**
 * 角色枚举
 *
 * @author tangyi
 * @date 2019/11/02 12:30
 */
public enum Roles implements GrantedAuthority {

    /**
     * 超级管理员
     */
    ROLE_ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}
