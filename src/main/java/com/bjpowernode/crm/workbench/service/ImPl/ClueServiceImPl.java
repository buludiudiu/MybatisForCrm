package com.bjpowernode.crm.workbench.service.ImPl;



import com.bjpowernode.crm.utils.DateTimeUtil;
import com.bjpowernode.crm.utils.SqlSessionUtil;
import com.bjpowernode.crm.utils.UUIDUtil;
import com.bjpowernode.crm.vo.PaginationVO;
import com.bjpowernode.crm.workbench.dao.*;
import com.bjpowernode.crm.workbench.domain.*;
import com.bjpowernode.crm.workbench.service.ClueService;

import java.util.List;
import java.util.Map;

public class ClueServiceImPl implements ClueService {

    //线索
    private ClueDao clueDao = SqlSessionUtil.getSession().getMapper(ClueDao.class);
    private ClueActivityRelationDao clueActivityRelationDao = SqlSessionUtil.getSession().getMapper(ClueActivityRelationDao.class);
    private ClueRemarkDao clueRemarkDao = SqlSessionUtil.getSession().getMapper(ClueRemarkDao.class);
    //客户
    private CustomerDao customerDao = SqlSessionUtil.getSession().getMapper(CustomerDao.class);
    private CustomerRemarkDao customerRemarkDao = SqlSessionUtil.getSession().getMapper(CustomerRemarkDao.class);
    //联系人
    private ContactsDao contactsDao = SqlSessionUtil.getSession().getMapper(ContactsDao.class);
    private ContactsRemarkDao contactsRemarkDao = SqlSessionUtil.getSession().getMapper(ContactsRemarkDao.class);
    private ContactsActivityRelationDao contactsActivityRelationDao = SqlSessionUtil.getSession().getMapper(ContactsActivityRelationDao.class);
    //交易
    private TranDao tranDao = SqlSessionUtil.getSession().getMapper(TranDao.class);
    private TranHistoryDao tranHistoryDao = SqlSessionUtil.getSession().getMapper(TranHistoryDao.class);


    @Override
    public boolean saveClue(Clue clue) {
        boolean flag = true;
        int i = clueDao.saveClue(clue);
        if(i != 1){
            flag = false;
        }
        return flag;
    }

    @Override
    public PaginationVO<Clue> pageList(Map<String,Object> map) {
        PaginationVO<Clue> vo = new PaginationVO<>();
        int total = clueDao.getTotal(map);
        List<Clue> list = clueDao.getClueList(map);
        vo.setTotal(total);
        vo.setList(list);
        return vo;
    }

    @Override
    public Clue detail(String id) {
        Clue clue = clueDao.detail(id);
        return clue;
    }

    @Override
    public List<Activity> ShowActivityList(String id) {
        List<Activity> list = clueDao.ShowActivityList(id);
        return list;
    }

    @Override
    public boolean deleteActivityList(String id) {
        boolean flag = true;
        int count = clueDao.deleteActivityList(id);
        if(count != 1){
            flag = false;
        }
        return flag;
    }

    @Override
    public List<Activity> getActivity(Map<String,String> map) {
        List<Activity> list = clueDao.getActivity(map);
        return list;
    }

    @Override
    public void Bound(String[] ids, String clueId) {
        for(String activityId : ids){
            String id = UUIDUtil.getUUID();
            ClueActivityRelation c = new ClueActivityRelation();
            c.setId(id);
            c.setActivityId(activityId);
            c.setClueId(clueId);
            clueDao.Bound(c);
        }
    }

    @Override
    public List<Activity> getActivityByName(String name) {
        List<Activity> list = clueDao.getActivityByName(name);
        return list;
    }

