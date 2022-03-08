package com.whx.workbench.dao;

import com.whx.workbench.domain.Tran;

import java.util.ArrayList;

public interface TranDao {
    int addTran(Tran tran);

    ArrayList<Tran> selectByContactsId(String contactsId);
}
