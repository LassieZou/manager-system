package org.qqz.contoller;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.qqz.common.vo.Resp;
import org.qqz.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user/")
@Slf4j
public class UserContoller {
    @Autowired
    private UserService userService;
    @PostMapping("{resource}")
    @ResponseBody
    @ApiOperation(notes = "check access of users", value = "")
    public Resp addUser(@PathVariable String resource) {
        Subject subject = SecurityUtils.getSubject();
        Boolean permitted = userService.checkResource(Integer.valueOf((String) subject.getPrincipal()),resource);
        return permitted ? Resp.success("accessed"): Resp.fail("not permitted");
    }
}
