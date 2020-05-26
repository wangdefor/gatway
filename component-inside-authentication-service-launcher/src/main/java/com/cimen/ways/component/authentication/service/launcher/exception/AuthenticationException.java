package com.cimen.ways.component.authentication.service.launcher.exception;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

/**
 * 自定义OauthException
 *
 * @author tangyi
 * @date 2020/2/29 14:12
 */
@JsonSerialize(using = AuthenticationExceptionSerializer.class)
public class AuthenticationException extends OAuth2Exception {
    public AuthenticationException(String msg) {
        super(msg);
    }
}
