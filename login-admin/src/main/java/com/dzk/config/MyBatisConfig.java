package com.dzk.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan({"com.dzk.dao"})
public class MyBatisConfig {
}
