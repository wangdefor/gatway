package com.cimen.ways.component.authentication.service.launcher.provider;

import com.cimen.ways.component.authentication.service.launcher.enums.LoginTypeEnum;
import com.cimen.ways.component.authentication.service.launcher.enums.Roles;
import com.cimen.ways.component.authentication.service.launcher.model.CustomUserDetails;
import com.cimen.ways.component.authentication.service.launcher.service.ICustomDetailQueryService;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 提供用户相关信息检验以及添加到token中
 * @author wangyong
 */
@Slf4j
public class UserDetailAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

    private PasswordEncoder passwordEncoder;

    private ICustomDetailQueryService customDetailQueryService;

    private String userNotFoundEncodedPassword;

    public UserDetailAuthenticationProvider(PasswordEncoder passwordEncoder, ICustomDetailQueryService customDetailQueryService) {
        this.passwordEncoder = passwordEncoder;
        this.customDetailQueryService = customDetailQueryService;
    }

    /**
     * 可以借鉴 DaoAuthenticationProvider 进行密码校验  也可以在此进行校验成功后的时间发布
     * @param userDetails
     * @param authentication
     * @throws AuthenticationException
     */
    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {

	}

    @Override
    protected void doAfterPropertiesSet() throws Exception {
        if (this.customDetailQueryService == null){
            throw new IllegalArgumentException("UserDetailsService can not be null");
        }
        this.userNotFoundEncodedPassword = this.passwordEncoder.encode("userNotFoundPassword");
    }

    /**
     * 加载用户信息
     *
     * @param username       username
     * @param authentication authentication
     * @return UserDetails
     * @throws AuthenticationException
     */
    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        UserDetails loadedUser;
        try {
            // 加载用户信息
            loadedUser = new CustomUserDetails("w","123456",true, Lists.newArrayList(Roles.ROLE_ADMIN),1,1,System.currentTimeMillis()/1000, LoginTypeEnum.PWD,null);
        } catch (UsernameNotFoundException notFound) {
            if (authentication.getCredentials() != null) {
                String presentedPassword = authentication.getCredentials().toString();
                passwordEncoder.matches(presentedPassword, userNotFoundEncodedPassword);
            }
            throw notFound;
        } catch (Exception tenantNotFound) {
			throw new InternalAuthenticationServiceException(tenantNotFound.getMessage(), tenantNotFound);
        }
        if (loadedUser == null) {
            throw new InternalAuthenticationServiceException("get user information failed");
        }
        return loadedUser;
    }


}