    @Override
    public boolean convert(String clueId, Tran t, String createBy) {
        String createTime = DateTimeUtil.getSysTime();
        boolean flag = true;
        Clue clue = clueDao.getById(clueId);
        String company = clue.getCompany();
        Customer customer = customerDao.getCustomerByName(company);
        if(customer == null){
            customer = new Customer();
            customer.setId(UUIDUtil.getUUID());
            customer.setOwner(clue.getOwner());
            customer.setName(company);
            customer.setWebsite(clue.getWebsite());
            customer.setPhone(clue.getPhone());
            customer.setCreateBy(createBy);
            customer.setContactSummary(clue.getContactSummary());
            customer.setNextContactTime(clue.getNextContactTime());
            customer.setDescription(clue.getDescription());
            customer.setAddress(clue.getAddress());
            customer.setCreateTime(createTime);
            int count = customerDao.save(customer);
            if(count != 1){
                flag = false;
            }
        }

        Contacts contacts = new Contacts();
        contacts.setId(UUIDUtil.getUUID());
        contacts.setOwner(clue.getOwner());
        contacts.setSource(clue.getSource());
        contacts.setCustomerId(customer.getId());
        contacts.setFullname(clue.getFullname());
        contacts.setAppellation(clue.getAppellation());
        contacts.setEmail(clue.getEmail());
        contacts.setMphone(clue.getMphone());
        contacts.setJob(clue.getJob());
        contacts.setCreateBy(createBy);
        contacts.setCreateTime(createTime);
        contacts.setDescription(clue.getDescription());
        contacts.setContactSummary(clue.getContactSummary());
        contacts.setNextContactTime(clue.getNextContactTime());
        contacts.setAddress(clue.getAddress());
        int count1 = contactsDao.save(contacts);
        if(count1 != 1){
            flag = false;
        }

        List<ClueRemark> list = clueRemarkDao.getById(clueId);
        for (ClueRemark clueRemark : list){
            ContactsRemark contactsRemark = new ContactsRemark();
            CustomerRemark customerRemark = new CustomerRemark();
            contactsRemark.setId(UUIDUtil.getUUID());
            contactsRemark.setNoteContent(clueRemark.getNoteContent());
            contactsRemark.setCreateBy(createBy);
            contactsRemark.setCreateTime(createTime);
            contactsRemark.setEditFlag("0");
            contactsRemark.setContactsId(contacts.getId());
            int count2 =contactsRemarkDao.save(contactsRemark);
            if(count2 != 1){
                flag = false;
            }
            customerRemark.setId(UUIDUtil.getUUID());
            customerRemark.setNoteContent(clueRemark.getNoteContent());
            customerRemark.setCreateBy(createBy);
            customerRemark.setCreateTime(createTime);
            customerRemark.setEditFlag("0");
            customerRemark.setCustomerId(customer.getId());
            int count3 =customerRemarkDao.save(customerRemark);
            if(count3 != 1){
                flag = false;
            }
        }
        List<ClueActivityRelation> list1 = clueActivityRelationDao.getByclueId(clueId);
        for(ClueActivityRelation clueActivityRelation : list1){
            ContactsActivityRelation contactsActivityRelation = new ContactsActivityRelation();
            contactsActivityRelation.setId(UUIDUtil.getUUID());
            contactsActivityRelation.setContactsId(contacts.getId());
            contactsActivityRelation.setActivityId(clueActivityRelation.getActivityId());
            int count4 = contactsActivityRelationDao.save(contactsActivityRelation);
            if(count4 != 1){
                flag = false;
            }
        }
        if(t != null){
            t.setSource(clue.getSource());
            t.setOwner(clue.getOwner());
            t.setNextContactTime(clue.getNextContactTime());
            t.setDescription(clue.getDescription());
            t.setCustomerId(customer.getId());
            t.setContactSummary(clue.getContactSummary());
            t.setContactsId(contacts.getId());
            int count5 = tranDao.save(t);
            if(count5!=1){
                flag = false;
            }


            TranHistory tranHistory = new TranHistory();
            tranHistory.setId(UUIDUtil.getUUID());
            tranHistory.setCreateBy(createBy);
            tranHistory.setCreateTime(createTime);
            tranHistory.setExpectedDate(t.getExpectedDate());
            tranHistory.setMoney(t.getMoney());
            tranHistory.setStage(t.getStage());
            tranHistory.setTranId(t.getId());
            int count6 = tranHistoryDao.save(tranHistory);
            if(count6 != 1){
                flag = false;
            }
        }
        for (ClueRemark clueRemark : list){
            int count7 = clueRemarkDao.delete(clueRemark);
            if(count7 != 1){
                flag = false;
            }
        }
        for(ClueActivityRelation clueActivityRelation : list1){
            int count8 = clueActivityRelationDao.delete(clueActivityRelation);
            if(count8 != 1){
                flag = false;
            }
        }
        int count9 = clueDao.delete(clueId);
        if(count9 != 1){
            flag = false;
        }
        return flag;
    }
}
