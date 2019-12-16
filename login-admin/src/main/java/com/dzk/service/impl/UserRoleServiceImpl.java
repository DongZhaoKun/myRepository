package com.dzk.service.impl;

import com.dzk.dao.UserAdminDao;
import com.dzk.dao.UserAdminRoleDao;
import com.dzk.dao.UserRoleMapper;
import com.dzk.dao.UserRolePermissionMapper;
import com.dzk.model.UserAdmin;
import com.dzk.model.UserPermission;
import com.dzk.model.UserRole;
import com.dzk.model.UserRolePermission;
import com.dzk.service.UserAdminService;
import com.dzk.service.UserRoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class UserRoleServiceImpl implements UserRoleService{

    private static final Logger LOGGER = LoggerFactory.getLogger(UserRoleServiceImpl.class);


    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private UserRolePermissionMapper userRolePermissionMapper;

    @Override
    public Integer insert(UserRole userRole) {
        userRole.setCreateTime(new Date());
        return userRoleMapper.insert(userRole);
    }

    @Override
    public List<UserRole> userRoleList() {
        return userRoleMapper.roleList();
    }

    @Override
    public Integer updateRole(UserRole userRole) {
        return userRoleMapper.update(userRole);
    }

    @Override
    public Integer deleteRole(List<UserRole> roleList) {
        int i = 0;
        for(UserRole role : roleList){
            userRoleMapper.delete(role.getId());
            i++;
        }
        return i;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer insertRP(UserRole userRole) {
        int i = 0;
        deleteRP(userRole.getId());
        UserRolePermission userRolePermission = new UserRolePermission();
        userRolePermission.setRole_id(userRole.getId());
        for(Long perId: userRole.getPermIdList()){
            userRolePermission.setPermission_id(perId);
            userRolePermissionMapper.insert(userRolePermission);
            i++;
        }
        return i;
    }

    @Override
    public Integer deleteRP(Long roleId) {
        int i = 0;
        i = userRolePermissionMapper.delete(roleId);
        return i;
    }

    @Override
    public List<UserRole> userRole(Long userId) {
        return userRoleMapper.userRole(userId);
    }
}
