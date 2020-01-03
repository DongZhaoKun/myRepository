package com.dzk.service;

import com.dzk.model.SysLog;
import com.dzk.model.UserRole;

import java.util.List;


public interface SysLogService {

        Integer insert(SysLog sysLog);

        List<SysLog> sysLogList(String operation,Integer pageNum,Integer pageSize);


}
