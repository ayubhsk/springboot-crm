package com.whx.workbench.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.whx.workbench.domain.Tran;

public interface TransactionService extends IService<Tran> {
    PageInfo<Tran> pageList(Tran tran, Integer pageNo, Integer pageSize);
}
