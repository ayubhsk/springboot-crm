package com.whx.workbench.service;

import com.whx.vo.PageVo;
import com.whx.workbench.domain.Activity;
import com.whx.workbench.domain.ActivityRemark;

import java.util.List;

public interface ActivityService {

    PageVo<Activity> pageList(Activity activity, Integer pageNo, Integer pageSize);

    boolean delete(String[] ids);


    boolean save(Activity activity);

    Activity selectById(String id);

    boolean update(Activity activity);

    Activity detail(String id);

    int detailDelete(String id);

    String selectOwnerId(String id);

    List<ActivityRemark> showRemarkList(String activityId);
}
