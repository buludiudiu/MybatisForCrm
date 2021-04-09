package com.bjpowernode.crm.workbench.service.ImPl;

import com.bjpowernode.crm.settings.dao.UserDao;
import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.utils.DateTimeUtil;
import com.bjpowernode.crm.utils.SqlSessionUtil;
import com.bjpowernode.crm.utils.UUIDUtil;
import com.bjpowernode.crm.workbench.dao.*;
import com.bjpowernode.crm.workbench.domain.*;
import com.bjpowernode.crm.workbench.service.TranService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TranServiceImPl implements TranService {
    private UserDao userDao = SqlSessionUtil.getSession().getMapper(UserDao.class);
    private ContactsDao contactsDao = SqlSessionUtil.getSession().getMapper(ContactsDao.class);
    private CustomerDao customerDao = SqlSessionUtil.getSession().getMapper(CustomerDao.class);
    private TranDao tranDao = SqlSessionUtil.getSession().getMapper(TranDao.class);
    private TranHistoryDao tranHistoryDao = SqlSessionUtil.getSession().getMapper(TranHistoryDao.class);
    private ActivityDao activityDao = SqlSessionUtil.getSession().getMapper(ActivityDao.class);

    @Override
    public List<User> getUserList() {
        List<User> userList = userDao.getUserList();
        return userList;
    }

    @Override
    public List<Activity> ShowActivityList(String name) {
        List<Activity> list = activityDao.ShowActivityList(name);
        return list;
    }

    @Override
    public List<Contacts> ShowContactsList(String name) {
        List<Contacts> list = contactsDao.ShowContactsList(name);
        return list;
    }

    @Override
    public boolean save(Tran t, String customerName) {
        boolean flag = true;
        Customer customer = customerDao.getCustomerByName(customerName);
        if(customer == null){
            customer = new Customer();
            customer.setCreateTime(DateTimeUtil.getSysTime());
            customer.setDescription(t.getDescription());
            customer.setNextContactTime(t.getNextContactTime());
            customer.setContactSummary(t.getContactSummary());
            customer.setCreateBy(t.getCreateBy());
            customer.setName(customerName);
            customer.setOwner(t.getOwner());
            customer.setId(UUIDUtil.getUUID());
            int count = customerDao.save(customer);
            if(count != 1){
                flag = false;
            }
        }
        t.setCustomerId(customer.getId());
        int count =tranDao.save(t);
        if(count != 1){
            flag = false;
        }

        TranHistory tranHistory = new TranHistory();
        tranHistory.setId(UUIDUtil.getUUID());
        tranHistory.setCreateBy(t.getCreateBy());
        tranHistory.setCreateTime(t.getCreateTime());
        tranHistory.setExpectedDate(t.getExpectedDate());
        tranHistory.setMoney(t.getMoney());
        tranHistory.setStage(t.getStage());
        tranHistory.setTranId(t.getId());
        int count1 = tranHistoryDao.save(tranHistory);
        if(count1 != 1){
            flag = false;
        }
        return flag;
    }

    @Override
    public Tran detail(String id) {
        Tran tran = tranDao.detail(id);
        return tran;
    }

    @Override
    public List<TranHistory> getHistoryListById(String id) {
        List<TranHistory> list = tranHistoryDao.getHistoryListById(id);
        return list;
    }

    @Override
    public boolean changeStage(Tran t) {
        boolean flag = true;
        int count1 =tranDao.changeStage(t);
        if(count1 != 1){
            flag= false;
        }
        TranHistory tranHistory = new TranHistory();
        tranHistory.setId(UUIDUtil.getUUID());
        tranHistory.setCreateBy(t.getEditBy());
        tranHistory.setCreateTime(DateTimeUtil.getSysTime());
        tranHistory.setExpectedDate(t.getExpectedDate());
        tranHistory.setMoney(t.getMoney());
        tranHistory.setStage(t.getStage());
        tranHistory.setTranId(t.getId());
        int count2 = tranHistoryDao.save(tranHistory);
        if(count2 != 1){
            flag= false;
        }
        return flag;
    }

    @Override
    public Map<String, Object> getCharts() {
        Map<String, Object> map = new HashMap<>();
        int total = tranDao.getTotal();
        List<Map<String,Map>> list = tranDao.getCharts();
        map.put("total",total);
        map.put("dataList",list);
        return map;
    }


}
