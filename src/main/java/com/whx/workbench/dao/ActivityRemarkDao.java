package com.whx.workbench.dao;

import com.whx.workbench.domain.ActivityRemark;

import java.util.List;

public interface ActivityRemarkDao {

    List<ActivityRemark> getByActivityId(String activityId);

    int deleteById(String id);

    int saveRemark(ActivityRemark activityRemark);

    int updateRemarkById(ActivityRemark remark);
}
