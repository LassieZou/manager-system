package org.qqz.config;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    @Bean
    public Realm realm(HashedCredentialsMatcher matcher) {
        // 自定义的Realm，用于身份验证和授权
        MyRealm realm = new MyRealm();
        realm.setCredentialsMatcher(matcher);
        return realm;
    }

    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher(){
        return new CustomCredentialsMatch();
    }

    @Bean
    public DefaultWebSecurityManager defaultWebSecurityManager(Realm realm){
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        defaultWebSecurityManager.setRealm(realm);
        return defaultWebSecurityManager;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        shiroFilterFactoryBean.setLoginUrl("/login");
        // 配置权限不足的请求
        shiroFilterFactoryBean.setUnauthorizedUrl("/unauthorized");
        // 配置过滤链
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        filterChainDefinitionMap.put("/admin/**", "login,roles[admin]");
        filterChainDefinitionMap.put("/user/**", "login,roles[user]");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

        HashMap<String, Filter> hashMap = new HashMap<>();
        hashMap.put("login",new LoginFilter());
        shiroFilterFactoryBean.setFilters(hashMap);
        return shiroFilterFactoryBean;
    }
}


