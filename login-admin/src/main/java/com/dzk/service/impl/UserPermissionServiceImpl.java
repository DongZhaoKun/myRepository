package com.dzk.service.impl;

import com.alibaba.fastjson.JSON;
import com.dzk.dao.UserPermissionMapper;
import com.dzk.dao.UserRoleMapper;
import com.dzk.model.UserPermission;
import com.dzk.model.UserRole;
import com.dzk.service.UserPermissionService;
import com.dzk.service.UserRoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class UserPermissionServiceImpl implements UserPermissionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserPermissionServiceImpl.class);


    @Autowired
    private UserPermissionMapper userPermissionMapper;


    @Override
    public Integer insert(UserPermission userPermission) {
        userPermission.setCreate_time(new Date());
        return userPermissionMapper.insert(userPermission);
    }

    @Override
    public List<UserPermission> userPermissionList() {
        return userPermissionMapper.list();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<UserPermission> userPermissionListByPid(Long pid) {
        return userPermissionMapper.listByPid(pid);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserPermission userPermissionListById(Long id) {
        return userPermissionMapper.listById(id);
    }

    @Override
    public Integer updatePermission(UserPermission userPermission) {
        return userPermissionMapper.update(userPermission);
    }

    @Override
    public Integer deletePermission(List<UserPermission> userPermissionList) {
        int i = 0;
        for(UserPermission permission: userPermissionList){
            userPermissionMapper.delete(permission.getId());
            i++;
        }
        return i;
    }
}
