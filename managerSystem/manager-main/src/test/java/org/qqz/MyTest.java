package org.qqz;


import org.junit.jupiter.api.Test;
import org.qqz.common.vo.ResourceInfo;
import org.qqz.service.AdminService;
import org.qqz.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class MyTest {
    @Autowired
    AdminService adminService;
    @Autowired
    UserService userService;
    @Test
    public void test(){
        ResourceInfo info = new ResourceInfo();
        info.setUserId("10001");
        List<String> endpoints = new ArrayList<>();
        endpoints.add("A");
        info.setEndpoint(endpoints);
        adminService.addUser(info);
        Assert.isTrue(!userService.checkResource(10001,"B"));
    }

}
