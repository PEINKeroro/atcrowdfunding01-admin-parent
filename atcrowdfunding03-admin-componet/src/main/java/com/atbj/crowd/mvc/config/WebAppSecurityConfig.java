package com.atbj.crowd.mvc.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

// 注意！这个类一定要放在自动扫描的包下，否则所有配置都不会生效！
//@Configuration            // 设置为配置类
//@EnableWebSecurity        // 开启web环境下的权限控制功能
// 需要继承WebSecurityConfigurerAdapter
public class WebAppSecurityConfig extends WebSecurityConfigurerAdapter {


}
