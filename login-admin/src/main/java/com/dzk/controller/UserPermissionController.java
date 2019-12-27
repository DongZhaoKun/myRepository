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
            return  CommonResult.failed("查询失败");
        }
        return  CommonResult.success(permissionsList);
    }

    @GetMapping("/permissionList/{pid}")
    public CommonResult listByPId(@PathVariable("pid") Long pid){

        List<UserPermission> permissionsList = userPermissionService.userPermissionListByPid(pid);

        if(permissionsList == null){
            return CommonResult.failed("查询失败");
        }
        return CommonResult.success(permissionsList);
    }

    @GetMapping("/permission/{id}")
    public CommonResult listById(@PathVariable("id") Long id){

        UserPermission permission = userPermissionService.userPermissionListById(id);

        if(permission == null){
            return CommonResult.failed("查询失败");
        }
        return CommonResult.success(permission);
    }

    @PostMapping("/insertPermission")
    public CommonResult insert(@RequestBody UserPermission userPermission){
        int i = 0;
        try{
             i = userPermissionService.insert(userPermission);
            if(i != 1){
                return  CommonResult.success("无数据",null);
            }
        }catch (Exception e){
            e.printStackTrace();
            return  CommonResult.failed(e.getMessage(),null);
        }
        return  CommonResult.success(i);

    }
    @PostMapping("/editPermission")
    public CommonResult update(@RequestBody UserPermission userPermission){
        int i = 0;
        try{
            i = userPermissionService.updatePermission(userPermission);
            if(i != 1){
                return  CommonResult.failed("修改失败",null);
            }
        }catch (Exception e){
            e.printStackTrace();
            return  CommonResult.failed(e.getMessage(),null);
        }
        return  CommonResult.success(i);
    }
    @PostMapping("/deletePermission")
    public CommonResult delete(@RequestBody List<UserPermission> userPermissionList){
        int i = 0;
        try{
            i = userPermissionService.deletePermission(userPermissionList);
            if(i == 0){
                return  CommonResult.failed("删除失败",null);
            }
        }catch (Exception e){
            e.printStackTrace();
            return  CommonResult.failed(e.getMessage(),null);
        }
        return  CommonResult.success(i);
    }
}
