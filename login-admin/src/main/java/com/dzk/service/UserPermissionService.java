package com.dzk.service;

import com.dzk.model.UserPermission;
import com.dzk.model.UserPermissionNode;
import com.dzk.model.UserRole;

import java.util.List;


public interface UserPermissionService {

        Integer insert(UserPermission userPermission);

        List<UserPermission> userPermissionList();

        List<UserPermission> userPermissionListByPid(Long pid);

        List<UserPermission> userPermissionListByRoleId(Long roleId);

        List<UserPermissionNode> listTree();

        UserPermission userPermissionListById(Long id);

        Integer updatePermission(UserPermission userPermission);

        Integer deletePermission(List<UserPermission> userPermissionList);
}
