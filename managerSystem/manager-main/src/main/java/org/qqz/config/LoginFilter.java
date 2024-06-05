package org.qqz.config;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.qqz.common.utils.Base64Utils;
import org.qqz.common.vo.UserInfo;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

@Slf4j
public class LoginFilter extends AccessControlFilter {

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object o) {
        try {
            String userInfo = ((HttpServletRequest)request).getHeader(org.qqz.common.Consts.USER_INFO);
            String infoStr = Base64Utils.decode(userInfo);
            UserInfo info = JSON.parseObject(infoStr, UserInfo.class);
            log.info("preHandle:url {}, user {} accessing.",
                    ((HttpServletRequest)request).getRequestURL(),
                    infoStr);
            Subject subject = this.getSubject(request, response);
            if(!subject.isAuthenticated() || SecurityUtils.getSubject().getPrincipal().equals(subject.getPrincipal())){
                subject.login(new MyToken(String.valueOf(info.getUserId()), null,info.getRole()));
            }
            return subject.isAuthenticated();
        }catch (Exception e){
            log.error("LoginFilter error,{}",e);
            return false;
        }

    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse){
        return false;
    }
}
