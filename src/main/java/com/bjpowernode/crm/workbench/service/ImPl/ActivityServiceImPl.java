package com.bjpowernode.crm.workbench.service.ImPl;


import com.bjpowernode.crm.settings.dao.UserDao;
import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.utils.SqlSessionUtil;
import com.bjpowernode.crm.vo.PaginationVO;
import com.bjpowernode.crm.workbench.dao.ActivityDao;
import com.bjpowernode.crm.workbench.dao.ActivityRemarkDao;
import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.domain.ActivityRemark;
import com.bjpowernode.crm.workbench.service.ActivityService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityServiceImPl implements ActivityService {
    private ActivityDao activityDao = SqlSessionUtil.getSession().getMapper(ActivityDao.class);
    private ActivityRemarkDao activityRemarkDao = SqlSessionUtil.getSession().getMapper(ActivityRemarkDao.class);
    private UserDao userDao = SqlSessionUtil.getSession().getMapper(UserDao.class);
    @Override
    public void saveActivity(String id, String owner, String name, String startDate, String endDate, String cost, String description, String createTime, String createBy) {
        Activity activity = new Activity();
        activity.setId(id);
        activity.setOwner(owner);
        activity.setName(name);
        activity.setStartDate(startDate);
        activity.setEndDate(endDate);
        activity.setCost(cost);
        activity.setDescription(description);
        activity.setCreateTime(createTime);
        activity.setCreateBy(createBy);
        activityDao.saveActivity(activity);
    }

    @Override
    public PaginationVO<Activity> pageList(Map<String, Object> map) {
        int total = activityDao.getTotal(map);
        List<Activity> list = activityDao.getActivityList(map);
        PaginationVO<Activity> vo = new PaginationVO<>();
        vo.setTotal(total);
        vo.setList(list);
        return vo;
    }

    @Override
    public boolean deleteActivity(String[] activityids) {
        boolean flag = true;
        int count1 = activityRemarkDao.getCountById(activityids);
        int count2 = activityRemarkDao.deleteById(activityids);
        if(count1 != count2){
            flag = false;
        }
        System.out.println(count2);
        int count3 = activityDao.deleteActivity(activityids);
        if(count3 != activityids.length){
            flag = false;
        }
        return flag;
    }

    @Override
    public Map<String, Object> getUserListandActivityById(String id) {
        List<User> list = userDao.getUserList();
        Activity activity = activityDao.getActivityById(id);
        Map<String,Object> map = new HashMap<>();
        map.put("list",list);
        map.put("activity",activity);
        return map;
    }

    @Override
    public boolean update(String id, String owner, String name, String startDate, String endDate, String cost, String description, String editTime, String editBy) {
        boolean flag = true;
        Activity activity = new Activity();
        activity.setId(id);
        activity.setOwner(owner);
        activity.setName(name);
        activity.setStartDate(startDate);
        activity.setEndDate(endDate);
        activity.setCost(cost);
        activity.setDescription(description);
        activity.setEditTime(editTime);
        activity.setEditBy(editBy);
        int count = activityDao.update(activity);
        if(count != 1){
            flag = false;
        }
        return flag;
    }

    @Override
    public Activity detail(String id) {
        Activity activity = activityDao.detail(id);
        return activity;
    }

    @Override
    public List<ActivityRemark> getRemarkList(String id) {
        List<ActivityRemark> list = activityRemarkDao.getRemarkList(id);
        return list;
    }

    @Override
    public void deleteById(String id) {
        activityRemarkDao.deleteRemarkById(id);
        activityDao.deleteActivityById(id);
    }

    @Override
    public boolean deleteRemark(String id) {
        boolean flag = true;
        int count = activityRemarkDao.deleteRemark(id);
        if(count != 1){
            flag= false;
        }
        return flag;
    }

    @Override
    public boolean saveRemark(ActivityRemark activityRemark) {
        boolean flag = true;
        int count = activityRemarkDao.saveRemark(activityRemark);
        if(count !=1){
            flag= false;
        }
        return flag;
    }

    @Override
    public boolean updateRemark(ActivityRemark activityRemark) {
        boolean flag = true;
        int count = activityRemarkDao.updateRemark(activityRemark);
        if(count !=1){
            flag= false;
        }
        return flag;
    }


}
