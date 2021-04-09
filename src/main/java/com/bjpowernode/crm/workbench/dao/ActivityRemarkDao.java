package com.bjpowernode.crm.workbench.dao;

import com.bjpowernode.crm.workbench.domain.ActivityRemark;

import java.util.List;

public interface ActivityRemarkDao {
    int getCountById(String[] activityids);

    int deleteById(String[] activityids);

    List<ActivityRemark> getRemarkList(String id);

    void deleteRemarkById(String id);

    int deleteRemark(String id);

    int saveRemark(ActivityRemark activityRemark);

    int updateRemark(ActivityRemark activityRemark);
}
