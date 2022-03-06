package com.whx.workbench.dao;

import com.whx.workbench.domain.Customer;

import java.util.List;

public interface CustomerDao {
    Customer selectByName(String company);

    int addByCustomer(Customer customer);

    List<String> queryCustomerNames(String name);

    List<Customer> pageList(Customer customer);
}
