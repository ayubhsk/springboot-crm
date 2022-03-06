package com.whx.workbench.web.controller;


import com.github.pagehelper.PageInfo;
import com.whx.settings.domain.User;
import com.whx.vo.PageVo;
import com.whx.workbench.domain.Activity;
import com.whx.workbench.domain.Contacts;
import com.whx.workbench.service.ContactsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/workbench/contacts")
public class ContactsController {
    @Resource
    ContactsService contactsService;


    @PostMapping("/pageList.do")
    public  PageInfo<Contacts> pageList(Contacts contacts, Integer pageNo, Integer pageSize, HttpSession session){
        PageInfo<Contacts> contactsInfo=contactsService.pageList(contacts,pageNo,pageSize);
        session.setAttribute("contactsInfo",contactsInfo);
        return contactsInfo;

    }

    @RequestMapping("/getCustomerName.do")
    public List<String> getCustomerName(String name){
        List<String> customerNameList=contactsService.getCustomerNames(name);
        return customerNameList;
    }

    @PostMapping("/save.do")
    public String save(Contacts contacts,String customerName,HttpSession session){
        User user= (User) session.getAttribute("user");
        String msg=contactsService.save(contacts,customerName,user);
        return msg;
    }

    @PostMapping("/delete.do")
    public String delete(String[] ids){

        String msg= null;
        try {
            msg = contactsService.deleteByIds(ids);
        } catch (Exception e) {
            e.printStackTrace();
            msg=e.getMessage();
        }
        return msg;
    }

    @GetMapping("/showEdit.do")
    public Contacts showEdit(String id){
        Contacts contacts=contactsService.edit(id);

        return contacts;
    }

    @PostMapping("/update.do")
    public String update(Contacts contacts,String customerName,HttpSession session){
        String msg="更新成功";
        User user= null;
        try {
            user = (User) session.getAttribute("user");
        } catch (Exception e) {
            e.printStackTrace();
            msg=e.getMessage();
        }
        boolean flag=contactsService.update(contacts,customerName,user);
        return msg;
    }


}
