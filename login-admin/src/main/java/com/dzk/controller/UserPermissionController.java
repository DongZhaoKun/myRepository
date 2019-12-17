package com.dzk.controller;

import com.dzk.model.UserPermission;
import com.dzk.model.UserRole;
import com.dzk.service.UserPermissionService;
import com.dzk.service.UserRoleService;
import com.dzk.utils.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sys/user")
public class UserPermissionController {
    @Autowired
    private UserPermissionService userPermissionService;

    @GetMapping("/permissionList")
    public CommonResult list(){

        List<UserPermission> permissionsList = userPermissionService.userPermissionList();

        if(permissionsList == null){
            return new CommonResult(500,"fail","查询失败");
        }
        return new CommonResult(200,"success",permissionsList);
    }
    @GetMapping("/permissionList/{pid}")
    public CommonResult listByPId(@PathVariable("pid") Long pid){

        List<UserPermission> permissionsList = userPermissionService.userPermissionListByPid(pid);

        if(permissionsList == null){
            return new CommonResult(500,"fail","查询失败");
        }
        return new CommonResult(200,"success",permissionsList);
    }

    @PostMapping("/insertPermission")
    public CommonResult insert(@RequestBody UserPermission userPermission){
        int i = 0;
        try{
             i = userPermissionService.insert(userPermission);
            if(i != 1){
                return new CommonResult(200,"success","无数据");
            }
        }catch (Exception e){
            e.printStackTrace();
            return new CommonResult(500,"faild",e.getMessage());
        }
        return new CommonResult(200,"success",i);

    }
    @PostMapping("/editPermission")
    public CommonResult update(@RequestBody UserPermission userPermission){
        int i = 0;
        try{
            i = userPermissionService.updatePermission(userPermission);
            if(i != 1){
                return new CommonResult(200,"success","修改失败");
            }
        }catch (Exception e){
            e.printStackTrace();
            return new CommonResult(500,"faild",e.getMessage());
        }
        return new CommonResult(200,"success",i);
    }
    @PostMapping("/deletePermission")
    public CommonResult delete(@RequestBody List<UserPermission> userPermissionList){
        int i = 0;
        try{
            i = userPermissionService.deletePermission(userPermissionList);
            if(i == 0){
                return new CommonResult(200,"success","删除失败");
            }
        }catch (Exception e){
            e.printStackTrace();
            return new CommonResult(500,"faild",e.getMessage());
        }
        return new CommonResult(200,"success",i);
    }
}
