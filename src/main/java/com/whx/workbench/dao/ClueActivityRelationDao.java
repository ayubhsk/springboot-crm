package com.whx.workbench.dao;


import com.whx.workbench.domain.ClueActivityRelation;

import java.util.List;

public interface ClueActivityRelationDao {

    int add(ClueActivityRelation car);

    int unbund(String activityId, String clueId);

    int getNumByClueId(String clueId);

    int deleteByClueId(String clueId);

    List<ClueActivityRelation> selectByClueId(String clueId);
}
