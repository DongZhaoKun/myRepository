package com.dzk.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class
UserAdmin implements Serializable {
    private Long id;

    private String username;

    private String password;

    private String icon;

    private String email;

    private String nickName;

    private String salt;

    private Date createTime;

    private Date loginTime;

    private Integer status;

    private List<Long> roleIdList;

    private static final long serialVersionUID = 1L;


}