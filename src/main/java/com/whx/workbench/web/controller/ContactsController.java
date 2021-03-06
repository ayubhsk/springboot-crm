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
        String msg = "????????????";
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
        HashMap<Object, Object> hashMap = contactsService.detail(id);//????????????contacts???owner???id??????
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

        mv.setViewName("workbench/contacts/detail");
        return mv;
    }

    @GetMapping("/saveRemark.do")
    public String saveRemark(ContactsRemark contactsRemark, HttpSession session) {
        String msg = "??????????????????";
        User user = (User) session.getAttribute("user");
        contactsRemark.setEditFlag("0");
        contactsRemark.setCreateTime(DateTimeUtil.getSysTime());
        contactsRemark.setCreateBy(user.getName());
        contactsRemark.setId(UUIDUtil.getUUID());
        try {
            //???????????????????????????????????????????????????????????????
            Integer count = contactsService.saveRemark(contactsRemark);
        } catch (Exception e) {
            e.printStackTrace();
            msg = "??????????????????";
        }

        return msg;

    }

    @GetMapping("/loadContactsRemark.do")
    public boolean loadContactsRemark(HttpSession session, String contactsId) {
        List<ContactsRemark> remarkList = contactsService.loadContactsRemark(contactsId);
        session.setAttribute("remarkList", remarkList);
        return true;
    }

    @GetMapping("/loadContactsActivity.do")
    public boolean loadContactsActivity(HttpSession session, String contactsId) {
        List<Activity> activityList = contactsService.loadContactsActivity(contactsId);
        session.setAttribute("activityList", activityList);
        return true;
    }


    @GetMapping("/updateRemark.do")
    public String updateRemark(ContactsRemark remark, HttpSession session) {
        String msg = "??????????????????";
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
        String msg = "??????????????????";
        try {
            int count = contactsService.deleteRemarkById(id);
        } catch (Exception e) {
            e.printStackTrace();
            msg = e.getMessage();
        }
        return msg;
    }

    @GetMapping("/getActivityListByNameAndNotByContactsId.do")
    public List<Activity> getActivityListByNameAndNotByContactsId(String aname, String contactsId) {
        List<Activity> list = contactsService.getActivityListByNameAndNotByContactsId(aname, contactsId);
        return list;
    }

    @PostMapping("/bindActivitys.do")
    public String bindActivitys(String[] ids,String contactsId){
        String msg="????????????????????????";
        try {
            int count=contactsService.bindActivitys(ids,contactsId);
        } catch (Exception e) {
            e.printStackTrace();
            msg=e.getMessage();
        }
        return msg;
    }

    @PostMapping("/unBind.do")
    public String unBind(String contactsId,String activityId){
        String msg="??????????????????";
        try {
            int count=contactsService.unBind(contactsId,activityId);
        } catch (Exception e) {
            e.printStackTrace();
            msg=e.getMessage();
        }
        return msg;
    }
}
