package com.whx.workbench.dao;

import com.whx.workbench.domain.ContactsActivityRelation;
import org.apache.ibatis.annotations.Param;

public interface ContactsActivityRelationDao {

    int addRelation(ContactsActivityRelation contactsActivityRelation);

    int deleteByCIds(String[] ids);

    int deleteByAidAndCid(@Param("activityId") String activityId, @Param("contactsId") String contactsId);
}
