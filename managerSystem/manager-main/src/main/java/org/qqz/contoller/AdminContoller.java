package org.qqz.contoller;

import io.swagger.annotations.ApiOperation;
import org.qqz.common.vo.ResourceInfo;
import org.qqz.common.vo.Resp;
import org.qqz.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/admin/")
public class AdminContoller {
    @Autowired
    AdminService adminService;

    @PostMapping("addUser")
    @ResponseBody
    @ApiOperation(notes = "users could be added", value = "")
    public Resp addUser(@RequestBody ResourceInfo resourceInfo) {
        adminService.addUser(resourceInfo);
        return Resp.success(null);
    }
}
