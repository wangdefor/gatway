package com.cimen.ways.component.authentication.service.launcher.converter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaSigner;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.util.JsonParser;
import org.springframework.security.oauth2.common.util.JsonParserFactory;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName JWTTokenConvert
 * @Description 设置jwt相关 convert
 * @Date 2020/5/21 15:41
 * @Author wangyong
 * @Version 1.0
 **/
public class JWTTokenConvert extends JwtAccessTokenConverter {


    @ApiModelProperty("相关签名算法")
    @Setter
    @Getter
    private RsaSigner signer;


    @Getter
    @Setter
    @ApiModelProperty("头部相关信息")
    private Map<String,String> headerMap;

    private JsonParser objectMapper = JsonParserFactory.create();


    public JWTTokenConvert(KeyPair keyPair, Map<String, String> headerMap) {
        super();
        super.setKeyPair(keyPair);
        this.signer = new RsaSigner((RSAPrivateKey) keyPair.getPrivate());
        this.headerMap = headerMap;
    }



    /**
     * 对token等相关信息进行校验解密  当然也可以通过  JwtHelper.decodeAndVerify() 进行校验 公钥
     *
     * @param accessToken
     * @param authentication
     * @return
     */
    @Override
    protected String encode(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        String content;
        try {
            content = this.objectMapper
                    .formatMap(getAccessTokenConverter().convertAccessToken(accessToken, authentication));
        } catch (Exception ex) {
            throw new IllegalStateException("Cannot convert access token to JSON", ex);
        }
        return JwtHelper.encode(content, getSigner(), this.getHeaderMap()).getEncoded();
    }

    /**
     * 将个人相关信息系放入 jwt中
     * @param accessToken
     * @param authentication
     * @return
     */
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        HashMap pars = JSONObject.parseObject(JSON.toJSONString(authentication.getPrincipal()),HashMap.class);
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(pars);
        return super.enhance(accessToken, authentication);
    }
}
