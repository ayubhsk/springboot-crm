package com.whx.workbench.service.impl;

import com.whx.excep.CountWrongException;
import com.whx.workbench.dao.ActivityRemarkDao;
import com.whx.workbench.domain.ActivityRemark;
import com.whx.workbench.service.ActivityRemarkService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class ActivityRemarkServiceImpl implements ActivityRemarkService {
    @Resource
    ActivityRemarkDao activityRemarkDao;


    @Override
    public boolean deleteById(String id) {
        int count=activityRemarkDao.deleteById(id);

        return count==1;
    }

    @Override
    @Transactional
    public ActivityRemark saveRemark(ActivityRemark activityRemark) {
        int count=activityRemarkDao.saveRemark(activityRemark);
        if(count!=1) throw new CountWrongException("保存备注出错");
        return activityRemark;

    }

    @Override
    public void updateRemarkById(ActivityRemark remark) {
        int count=activityRemarkDao.updateRemarkById(remark);
        if(count!=1) throw new CountWrongException("更新备注出错");
    }
}
