package com.whx.settings.web.controller;


import com.whx.settings.domain.User;
import com.whx.settings.service.UserService;
import com.whx.utils.MD5Util;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;


@Controller
@RequestMapping("/settings/user")
public class UserController {

    @Resource
    UserService userService;

    @PostMapping("/login.do")
    public String login(User loginUser, Model model, HttpSession session){
        loginUser.setLoginPwd(MD5Util.getMD5(loginUser.getLoginPwd()));
        User user=userService.login(loginUser);
        if(user==null){
            model.addAttribute("msg","输入账户或密码错误");
            return "login";
        }else {
            session.setAttribute("user",user);
            return "redirect:/workbench/index.html";//这里是假的重定向
        }
    }
}
