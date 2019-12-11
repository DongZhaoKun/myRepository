package com.dzk.dao;

import com.dzk.model.UserRole;
import com.dzk.model.UserRolePermission;

import java.util.List;

public interface UserRolePermissionMapper {

    int insert(UserRolePermission userRolePermission);

    int delete(Long id);
}
