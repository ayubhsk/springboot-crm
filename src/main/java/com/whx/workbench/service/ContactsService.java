package com.whx.workbench.service;

import com.github.pagehelper.PageInfo;
import com.whx.settings.domain.User;
import com.whx.vo.PageVo;
import com.whx.workbench.domain.Activity;
import com.whx.workbench.domain.Contacts;
import com.whx.workbench.domain.ContactsRemark;
import com.whx.workbench.domain.Customer;

import java.util.HashMap;
import java.util.List;

public interface ContactsService {
    PageInfo<Contacts> pageList(Contacts contacts, Integer pageNo, Integer pageSize);

    List<String> getCustomerNames(String name);

    String save(Contacts contacts, String customerName, User user);

    String deleteByIds(String[] ids);

    Contacts edit(String id);

    boolean update(Contacts contacts, String customerName, User user);

    HashMap<Object,Object> detail(String id);

    String getOwnerName(String owner);

    Integer  saveRemark(ContactsRemark contactsRemark);

    List<ContactsRemark> loadContactsRemark(String contactsId);

    int updateRemark(ContactsRemark remark);

    int deleteRemarkById(String id);
}
