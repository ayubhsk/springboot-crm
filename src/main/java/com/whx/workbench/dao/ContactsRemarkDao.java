package com.whx.workbench.dao;

import com.whx.workbench.domain.ContactsRemark;

import java.util.ArrayList;

public interface ContactsRemarkDao {
    int addContactsRemark(ContactsRemark contactsRemark);

    int deleteByCIds(String[] ids);

    ArrayList<ContactsRemark> selectByContactsId(String contactsId);


    int update(ContactsRemark remark);

    int deleteRemarkById(String id);
}
