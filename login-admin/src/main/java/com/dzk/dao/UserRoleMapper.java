package com.dzk.dao;

import com.dzk.model.UserRole;

import java.util.List;

public interface UserRoleMapper {

    int insert(UserRole userRole);

    List<UserRole> roleList();

    int update(UserRole userRole);

    int delete(Long id);
}
