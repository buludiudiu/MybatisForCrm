package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.vo.PaginationVO;
import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.domain.Clue;
import com.bjpowernode.crm.workbench.domain.Tran;

import java.util.List;
import java.util.Map;

public interface ClueService {

    boolean saveClue(Clue clue);

    PaginationVO<Clue> pageList(Map<String,Object> map);

    Clue detail(String id);

    List<Activity> ShowActivityList(String id);

    boolean deleteActivityList(String id);

    List<Activity> getActivity(Map<String,String> map);

    void Bound(String[] ids, String clueId);

    List<Activity> getActivityByName(String name);

    boolean convert(String clueId, Tran t, String createBy);
}
