package com.dzk.controller;

import com.dzk.dao.UserRoleMapper;
import com.dzk.model.UserAdmin;
import com.dzk.model.UserPermission;
import com.dzk.model.UserRole;
import com.dzk.model.UserRolePermission;
import com.dzk.service.UserAdminService;
import com.dzk.service.UserRoleService;
import com.dzk.utils.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sys/user")
public class UserRoleController {
    @Autowired
    private UserRoleService userRoleService;

    @GetMapping("/roleList")
    public CommonResult list(){

        List<UserRole> roleList = userRoleService.userRoleList();

        if(roleList == null){
            return  CommonResult.failed("查询失败",null);
        }
        return  CommonResult.success(roleList);
    }

    @PostMapping("/insertRole")
    public CommonResult insert(@RequestBody UserRole userRole){
        int i = 0;
        try{
             i = userRoleService.insert(userRole);
            if(i != 1){
                return  CommonResult.success(null);
            }
        }catch (Exception e){
            e.printStackTrace();
            return  CommonResult.failed(e.getMessage(),null);
        }
        return  CommonResult.success(i);

    }
    @PostMapping("/editRole")
    public CommonResult update(@RequestBody UserRole userRole){
        int i = 0;
        try{
            i = userRoleService.updateRole(userRole);
            if(i != 1){
                return  CommonResult.failed("修改失败",null);
            }
        }catch (Exception e){
            e.printStackTrace();
            return  CommonResult.failed(e.getMessage(),null);
        }
        return  CommonResult.success(i);
    }
    @PostMapping("/deleteRole")
    public CommonResult delete(@RequestBody List<UserRole> userRoleList){
        int i = 0;
        try{
            i = userRoleService.deleteRole(userRoleList);
            if(i == 0){
                return  CommonResult.failed("删除失败",null);
            }
        }catch (Exception e){
            e.printStackTrace();
            return  CommonResult.failed(e.getMessage(),null);
        }
        return  CommonResult.success(i);
    }
    @PostMapping("/updateRP")
    public CommonResult updateRolePer(@RequestBody UserRole userRole){
        int i;
        i = userRoleService.insertRP(userRole);
        if(i == 0){
            return CommonResult.failed("插入角色权限关系失败", null);
        }
        return  CommonResult.success(i);
    }

}
