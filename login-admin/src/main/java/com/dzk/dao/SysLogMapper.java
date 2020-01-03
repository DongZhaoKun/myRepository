package com.dzk.dao;

import com.dzk.model.SysLog;
import com.dzk.model.UserAdmin;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysLogMapper {

    int insert(SysLog sysLog);

    List<SysLog> logList(@Param("operation") String operation);


}
