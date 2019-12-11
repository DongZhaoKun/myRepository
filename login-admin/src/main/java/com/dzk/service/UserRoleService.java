package com.dzk.service;

import com.dzk.model.UserAdmin;
import com.dzk.model.UserPermission;
import com.dzk.model.UserRole;
import com.dzk.model.UserRolePermission;

import java.util.List;


public interface UserRoleService {

        Integer insert(UserRole userRole);

        List<UserRole> userRoleList();

        Integer updateRole(UserRole userRole);

        Integer deleteRole(List<UserRole> roleList);

        Integer insertRP(UserRole userRole);

        Integer deleteRP(Long roleId);
}
