package com.whx.workbench.service;

import com.whx.settings.domain.User;
import com.whx.vo.PageVo;
import com.whx.workbench.domain.Activity;
import com.whx.workbench.domain.Clue;
import com.whx.workbench.domain.ClueRemark;
import com.whx.workbench.domain.Tran;

import java.util.List;

public interface ClueService {
    PageVo<Clue> pageList(Clue clue, Integer pageNo, Integer pageSize);

    boolean save(Clue clue);

    Clue getById(String id);


    boolean delete(String[] ids);

    Clue update(String id);

    boolean updateClue(Clue clue);

    List<Activity> remarkList(String id);

    List<Activity> getActivityListByNameAndNotByClueId(String aname, String clueId);

    boolean bundActivity(String[] ids, String clueId);

    boolean unbund(String activityId, String clueId);

    boolean deleteCAR(String id);

    String getOwnerIdBy(String id);

    List<ClueRemark> showRemarkList(String clueId);

    boolean deleteRemark(String id);

    boolean saveRemark(ClueRemark clueRemark);

    List<Activity> getActivityListByName(String aName);

    boolean convertCLue(String flag, String clueId, Tran tran, User user);
}
