package com.dzh.Shiro;

import com.dzh.entity.User;
import com.dzh.service.UserService;
import com.dzh.util.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AccountRealm extends AuthorizingRealm {
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    UserService userService;
    public boolean supports(AuthenticationToken token){
        return token != null && token instanceof JwtToken;
    }
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //获取用户权限
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //获取token，进行验证
        JwtToken jwtToken = (JwtToken) token;
        String userId = jwtUtils.getClaimByToken((String) jwtToken.getPrincipal()).getSubject();
        User user = userService.getById(userId);
        if(user == null){
            throw new AccountException("账户不存在！");
        }
        if(user.getStatus() == -1){
            throw new LockedAccountException("账户被锁定！");
        }
        AccountProfile profile = new AccountProfile();
        BeanUtils.copyProperties(user,profile);
        log.info("profile----------------->{}", profile.toString());
        System.out.println("getName() = "+getName());
        return new SimpleAuthenticationInfo(profile,jwtToken.getCredentials(),getName());
    }
}
