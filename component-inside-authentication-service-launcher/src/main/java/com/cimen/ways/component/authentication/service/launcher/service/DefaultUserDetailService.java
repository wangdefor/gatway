package com.cimen.ways.component.authentication.service.launcher.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @ClassName DefaultUserDetailService
 * @Description 用于刷新token的时候 查询用户信息
 * @Date 2020/5/21 16:25
 * @Author wangyong
 * @Version 1.0
 **/
@Service("DefaultUserDetailsService")
public class DefaultUserDetailService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
