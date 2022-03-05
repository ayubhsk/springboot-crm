package com.whx.workbench.dao;

import com.whx.workbench.domain.ContactsRemark;

public interface ContactsRemarkDao {
    int addContactsRemark(ContactsRemark contactsRemark);

    int deleteByCIds(String[] ids);
}
