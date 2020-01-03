package com.dzk.service.impl;

import com.dzk.dao.SysLogMapper;
import com.dzk.dao.UserRoleMapper;
import com.dzk.dao.UserRolePermissionMapper;
import com.dzk.model.SysLog;
import com.dzk.model.UserRole;
import com.dzk.model.UserRolePermission;
import com.dzk.service.SysLogService;
import com.dzk.service.UserRoleService;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class SysLogServiceImpl implements SysLogService{

    private static final Logger LOGGER = LoggerFactory.getLogger(SysLogServiceImpl.class);

    @Autowired
    private SysLogMapper sysLogMapper;

    @Override
    public List<SysLog> sysLogList(String  operation,Integer pageNum,Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return sysLogMapper.logList(operation);
    }

    @Override
    public Integer insert(SysLog sysLog) {
        return sysLogMapper.insert(sysLog);
    }
}
