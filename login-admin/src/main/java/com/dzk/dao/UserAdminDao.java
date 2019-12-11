package com.dzk.dao;

import com.dzk.model.UserAdmin;

import java.util.List;

public interface UserAdminDao {

    int insert(UserAdmin userAdmin);

    UserAdmin queryByName(String userName);

    List<UserAdmin> userList(UserAdmin userAdmin);

    int update(UserAdmin userAdmin);

    int delete(Long id);
}
