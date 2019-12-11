package com.dzk.dao;

import com.dzk.model.UserAdminRole;

public interface UserAdminRoleMapper {

    int insert(UserAdminRole userAdminRole);

    int delete(Long id);
}
