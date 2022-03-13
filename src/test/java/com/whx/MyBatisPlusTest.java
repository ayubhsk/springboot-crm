package com.whx;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.whx.workbench.dao.TranDao;
import com.whx.workbench.domain.Tran;
import com.whx.workbench.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class MyBatisPlusTest {
    @Resource
    TranDao tranDao;
    @Resource
    TransactionService transactionService;

    @Test
    public void testSelect(){
        QueryWrapper<Tran> wrapper=new QueryWrapper<>();
        wrapper.like("name","卡");
        Tran tran = tranDao.selectOne(wrapper);
        System.out.println(tran);
    }

    @Test
    public void testSelect1(){
        LambdaQueryWrapper<Tran> wrapper=new LambdaQueryWrapper<>();
        wrapper.like(Tran::getName,"卡");
        Tran tran = tranDao.selectOne(wrapper);
        System.out.println(tran);
    }
}
