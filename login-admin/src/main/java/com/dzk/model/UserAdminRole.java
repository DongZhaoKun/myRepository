package com.dzk.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserAdminRole implements Serializable {
    private Long id;

    private Long role_id;

    private Long admin_id;

    private static final long serialVersionUID = 1L;


}