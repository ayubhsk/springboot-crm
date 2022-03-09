package com.whx.workbench.web.controller;

import com.github.pagehelper.PageInfo;
import com.whx.settings.dao.UserDao;
import com.whx.workbench.dao.CustomerDao;
import com.whx.workbench.domain.Customer;
import com.whx.workbench.service.CustomerService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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


    @GetMapping("/detail.do/{id}")
    public ModelAndView detail(@PathVariable("id") String id, ModelAndView mv){
        Customer customer=customerService.detail(id);
        mv.addObject("c",customer);
        mv.setViewName("workbench/customer/detail");
        return mv;
    }
}
