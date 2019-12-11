package com.dzk.service.impl;

import com.dzk.dao.UserAdminDao;
import com.dzk.dao.UserAdminRoleDao;
import com.dzk.dao.UserAdminRoleMapper;
import com.dzk.dao.UserRoleMapper;
import com.dzk.model.UserAdmin;
import com.dzk.model.UserAdminRole;
import com.dzk.model.UserPermission;
import com.dzk.model.UserRole;
import com.dzk.service.UserAdminService;
import com.dzk.utils.JWTUtil;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class UserAdminServiceImpl implements UserAdminService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserAdminServiceImpl.class);

    @Autowired
    private UserAdminDao userAdminDao;
    @Autowired
    private UserAdminRoleDao userAdminRoleDao;
    @Autowired
    private UserAdminRoleMapper userAdminRoleMapper;

    @Override
    public UserAdmin register(UserAdmin userAdminParam) {

        UserAdmin user = queryByName(userAdminParam.getUsername());
        if(user != null){
            throw new RuntimeException("用户名存在");
        }
        UserAdmin userAdmin = new UserAdmin();
        BeanUtils.copyProperties(userAdminParam,userAdmin);
        userAdmin.setCreateTime(new Date());
        userAdmin.setStatus(0);
        userAdmin.setSalt(Long.toString(System.currentTimeMillis()));
        userAdmin.setPassword(new Md5Hash(userAdminParam.getPassword(), Long.toString(System.currentTimeMillis()), 2).toString());
        LOGGER.info("userAdmin", userAdmin);
        userAdminDao.insert(userAdmin);
        return userAdmin;
    }

    @Override
    public List<String> getPermissionList(Long adminId) {

        return userAdminRoleDao.getPermissionList(adminId);
    }

    @Override
    public UserAdmin queryByName(String userName) {
        return userAdminDao.queryByName(userName);
    }




    @Override
    public List<UserAdmin> userList(UserAdmin userAdmin) {
        return userAdminDao.userList(userAdmin);
    }

    @Override
    public Integer updateUser(UserAdmin userAdmin) {
        System.out.println("service :"+ userAdmin.getStatus());
        return userAdminDao.update(userAdmin);
    }

    @Override
    public Integer deleteUser(List<UserAdmin> userList) {
        int i = 0;
        for(UserAdmin user : userList){
            userAdminDao.delete(user.getId());
            i++ ;
        }
        return i;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer insertAR(UserAdmin userAdmin) {
        int i = 0;
        deleteAR(userAdmin.getId());
        UserAdminRole userAdminRole = new UserAdminRole();
        userAdminRole.setAdmin_id(userAdmin.getId());
        for(Long roleId : userAdmin.getRoleIdList()){
            userAdminRole.setRole_id(roleId);
            userAdminRoleMapper.insert(userAdminRole);
            i++;
        }
        return i;
    }

    @Override
    public Integer deleteAR(Long userId) {
        return userAdminRoleMapper.delete(userId);
    }


}
