package com.dzk.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class UserRolePermission implements Serializable {
    private Long id;

    private Long role_id;

    private Long permission_id;

    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        return "UserRolePermission{" +
                "id=" + id +
                ", role_id=" + role_id +
                ", permission_id=" + permission_id +
                '}';
    }
}