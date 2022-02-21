package com.whx.workbench.service;

import com.whx.workbench.dao.ActivityRemarkDao;
import com.whx.workbench.domain.ActivityRemark;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


public interface ActivityRemarkService {


    boolean deleteById(String id);

    ActivityRemark saveRemark(ActivityRemark activityRemark);

    void updateRemarkById(ActivityRemark remark);
}
