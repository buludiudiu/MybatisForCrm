package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.domain.Contacts;
import com.bjpowernode.crm.workbench.domain.Tran;
import com.bjpowernode.crm.workbench.domain.TranHistory;

import java.util.List;
import java.util.Map;

public interface TranService {
    List<User> getUserList();

    List<Activity> ShowActivityList(String name);

    List<Contacts> ShowContactsList(String name);

    boolean save(Tran t, String customerName);

    Tran detail(String id);

    List<TranHistory> getHistoryListById(String id);

    boolean changeStage(Tran t);

    Map<String, Object> getCharts();
}
