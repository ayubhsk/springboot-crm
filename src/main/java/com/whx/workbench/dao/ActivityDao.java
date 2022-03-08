package com.whx.workbench.dao;

import com.whx.workbench.domain.Activity;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mr.Wang
 */
public interface ActivityDao {


    List<Activity> getAll();

    List<Activity> pageList(Activity activity, Integer skipCount, Integer pageSize);

    int pageTotal(Activity activity);

    int deleteById(String id);

    int save(Activity activity);

    Activity selectById(String id);

    int update(Activity activity);

    Activity selectActivityById(String id);


    String selectOwnerId(String id);

    List<Activity> remarkList(String id);

    List<Activity> getActivityListByNameAndNotByClueId(String aname, String clueId);

    List<Activity> selectListLikeName(String aName);

    ArrayList<Activity> selectByContactRelation(String contactsId);
}
