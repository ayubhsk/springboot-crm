package com.whx.workbench.dao;

import com.whx.workbench.domain.Activity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ActivityDao {

    List<Activity> getAll();

    List<Activity> pageList(Activity activity, Integer skipCount, Integer pageSize);

    int pageTotal(Activity activity);

    int deleteById(String id);

    int save(Activity activity);

    Activity selectById(String id);

    int update(Activity activity);
}
