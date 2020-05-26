package com.cimen.ways.component.authentication.service.launcher.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.oauth2.common.DefaultOAuth2RefreshToken;
import org.springframework.security.oauth2.common.ExpiringOAuth2RefreshToken;

import java.util.Date;

/**
 * @ClassName DeExpiringOAuth2RefreshToken
 * @Description DeExpiringOAuth2RefreshToken
 * @Date 2020/5/26 11:34
 * @Author wangyong
 * @Version 1.0
 **/
public class DeExpiringOAuth2RefreshToken extends DefaultOAuth2RefreshToken implements ExpiringOAuth2RefreshToken {


    @Getter
    @Setter
    private Date expiration;

    /**
     * Create a new refresh token.
     *
     * @param value
     */
    public DeExpiringOAuth2RefreshToken(String value) {
        super(value);
    }

    @Override
    public Date getExpiration() {
        return expiration;
    }

    @Override
    public String getValue() {
        return super.getValue();
    }
}
