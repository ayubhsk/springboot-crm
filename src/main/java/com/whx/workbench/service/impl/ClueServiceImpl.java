package com.whx.workbench.service.impl;

import com.whx.excep.CountWrongException;
import com.whx.utils.UUIDUtil;
import com.whx.vo.PageVo;
import com.whx.workbench.dao.ActivityDao;
import com.whx.workbench.dao.ClueActivityRelationDao;
import com.whx.workbench.dao.ClueDao;
import com.whx.workbench.domain.Activity;
import com.whx.workbench.domain.Clue;
import com.whx.workbench.domain.ClueActivityRelation;
import com.whx.workbench.service.ClueService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.lang.invoke.WrongMethodTypeException;
import java.util.List;


@Service
public class ClueServiceImpl implements ClueService {
    @Resource
    ClueDao clueDao;

    @Resource
    ActivityDao activityDao;

    @Resource
    ClueActivityRelationDao clueActivityRelationDao;

    @Override
    public PageVo<Clue> pageList(Clue clue, Integer pageNo, Integer pageSize) {
        Integer skipCount=(pageNo-1)*pageSize;
        List<Clue> list=clueDao.pageList(clue,skipCount,pageSize);

        Integer total=clueDao.pageTotal(clue,skipCount,pageSize);
        PageVo<Clue> pageVo=new PageVo<>();
        pageVo.setList(list);
        pageVo.setTotal(total);
        return pageVo;

    }

    @Override
    public boolean save(Clue clue) {
        int count=clueDao.save(clue);
        if(count!=1) throw new CountWrongException("线索保存出错");
        return count==1;
    }

    @Override
    public Clue getById(String id) {
        Clue clue=clueDao.getById(id);
        return clue;
    }

    @Override
    @Transactional
    public boolean delete(String[] ids) {
        int count=0;
        for (int i = 0; i <ids.length ; i++) {
            String id=ids[i];
            count+=clueDao.deleteById(id);
        }
        if(count!=ids.length) throw new CountWrongException("删除失败");
        return true;
    }

    @Override
    public Clue update(String id) {
        Clue clue=clueDao.getByIdNoChange(id);
        return clue;

    }

    @Override
    public boolean updateClue(Clue clue) {
        int count=clueDao.updateClue(clue);
        return count==1;
    }

    @Override
    public List<Activity> remarkList(String id) {
        List<Activity> list=activityDao.remarkList(id);
        return list;

    }

    @Override
    public List<Activity> getActivityListByNameAndNotByClueId(String aname, String clueId) {
        List<Activity> list=activityDao.getActivityListByNameAndNotByClueId(aname, clueId);
        return list;
    }

    @Override
    @Transactional
    public boolean bundActivity(String[] ids, String clueId) {
        int count=0;
        for (int i = 0; i <ids.length ; i++) {
            ClueActivityRelation car=new ClueActivityRelation();
            car.setActivityId(ids[i]);
            car.setClueId(clueId);
            car.setId(UUIDUtil.getUUID());
            count+=clueActivityRelationDao.add(car);
        }
        if(count!=ids.length) throw new CountWrongException("关联出错");
        return true;
    }

    @Override
    @Transactional
    public boolean unbund(String activityId, String clueId) {
        int count=clueActivityRelationDao.unbund(activityId,clueId);
        if(count!=1) throw new WrongMethodTypeException("解除关联失败");
        return true;
    }
}
