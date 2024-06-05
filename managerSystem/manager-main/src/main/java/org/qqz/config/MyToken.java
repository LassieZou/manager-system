package org.qqz.config;

import org.apache.shiro.authc.UsernamePasswordToken;

public class MyToken extends UsernamePasswordToken {
    String roleInfo;

    public MyToken(String username, String password, String roleInfo) {
        super(username, password);
        this.roleInfo = roleInfo;
    }

    public String getRoleInfo() {
        return roleInfo;
    }

    public void setRoleInfo(String roleInfo) {
        this.roleInfo = roleInfo;
    }
}
