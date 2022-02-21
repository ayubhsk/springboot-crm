package com.whx.workbench.web.controller;

import com.whx.settings.domain.User;
import com.whx.utils.DateTimeUtil;
import com.whx.utils.UUIDUtil;
import com.whx.workbench.domain.Activity;
import com.whx.workbench.domain.ActivityRemark;
import com.whx.workbench.service.ActivityRemarkService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/workbench/ActivityRemark")
public class ActivityRemarkController {

    @Resource
    ActivityRemarkService activityRemarkService;


    @GetMapping("/deleteRemark.do")
    @ResponseBody
    public boolean deleteRemark(String id){
       boolean flag= activityRemarkService.deleteById(id);
       return flag;
    }

    @GetMapping("/saveRemark.do")
    @ResponseBody
    public ActivityRemark saveRemark(ActivityRemark activityRemark, HttpSession session){
        activityRemark.setId(UUIDUtil.getUUID());
        activityRemark.setCreateTime(DateTimeUtil.getSysTime());
        activityRemark.setCreateBy(((User)session.getAttribute("user")).getName());
        activityRemark.setEditFlag("0");
        ActivityRemark remark=activityRemarkService.saveRemark(activityRemark);
        return remark;
    }

    @GetMapping("/updateRemark.do")
    @ResponseBody
    public ActivityRemark updateRemark(ActivityRemark remark,HttpSession session){
        remark.setEditTime(DateTimeUtil.getSysTime());
        remark.setEditBy(((User)session.getAttribute("user")).getName());
        remark.setEditFlag("1");
        activityRemarkService.updateRemarkById(remark);
        return remark;
    }
}
