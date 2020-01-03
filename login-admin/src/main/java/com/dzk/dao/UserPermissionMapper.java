package com.dzk.dao;

import com.dzk.model.UserPermission;
import com.dzk.model.UserRole;

import java.util.List;

public interface UserPermissionMapper {

    int insert(UserPermission userPermission);

    List<UserPermission> list();

    List<UserPermission> listByRoleId(Long roleId);

    List<UserPermission> listByPid(Long pid);

    UserPermission listById(Long id);

    int update(UserPermission userPermission);

    int delete(Long id);
}
