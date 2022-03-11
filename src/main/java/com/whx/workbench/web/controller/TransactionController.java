package com.whx.workbench.web.controller;

import com.github.pagehelper.PageInfo;
import com.whx.workbench.domain.Contacts;
import com.whx.workbench.domain.Tran;
import com.whx.workbench.service.ContactsService;
import com.whx.workbench.service.TransactionService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/workbench/transaction")
public class TransactionController {
    @Resource
    ContactsService contactsService;
    @Resource
    TransactionService transactionService;


    @GetMapping("/saveTran.do")
    public ModelAndView saveTran(String contactsId, ModelAndView mv) {
        Contacts contacts = contactsService.getContactsById(contactsId);
        mv.addObject("c", contacts);
        mv.addObject("fromContacts", true);
        mv.setViewName("workbench/transaction/save");
        return mv;
    }

    @PostMapping("/pageList.do")
    public PageInfo<Tran> pageList(Tran tran, HttpSession session,String customerName, String contactsName, Integer pageNo, Integer pageSize) {
        tran.setCustomerId(customerName);
        tran.setContactsId(contactsName);
        PageInfo<Tran> tranInfo = transactionService.pageList(tran,pageNo,pageSize);
        session.setAttribute("tranInfo",tranInfo);
        return tranInfo;
    }


}
