package com.dzk.controller;

import com.dzk.model.UserAdmin;
import com.dzk.service.UserAdminService;
import com.dzk.utils.CommonResult;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/sys/user")
public class UserAdminController {
    @Autowired
    private UserAdminService userAdminService;

    @PostMapping("/list")
    public CommonResult list(@RequestBody UserAdmin userAdminParam){

        List<UserAdmin> userList = userAdminService.userList(userAdminParam);

        if(userList != null){

            return  CommonResult.success(userList);
        }else {
            return  CommonResult.failed("查询无数据",null);
        }

    }
    @PostMapping("/edit")
    public CommonResult edit(@RequestBody UserAdmin userAdminParam){
        System.out.println("controller : "+userAdminParam.getStatus());
        int i = userAdminService.updateUser(userAdminParam);

        if(i != 0){
            return  CommonResult.success(i);
        }else {
            return  CommonResult.failed("修改失败",null);
        }

    }
    @PostMapping("/delete")
    public CommonResult delete(@RequestBody List<UserAdmin> userAdminList ){
        int i = userAdminService.deleteUser(userAdminList);

        if(i != 0){
            return  CommonResult.success(i);
        }else {
            return  CommonResult.failed("删除失败",null);
        }

    }
    @PostMapping("/insert")
    public CommonResult insert(@RequestBody UserAdmin userAdmin ){
        UserAdmin user = userAdminService.register(userAdmin);

        if(user != null){
            return  CommonResult.success(user);
        }else {
            return  CommonResult.failed("新增失败",null);
        }

    }
    @PostMapping("/insertAR")
    public CommonResult insertAR(@RequestBody UserAdmin userAdmin ){
        int i = 0;
        i = userAdminService.insertAR(userAdmin);
        if(i == 0){
            return CommonResult.failed("插入用户角色信息失败", null);
        }
        return CommonResult.success(i);
    }
}
