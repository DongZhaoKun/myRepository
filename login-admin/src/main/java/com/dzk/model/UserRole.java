package com.dzk.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class UserRole implements Serializable {
    private Long id;

    private String name;

    private String description;

    private Integer admin_count;

    private Date createTime;

    private Integer status;

    private Integer sort;

    private List<Long> permIdList;
    private static final long serialVersionUID = 1L;


}