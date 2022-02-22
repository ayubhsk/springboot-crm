package com.whx.workbench.service;

import com.whx.vo.PageVo;
import com.whx.workbench.domain.Clue;

public interface ClueService {
    PageVo<Clue> pageList(Clue clue, Integer pageNo, Integer pageSize);

    boolean save(Clue clue);

    Clue getById(String id);


    boolean delete(String[] ids);

    Clue update(String id);

    boolean updateClue(Clue clue);
}
