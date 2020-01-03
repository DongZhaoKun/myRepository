package com.dzk.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class UserPermissionNode extends UserPermission {

    private List<UserPermissionNode> children;

    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        return "UserPermissionNode{" +
                "children=" + children +
                '}';
    }
}