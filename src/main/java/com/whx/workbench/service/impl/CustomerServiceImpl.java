package com.whx.workbench.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.whx.settings.dao.UserDao;
import com.whx.workbench.dao.CustomerDao;
import com.whx.workbench.domain.Customer;
import com.whx.workbench.service.CustomerService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Resource
    CustomerDao customerDao;
    @Resource
    UserDao userDao;


    @Override
    public PageInfo<Customer> pageList(Customer customer, Integer pageNo, Integer pageSize) {
        PageHelper.startPage(pageNo,pageSize);
        List<Customer> list=customerDao.pageList(customer);
        PageInfo<Customer> customerInfo=new PageInfo<>(list);
        return customerInfo;
    }
}
