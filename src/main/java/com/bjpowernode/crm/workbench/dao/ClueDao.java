package com.bjpowernode.crm.workbench.dao;

import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.domain.Clue;
import com.bjpowernode.crm.workbench.domain.ClueActivityRelation;

import java.util.List;
import java.util.Map;

public interface ClueDao {


    int saveClue(Clue clue);

    int getTotal(Map<String,Object> map);

    List<Clue> getClueList(Map<String,Object> map);

    Clue detail(String id);

    List<Activity> ShowActivityList(String id);

    int deleteActivityList(String id);

    List<Activity> getActivity(Map<String,String> map);

    void Bound(ClueActivityRelation c);

    List<Activity> getActivityByName(String name);

    Clue getById(String clueId);

    int delete(String clueId);
}
