package org.qqz.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Slf4j
public class MyRealm extends AuthorizingRealm {
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setRoles(principals.getRealmNames());
//        Set<String> roles = roleService.findRoleIdsByUserCode(code);
//        info.setRoles(roles);
        return info;
    }
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        MyToken myToken = (MyToken) token;
        /*Set<Object> set = new HashSet<>();
        set.add(token.getPrincipal());
        set.add(((MyToken) token).getRoleInfo());*/
        AuthenticationInfo info = new SimpleAuthenticationInfo(
                token.getPrincipal(),null,myToken.getRoleInfo()
        );
        return info;
    }
}
