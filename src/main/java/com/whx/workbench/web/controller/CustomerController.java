package com.whx.workbench.web.controller;

import com.github.pagehelper.PageInfo;
import com.whx.settings.dao.UserDao;
import com.whx.workbench.dao.CustomerDao;
import com.whx.workbench.domain.Customer;
import com.whx.workbench.service.CustomerService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/workbench/customer")
public class CustomerController {
    @Resource
    CustomerService customerService;


    @PostMapping("/pageList.do")
    public PageInfo<Customer> pageList(Customer customer, HttpSession httpSession, Integer pageNo, Integer pageSize) {
        PageInfo<Customer> customerInfo = customerService.pageList(customer, pageNo, pageSize);
        httpSession.setAttribute("customerInfo", customerInfo);
        return customerInfo;
    }
}
