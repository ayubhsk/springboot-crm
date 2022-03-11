package com.whx.workbench.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.whx.workbench.dao.TranDao;
import com.whx.workbench.domain.Tran;
import com.whx.workbench.service.TransactionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TransactionServiceImpl extends ServiceImpl<TranDao, Tran> implements TransactionService {
    @Resource
    TranDao tranDao;

    @Override
    public PageInfo<Tran> pageList(Tran tran, Integer pageNo, Integer pageSize) {
        PageHelper.startPage(pageNo,pageSize);
        List<Tran> list=tranDao.pageList(tran);
        PageInfo<Tran> tranInfo=new PageInfo<>(list);
        return tranInfo;

    }
}
