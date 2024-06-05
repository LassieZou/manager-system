package org.qqz;

import com.alibaba.fastjson.JSON;
import org.junit.jupiter.api.Test;
import org.qqz.common.utils.Base64Utils;
import org.qqz.common.vo.ResourceInfo;
import org.qqz.common.vo.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class MyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void mvcTest() throws Exception{
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(10000);
        userInfo.setRole("admin");
        userInfo.setAccountName("administrator");
        String base64 = Base64Utils.encode(userInfo);
        ResourceInfo info = new ResourceInfo();
        info.setUserId("10001");
        List<String> endpoints = new ArrayList<>();
        endpoints.add("A");
        info.setEndpoint(endpoints);
        mockMvc.perform(MockMvcRequestBuilders.post("/admin/addUser")
                .contentType(MediaType.APPLICATION_JSON)
                .header("user_info",base64)
                .content(JSON.toJSONString(info)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    void mvcTest_403() throws Exception{
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(11000);
        userInfo.setRole("user");
        userInfo.setAccountName("user1");
        String base64 = Base64Utils.encode(userInfo);
        ResourceInfo info = new ResourceInfo();
        info.setUserId("10001");
        List<String> endpoints = new ArrayList<>();
        endpoints.add("A");
        info.setEndpoint(endpoints);
        mockMvc.perform(MockMvcRequestBuilders.post("/admin/addUser")
                .contentType(MediaType.APPLICATION_JSON)
                .header("user_info",base64)
                .content(JSON.toJSONString(info)))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }
}