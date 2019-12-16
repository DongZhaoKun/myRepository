package com.dzk.service;

import com.dzk.model.UserAdmin;
import com.dzk.model.UserPermission;
import com.dzk.model.UserRole;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;


public interface UserAdminService {

        UserAdmin register(UserAdmin userAdmin);

        List<String> getPermissionList(Long adminId);

        UserAdmin queryByName(String userName);

        UserAdmin queryById(Long userId);

        List<UserAdmin> userList(UserAdmin userAdmin);

        Integer updateUser(UserAdmin userAdmin);

        Integer deleteUser(List<UserAdmin> userList);

        Integer insertAR(UserAdmin userAdmin);

        Integer deleteAR(Long userId);
}
