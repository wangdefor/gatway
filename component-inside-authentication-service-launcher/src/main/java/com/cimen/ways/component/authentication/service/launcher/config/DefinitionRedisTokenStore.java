package com.cimen.ways.component.authentication.service.launcher.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.common.*;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @ClassName DefintionRedisTokenStore
 * @Description DefinitionRedisTokenStore
 * @Date 2020/5/26 11:02
 * @Author wangyong
 * @Version 1.0
 **/
@Component
public class DefinitionRedisTokenStore extends RedisTokenStore {


    @Autowired
    private ClientDetailsService clientDetailsService;

    public DefinitionRedisTokenStore(RedisConnectionFactory connectionFactory) {
        super(connectionFactory);
    }

    @Override
    public OAuth2Authentication readAuthentication(OAuth2AccessToken token) {
        OAuth2Authentication result = super
                .readAuthentication(token);
        if(result != null){
            // 如果token没有失效  更新AccessToken过期时间
            DefaultOAuth2AccessToken oAuth2AccessToken = (DefaultOAuth2AccessToken) token;
            //重新设置过期时间
            int validitySeconds = getAccessTokenValiditySeconds(result.getOAuth2Request());
            if (validitySeconds > 0) {
                oAuth2AccessToken.setExpiration(new Date(System.currentTimeMillis() + (validitySeconds * 1000L)));
            }
            //将重新设置过的过期时间重新存入redis, 此时会覆盖redis中原本的过期时间
            storeAccessToken(token, result);
            //刷新refresh token
            OAuth2RefreshToken refreshToken = token.getRefreshToken();
            this.revertRefreshToken(refreshToken,result);
        }
        return result;
    }

    /**
     * 重新设置refreshToken
     * @param refreshToken
     * @param result
     */
    private void revertRefreshToken(OAuth2RefreshToken refreshToken,OAuth2Authentication result) {
        DeExpiringOAuth2RefreshToken oAuth2RefreshToken = new DeExpiringOAuth2RefreshToken(refreshToken.getValue());
        int validitySeconds = getRefreshTokenValiditySeconds(result.getOAuth2Request());
        //系统时间可能会有2秒的延迟，所以需要大于这个延迟
        if(validitySeconds > 2){
            oAuth2RefreshToken.setExpiration(new Date(System.currentTimeMillis() + (validitySeconds * 1000L)));
            OAuth2Authentication oAuth2Authentication = super.readAuthenticationForRefreshToken(refreshToken);
            storeRefreshToken(oAuth2RefreshToken,oAuth2Authentication);
        }
    }


    protected int getAccessTokenValiditySeconds(OAuth2Request clientAuth) {
        if (clientDetailsService != null) {
            ClientDetails client = clientDetailsService.loadClientByClientId(clientAuth.getClientId());
            Integer validity = client.getAccessTokenValiditySeconds();
            if (validity != null) {
                return validity;
            }
        }
        return 0;
    }

    protected int getRefreshTokenValiditySeconds(OAuth2Request clientAuth) {
        if (clientDetailsService != null) {
            ClientDetails client = clientDetailsService.loadClientByClientId(clientAuth.getClientId());
            Integer validity = client.getRefreshTokenValiditySeconds();
            if (validity != null) {
                return validity;
            }
        }
        return 0;
    }
}
