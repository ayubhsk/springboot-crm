package com.whx.Listener;

import com.whx.settings.domain.DicType;
import com.whx.settings.domain.DicValue;
import com.whx.settings.domain.User;
import com.whx.settings.service.DicService;
import com.whx.settings.service.UserService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.List;


@Component
public class SysInitListener implements ServletContextListener {
    @Resource
    UserService userService;

    @Resource
    DicService dicService;
    @Override
    public void contextInitialized(ServletContextEvent event) {
        System.out.println("服务器缓存处理数据字典开始");
        ServletContext application = event.getServletContext();
        List<User> userList = userService.getAll();
        application.setAttribute("userList",userList);


        List<DicType> dicTypeList=dicService.getAllDicType();
        for (int i = 0; i <dicTypeList.size(); i++) {
            DicType dicType = dicTypeList.get(i);
            List<DicValue> dicValueList=dicService.getDicValueByCode(dicType.getCode());
            application.setAttribute(dicType.getCode()+"List",dicValueList);
            System.out.println(dicType.getCode()+"List添加成功");
        }


    }
}