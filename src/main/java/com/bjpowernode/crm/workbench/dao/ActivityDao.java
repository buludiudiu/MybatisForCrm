package com.bjpowernode.crm.workbench.dao;

import com.bjpowernode.crm.workbench.domain.Activity;

import java.util.List;
import java.util.Map;


public interface ActivityDao {


    void saveActivity(Activity activity);


    int getTotal(Map<String, Object> map);

    List<Activity> getActivityList(Map<String, Object> map);

    int deleteActivity(String[] activityids);


    Activity getActivityById(String id);

    int update(Activity activity);

    Activity detail(String id);

    void deleteActivityById(String id);

    List<Activity> ShowActivityList(String name);
}
