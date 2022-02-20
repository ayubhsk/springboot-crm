package com.whx.workbench.service;

import com.whx.vo.PageVo;
import com.whx.workbench.domain.Activity;

import java.util.List;

public interface ActivityService {

    PageVo<Activity> pageList(Activity activity, Integer pageNo, Integer pageSize);

    boolean delete(String[] ids);


    boolean save(Activity activity);

    Activity selectById(String id);

    boolean update(Activity activity);
}
