package com.cimen.ways.component.authentication.service.launcher.model;

import com.cimen.ways.component.authentication.service.launcher.enums.LoginTypeEnum;
import com.cimen.ways.component.authentication.service.launcher.enums.MemberType;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * 用户信息
 *
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
public class CustomUserDetails extends User {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("顾客ID")
    private Integer customId;

    @ApiModelProperty("会员ID")
    private Integer memberId;

    @ApiModelProperty("会员类型")
    private MemberType memberType;

    @ApiModelProperty("开始授权时间")
    private Long startAccessTime;

    @ApiModelProperty("登陆类型")
    private LoginTypeEnum loginType;

    /**
     * 构造方法
     *
     * @param username    username
     * @param password    password
     * @param enabled     enabled
     * @param authorities authorities
     * @param customId  顾客编号
     * @param startAccessTime   startAccessTime
     * @param loginType   loginType
     */
    public CustomUserDetails(String username, String password, Boolean enabled, Collection<? extends GrantedAuthority> authorities,
                             Integer customId, Integer memberId, Long startAccessTime, LoginTypeEnum loginType,MemberType memberType) {
        super(username, password, enabled, true, true, true, authorities);
        this.memberType = memberType;
        this.customId = customId;
        this.memberId = memberId;
        this.startAccessTime = startAccessTime;
        this.loginType = loginType;

    }
}
