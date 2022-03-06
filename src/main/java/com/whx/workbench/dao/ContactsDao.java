package com.whx.workbench.dao;

import com.whx.workbench.domain.Contacts;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ContactsDao {
    int addContacts(Contacts contacts);

    List<Contacts> pageListInfo(Contacts contacts);


    int deleteByIds(String[] ids);


    Contacts selectById(String id);

    int updateContacts(Contacts contacts);
}
