package com.whx.workbench.web.controller;

import com.whx.settings.domain.User;
import com.whx.settings.service.UserService;
import com.whx.utils.DateTimeUtil;
import com.whx.utils.UUIDUtil;
import com.whx.vo.PageVo;
import com.whx.workbench.domain.Activity;
import com.whx.workbench.service.ActivityService;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/workbench/activity")
public class ActivityController {
    @Resource
    ActivityService activityService;
    @Resource
    UserService userService;


    @GetMapping("/pageList.do")
    @ResponseBody
    public PageVo<Activity> pageList(Activity activity,
                                   Integer pageNo,Integer pageSize){

        PageVo<Activity> pageVo=activityService.pageList(activity,pageNo,pageSize);
        return pageVo;
    }

    @GetMapping("/detail.do")
    public String detail(Model model,String id){
        System.out.println("执行了detail.do方法");

        return "workbench/activity/detail";
    }

    @PostMapping("/delete.do")
    @ResponseBody
    public boolean delete(String[] ids){
        boolean flag=true;
        flag=activityService.delete(ids);
        return flag;
    }

    @GetMapping("/create.do")
    @ResponseBody
    public List<User> create(){
        List<User> list=userService.getAll();
        return list;
    }

    @GetMapping("/save.do")
    @ResponseBody
    public boolean save(Activity activity, HttpSession session){
        String createTime= DateTimeUtil.getSysTime();
        String createBy=((User)session.getAttribute("user")).getName();
        activity.setCreateTime(createTime);
        activity.setCreateBy(createBy);
        activity.setId(UUIDUtil.getUUID());
        boolean flag=activityService.save(activity);
        return flag;
    }

    @GetMapping("/edit.do")
    @ResponseBody
    public Activity edit(String id){
        Activity activity=activityService.selectById(id);
        return activity;
    }

    @GetMapping("/update.do")
    @ResponseBody
    public boolean update(Activity activity,HttpSession session){
        String editTime=DateTimeUtil.getSysTime();
        String editBy=((User)session.getAttribute("user")).getName();
        activity.setEditTime(editTime);
        activity.setEditBy(editBy);
        boolean flag=activityService.update(activity);
        return flag;
    }

}
