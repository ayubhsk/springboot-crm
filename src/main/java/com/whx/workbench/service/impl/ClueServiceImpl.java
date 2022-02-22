package com.whx.workbench.service.impl;

import com.whx.excep.CountWrongException;
import com.whx.vo.PageVo;
import com.whx.workbench.dao.ClueDao;
import com.whx.workbench.domain.Clue;
import com.whx.workbench.service.ClueService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


@Service
public class ClueServiceImpl implements ClueService {
    @Resource
    ClueDao clueDao;

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
}
