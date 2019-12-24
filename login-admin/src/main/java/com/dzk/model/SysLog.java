package com.dzk.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class SysLog implements Serializable {

    private Long id;

    private String username;

    private String operation;

    private String method;

    private String params;

    private Long time;

    private String ip;

    private Date create_time;

    private static final long serialVersionUID = 1L;


}