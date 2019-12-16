package com.dzk.controller;

import com.alibaba.fastjson.JSON;
import com.dzk.model.UserAdmin;
import com.dzk.service.UserAdminService;
import com.dzk.shiro.JWTToken;
import com.dzk.utils.CommonResult;
import com.dzk.utils.JWTUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/sys/user")
public class UserLoginController {
    @Autowired
    private UserAdminService userAdminService;

    @Autowired
    private JWTUtil jwtUtil;
    @PostMapping("/register")
    public CommonResult register(@RequestBody UserAdmin userAdminParam){

        UserAdmin userAdmin = userAdminService.register(userAdminParam);

        if(userAdmin == null){
            return CommonResult.success(null);
        }
        return  CommonResult.success("注册成功");
    }
    @PostMapping("/login")
    public CommonResult login(@RequestBody UserAdmin userAdminParam){

            UserAdmin userAdmin = userAdminService.queryByName(userAdminParam.getUsername());

            if(userAdmin == null || !userAdmin.getPassword().equals(new Md5Hash(userAdminParam.getPassword(),
                    userAdmin.getSalt(), 2).toString())){
                return CommonResult.viladateFailed("用户名或密码错误");
            }
            if(userAdmin.getStatus() != 0){
                return CommonResult.failed("账号被锁定",null);
            }
            String token = jwtUtil.generateToken(userAdmin);
            return  CommonResult.success(token);
    }
    @GetMapping("/info")
    @RequiresPermissions("sys:user:info")
    public CommonResult userInfo(){
//        String userName = jwtUtil.getUserNameFromToken(token);
//        UserAdmin userAdmin = userAdminService.queryByName(userName);
        Map<String,Object> map = new HashMap<>();
        map.put("roles", "[test]");
        return  CommonResult.success(map);
    }
    @GetMapping("/logout")
    public CommonResult logout(){
        SecurityUtils.getSubject().logout();
        return  CommonResult.success(null);
    }
    @GetMapping("/401")
    @RequiresPermissions("test401")
    public CommonResult result(){

        return  CommonResult.unauthorized(null);


    }
    @GetMapping("/test")
    @RequiresPermissions("sys:user:test")
    public CommonResult test(){

        return  CommonResult.success("test");

    }
}
