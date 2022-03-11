package com.whx.workbench.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.whx.workbench.domain.Tran;

import java.util.ArrayList;
import java.util.List;

public interface TranDao extends BaseMapper<Tran> {
    int addTran(Tran tran);

    ArrayList<Tran> selectByContactsId(String contactsId);

    List<Tran> pageList(Tran tran);
}
