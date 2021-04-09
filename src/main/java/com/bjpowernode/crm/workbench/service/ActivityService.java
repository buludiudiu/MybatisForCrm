package com.bjpowernode.crm.workbench.service;


import com.bjpowernode.crm.vo.PaginationVO;
import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.domain.ActivityRemark;

import java.util.List;
import java.util.Map;

public interface ActivityService {

    void saveActivity(String id, String owner, String name, String startDate, String endDate, String cost, String description, String createTime, String createBy);

    PaginationVO<Activity> pageList(Map<String, Object> map);


    boolean deleteActivity(String[] activityids);


    Map<String, Object> getUserListandActivityById(String id);

    boolean update(String id, String owner, String name, String startDate, String endDate, String cost, String description, String editTime, String editBy);

    Activity detail(String id);

    List<ActivityRemark> getRemarkList(String id);

    void deleteById(String id);

    boolean deleteRemark(String id);

    boolean saveRemark(ActivityRemark activityRemark);

    boolean updateRemark(ActivityRemark activityRemark);
}
