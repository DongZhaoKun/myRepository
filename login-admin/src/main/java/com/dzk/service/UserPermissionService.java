package com.dzk.service;

import com.dzk.model.UserPermission;
import com.dzk.model.UserRole;

import java.util.List;


public interface UserPermissionService {

        Integer insert(UserPermission userPermission);

        List<UserPermission> userPermissionList();

        List<UserPermission> userPermissionListByPid(Long pid);

        Integer updatePermission(UserPermission userPermission);

        Integer deletePermission(List<UserPermission> userPermissionList);
}
