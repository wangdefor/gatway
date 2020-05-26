package com.cimen.ways.component.authentication.service.launcher.config;

import com.cimen.ways.component.authentication.service.launcher.handler.CustomAccessDeniedHandler;
import com.cimen.ways.component.authentication.service.launcher.properties.FilterIgnorePropertiesConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.web.access.AccessDeniedHandler;

/**
 * 资源服务器配置
 *
 */
@Configuration
@EnableResourceServer
public class AuthenticationResourceServerConfig extends ResourceServerConfigurerAdapter {

    private static final String RESOURCE_ID = "resource_id";

    /**
     * 开放权限的URL
     */
    private final FilterIgnorePropertiesConfig filterIgnorePropertiesConfig;


    @Autowired
    private JwtAccessTokenConverter jwtAccessTokenConverter;


    private final ObjectMapper objectMapper;

    /**
     * 加载配置
     * @param filterIgnorePropertiesConfig
     * @param objectMapper
     */
    @Autowired
    public AuthenticationResourceServerConfig(FilterIgnorePropertiesConfig filterIgnorePropertiesConfig,
                                              ObjectMapper objectMapper) {
        this.filterIgnorePropertiesConfig = filterIgnorePropertiesConfig;
        this.objectMapper = objectMapper;
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.resourceId(RESOURCE_ID).stateless(false);
        resources.accessDeniedHandler(accessDeniedHandler());
        resources.tokenStore(jwtTokenStore());
        resources.tokenServices(defaultTokenServices());
    }


    @Bean
    public JwtTokenStore jwtTokenStore(){
        return new JwtTokenStore(jwtAccessTokenConverter);
    }

    /**
     * http.apply  可以添加额外的资源服务端管理配置  做到多段登陆配置
     * @param http
     * @throws Exception
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        String[] ignores = new String[filterIgnorePropertiesConfig.getUrls().size()];
        http
                .csrf().disable()
                .httpBasic().disable()
                .authorizeRequests()
                .antMatchers(filterIgnorePropertiesConfig.getUrls().toArray(ignores)).permitAll()
                .anyRequest().authenticated()
                .and().exceptionHandling().accessDeniedHandler(new OAuth2AccessDeniedHandler());
    }

    @Bean
    public ResourceServerTokenServices defaultTokenServices(){
        final DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenEnhancer(jwtAccessTokenConverter);
        defaultTokenServices.setTokenStore(jwtTokenStore());
        return defaultTokenServices;
    }

    /**
     * 权限拒绝相关handler之后的处理
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(AccessDeniedHandler.class)
    public AccessDeniedHandler accessDeniedHandler() {
        return new CustomAccessDeniedHandler(objectMapper);
    }
}
