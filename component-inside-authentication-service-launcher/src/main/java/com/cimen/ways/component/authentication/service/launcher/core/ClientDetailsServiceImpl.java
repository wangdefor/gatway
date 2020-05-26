package com.cimen.ways.component.authentication.service.launcher.core;

import org.springframework.security.oauth2.common.exceptions.InvalidClientException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;

import javax.sql.DataSource;

/**
 * jdbc客户端service  根据客户端ID加载客户端信息
 *
 */
public class ClientDetailsServiceImpl extends JdbcClientDetailsService {

    public ClientDetailsServiceImpl(DataSource dataSource) {
        super(dataSource);
    }

    /**
     * 重写方法
     *
     * @param clientId clientId
     * @return ClientDetails
     * @author tangyi
     * @date 2019/03/30 23:31
     */
    @Override
    public ClientDetails loadClientByClientId(String clientId) throws InvalidClientException {
        return super.loadClientByClientId(clientId);
    }


}
