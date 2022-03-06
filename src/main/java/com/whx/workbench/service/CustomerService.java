package com.whx.workbench.service;

import com.github.pagehelper.PageInfo;
import com.whx.workbench.domain.Customer;


public interface CustomerService {
    PageInfo<Customer> pageList(Customer customer, Integer pageNo, Integer pageSize);
}
