package com.whx.workbench.dao;

import com.whx.workbench.domain.ClueRemark;

import java.util.List;

public interface ClueRemarkDao {
    List<ClueRemark> getListByClueId(String clueId);

    int deleteById(String id);

    int insert(ClueRemark clueRemark);

    int deleteByClueId(String clueId);
}
