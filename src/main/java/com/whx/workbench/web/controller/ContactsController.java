package com.whx.workbench.web.controller;


import com.github.pagehelper.PageInfo;
import com.whx.settings.domain.User;
import com.whx.utils.DateTimeUtil;
import com.whx.utils.UUIDUtil;
import com.whx.vo.PageVo;
import com.whx.workbench.domain.*;
import com.whx.workbench.service.ContactsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/workbench/contacts")
public class ContactsController {
    @Resource
    ContactsService contactsService;


    @PostMapping("/pageList.do")
    public PageInfo<Contacts> pageList(Contacts contacts, Integer pageNo, Integer pageSize, HttpSession session) {
        PageInfo<Contacts> contactsInfo = contactsService.pageList(contacts, pageNo, pageSize);
        session.setAttribute("contactsInfo", contactsInfo);
        return contactsInfo;

    }

    @RequestMapping("/getCustomerName.do")
    public List<String> getCustomerName(String name) {
        List<String> customerNameList = contactsService.getCustomerNames(name);
        return customerNameList;
    }

    @PostMapping("/save.do")
    public String save(Contacts contacts, String customerName, HttpSession session) {
        User user = (User) session.getAttribute("user");
        String msg = contactsService.save(contacts, customerName, user);
        return msg;
    }

    @PostMapping("/delete.do")
    public String delete(String[] ids) {

        String msg = null;
        try {
            msg = contactsService.deleteByIds(ids);
        } catch (Exception e) {
            e.printStackTrace();
            msg = e.getMessage();
        }
        return msg;
    }

    @GetMapping("/showEdit.do")
    public Contacts showEdit(String id) {
        Contacts contacts = contactsService.edit(id);

        return contacts;
    }

    @PostMapping("/update.do")
    public String update(Contacts contacts, String customerName, HttpSession session) {
        String msg = "更新成功";
        User user = null;
        try {
            user = (User) session.getAttribute("user");
        } catch (Exception e) {
            e.printStackTrace();
            msg = e.getMessage();
        }
        boolean flag = contactsService.update(contacts, customerName, user);
        return msg;
    }

    @GetMapping("/detail.do/{id}")
    public ModelAndView detail(@PathVariable("id") String id, ModelAndView mv, HttpSession session) {
        HashMap<Object, Object> hashMap = contactsService.detail(id);//这获得的contacts的owner是id编号
        Contacts contacts = (Contacts) hashMap.get("contacts");
        session.setAttribute("contactsDetail", contacts);

        String ownerName = contactsService.getOwnerName(contacts.getOwner());
        session.setAttribute("contactsOwnerName", ownerName);

        ArrayList<ContactsRemark> remarkList = (ArrayList<ContactsRemark>) hashMap.get("remarkList");
        session.setAttribute("remarkList", remarkList);
        ArrayList<Activity> activityList = (ArrayList<Activity>) hashMap.get("activityList");
        session.setAttribute("activityList", activityList);
        ArrayList<Tran> tranList = (ArrayList<Tran>) hashMap.get("tranList");
        session.setAttribute("tranList", tranList);

        /*        session.setAttribute("contactsDetailMap", hashMap);*/

        mv.setViewName("/workbench/contacts/detail");
        return mv;
    }

    @GetMapping("/saveRemark.do")
    public String saveRemark(ContactsRemark contactsRemark, HttpSession session) {
        String msg = "添加备注成功";
        User user = (User) session.getAttribute("user");
        contactsRemark.setEditFlag("0");
        contactsRemark.setCreateTime(DateTimeUtil.getSysTime());
        contactsRemark.setCreateBy(user.getName());
        contactsRemark.setId(UUIDUtil.getUUID());
        try {
            //保存后，将备注列表取出，重新刷新页面的备注
            Integer count = contactsService.saveRemark(contactsRemark);
        } catch (Exception e) {
            e.printStackTrace();
            msg = "添加备注失败";
        }

        return msg;

    }

    @GetMapping("/loadContactsRemark.do")
    public boolean loadContactsRemark(HttpSession session, String contactsId) {
        List<ContactsRemark> remarkList = contactsService.loadContactsRemark(contactsId);
        session.setAttribute("remarkList", remarkList);
        return true;
    }

    @GetMapping("/updateRemark.do")
    public String updateRemark(ContactsRemark remark, HttpSession session) {
        String msg = "修改备注成功";
        remark.setEditTime(DateTimeUtil.getSysTime());
        remark.setEditBy(((User) session.getAttribute("user")).getName());
        remark.setEditFlag("1");
        try {
            int count = contactsService.updateRemark(remark);
        } catch (Exception e) {
            e.printStackTrace();
            msg = e.getMessage();
        }
        return msg;
    }

    @GetMapping("/deleteRemark.do")
    public String deleteRemark(String id) {
        String msg = "删除备注成功";
        try {
            int count=contactsService.deleteRemarkById(id);
        } catch (Exception e) {
            e.printStackTrace();
            msg=e.getMessage();
        }
        return msg;
    }
}
