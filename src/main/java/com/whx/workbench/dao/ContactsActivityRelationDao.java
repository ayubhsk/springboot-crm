package com.whx.workbench.dao;

import com.whx.workbench.domain.ContactsActivityRelation;

public interface ContactsActivityRelationDao {

    int addRelation(ContactsActivityRelation contactsActivityRelation);

    int deleteByCIds(String[] ids);
}
