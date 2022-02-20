package com.whx.Listener;

import com.whx.settings.domain.User;
import com.whx.settings.service.UserService;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.List;

public class SysInitListener implements ServletContextListener {
    @Resource
    UserService userService;

    @Override
    public void contextInitialized(ServletContextEvent event) {
        System.out.println("服务器缓存处理数据字典开始");
        ServletContext application = event.getServletContext();
        List<User> userList = userService.getAll();
        application.setAttribute("userList",userList);
    }
}