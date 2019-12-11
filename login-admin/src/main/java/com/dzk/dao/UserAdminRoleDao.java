package com.dzk.dao;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserAdminRoleDao {

    List<String> getPermissionList(Long adminId);
}
