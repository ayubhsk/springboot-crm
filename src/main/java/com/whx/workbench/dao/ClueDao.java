package com.whx.workbench.dao;

import com.whx.workbench.domain.Clue;

import java.util.List;

public interface ClueDao {
    List<Clue> pageList(Clue clue, Integer skipCount, Integer pageSize);

    Integer pageTotal(Clue clue, Integer skipCount, Integer pageSize);

    int save(Clue clue);

    Clue getById(String id);

    int deleteById(String id);

    Clue getByIdNoChange(String id);

    int updateClue(Clue clue);

    String getOwnerIdById(String id);
}
