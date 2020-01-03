package com.dzk.controller;

import com.dzk.model.SysLog;
import com.dzk.model.UserAdmin;
import com.dzk.service.SysLogService;
import com.dzk.service.UserAdminService;
import com.dzk.utils.CommonResult;
import com.dzk.utils.JWTUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/sys/log")
public class SysLogController {
    @Autowired
    private SysLogService sysLogService;


    @PostMapping("/list")
    public CommonResult list(@RequestParam(value = "opration",required = false)  String opration,
                             @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                             @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum){

        List<SysLog> sysLogs = sysLogService.sysLogList(opration,pageNum,pageSize);

        if(sysLogs == null){
            return CommonResult.failed(null);
        }
        return  CommonResult.success(sysLogs);
    }

}
