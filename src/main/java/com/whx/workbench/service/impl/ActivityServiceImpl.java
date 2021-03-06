package com.whx.workbench.service.impl;

import com.whx.excep.CountWrongException;
import com.whx.vo.PageVo;
import com.whx.workbench.dao.ActivityDao;
import com.whx.workbench.dao.ActivityRemarkDao;
import com.whx.workbench.domain.Activity;
import com.whx.workbench.domain.ActivityRemark;
import com.whx.workbench.service.ActivityService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


@Service
public class ActivityServiceImpl implements ActivityService {

    @Resource
    ActivityDao activityDao;

    @Resource
    ActivityRemarkDao activityRemarkDao;

    @Override
    public PageVo<Activity> pageList(Activity activity, Integer pageNo, Integer pageSize) {
        //计算略过的记录数
        PageVo<Activity> pageVo=new PageVo<>();
        int skipCount = (pageNo - 1) * pageSize;
        List<Activity> list=activityDao.pageList(activity,skipCount,pageSize);
        pageVo.setList(list);

        int total=activityDao.pageTotal(activity);
        pageVo.setTotal(total);
        return pageVo;
    }

    @Override
    public boolean delete(String[] ids) {
        int count=0;
        for (int i = 0; i <ids.length ; i++) {
            count+=activityDao.deleteById(ids[i]);
        }

        return count==ids.length;
    }

    @Override
    @Transactional
    public boolean save(Activity activity) {
        int count=activityDao.save(activity);
        if(count!=1) throw new CountWrongException("保存市场活动出错");
        return count==1;

    }

    @Override
    public Activity selectById(String id) {
        Activity activity=activityDao.selectById(id);
        return activity;

    }

    @Override
    public boolean update(Activity activity) {
        int count=activityDao.update(activity);
        return count==1;

    }

    @Override
    public Activity detail(String id) {
        Activity activity=activityDao.selectActivityById(id);
        return activity;
    }

    @Override
    public int detailDelete(String id) {
        int count=activityDao.deleteById(id);
        return count;

    }

    @Override
    public String selectOwnerId(String id) {
        String ownerId=activityDao.selectOwnerId(id);
        return ownerId;

    }

    @Override
    public List<ActivityRemark> showRemarkList(String activityId) {
        List<ActivityRemark> arList=activityRemarkDao.getByActivityId(activityId);
        return arList;

    }


}
