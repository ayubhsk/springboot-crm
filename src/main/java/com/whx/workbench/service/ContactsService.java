package com.whx.workbench.service;

import com.github.pagehelper.PageInfo;
import com.whx.settings.domain.User;
import com.whx.vo.PageVo;
import com.whx.workbench.domain.Activity;
import com.whx.workbench.domain.Contacts;

import java.util.List;

public interface ContactsService {
    PageInfo<Contacts> pageList(Contacts contacts, Integer pageNo, Integer pageSize);

    List<String> getCustomerNames(String name);

    String save(Contacts contacts, String customerName, User user);

    String deleteByIds(String[] ids);

    Contacts edit(String id);

    boolean update(Contacts contacts, String customerName, User user);
}
