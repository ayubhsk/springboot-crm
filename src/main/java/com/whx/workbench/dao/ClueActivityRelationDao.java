package com.whx.workbench.dao;


import com.whx.workbench.domain.ClueActivityRelation;

public interface ClueActivityRelationDao {

    int add(ClueActivityRelation car);

    int unbund(String activityId, String clueId);
}
