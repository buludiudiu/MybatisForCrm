package com.bjpowernode.crm.workbench.web.controller;

import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.utils.DateTimeUtil;
import com.bjpowernode.crm.utils.PrintJson;
import com.bjpowernode.crm.utils.ServiceFactory;
import com.bjpowernode.crm.utils.UUIDUtil;
import com.bjpowernode.crm.workbench.domain.*;
import com.bjpowernode.crm.workbench.service.CustomerService;
import com.bjpowernode.crm.workbench.service.ImPl.CustomerServiceImPl;
import com.bjpowernode.crm.workbench.service.ImPl.TranServiceImPl;
import com.bjpowernode.crm.workbench.service.TranService;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TranController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path =  request.getServletPath();
        if("/workbench/transaction/add.do".equals(path)){
            add(request,response);
        }else if("/workbench/transaction/ShowActivityList.do".equals(path)){
            ShowActivityList(request,response);
        }else if("/workbench/transaction/ShowContactsList.do".equals(path)){
            ShowContactsList(request,response);
        }else if("/workbench/transaction/getCustomerName.do".equals(path)){
            getCustomerName(request,response);
        }else if("/workbench/transaction/save.do".equals(path)){
            save(request,response);
        }else if("/workbench/transaction/detail.do".equals(path)){
            detail(request,response);
        }else if("/workbench/transaction/getHistoryListById.do".equals(path)){
            getHistoryListById(request,response);
        }else if("/workbench/transaction/changeStage.do".equals(path)){
            changeStage(request,response);
        }else if("/workbench/transaction/getCharts.do".equals(path)){
            getCharts(request,response);
        }
    }

    private void getCharts(HttpServletRequest request, HttpServletResponse response) {
        TranService tranService =(TranService) ServiceFactory.getService(new TranServiceImPl());
        Map<String,Object> map = tranService.getCharts();
        PrintJson.printJsonObj(response,map);
    }

    private void changeStage(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        String stage = request.getParameter("stage");
        String money = request.getParameter("money");
        String expectedDate = request.getParameter("expectedDate");
        String editBy = ((User) request.getSession().getAttribute("user")).getName();
        Tran t = new Tran();
        t.setId(id);
        t.setStage(stage);
        t.setMoney(money);
        t.setExpectedDate(expectedDate);
        t.setEditBy(editBy);
        t.setEditTime(DateTimeUtil.getSysTime());
        ServletContext application = this.getServletContext();
        Map<String,String> pMap = (Map<String, String>) application.getAttribute("pMap");
        t.setPossibility(pMap.get(stage));
        TranService tranService =(TranService) ServiceFactory.getService(new TranServiceImPl());
        boolean flag = tranService.changeStage(t);
        Map<String,Object> map = new HashMap<>();
        map.put("success",flag);
        map.put("t",t);
        PrintJson.printJsonObj(response,map);
    }

    private void getHistoryListById(HttpServletRequest request, HttpServletResponse response) {
        TranService tranService =(TranService) ServiceFactory.getService(new TranServiceImPl());
        String id = request.getParameter("id");
        ServletContext application = this.getServletContext();
        Map<String,String> pMap = (Map<String, String>) application.getAttribute("pMap");
        List<TranHistory> list = tranService.getHistoryListById(id);
        for(TranHistory t : list){
            t.setPossibility(pMap.get(t.getStage()));
        }
        PrintJson.printJsonObj(response,list);
    }

    private void detail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        TranService tranService =(TranService) ServiceFactory.getService(new TranServiceImPl());
        ServletContext application = request.getServletContext();
        Map<String,String> pMap = (Map<String, String>) application.getAttribute("pMap");
        Tran t = tranService.detail(id);
        String value = pMap.get(t.getStage());
        t.setPossibility(value);
        request.setAttribute("t",t);
        request.getRequestDispatcher("detail.jsp").forward(request,response);
    }

    private void save(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String id = UUIDUtil.getUUID();
        String createTime = DateTimeUtil.getSysTime();
        User user =(User) request.getSession().getAttribute("user");
        String createBy = user.getName();
        String owner = request.getParameter("owner");
        String money = request.getParameter("money");
        String name = request.getParameter("name");
        String expectedDate = request.getParameter("expectedDate");
        String customerName = request.getParameter("customerName");
        String stage = request.getParameter("stage");
        String type = request.getParameter("type");
        String source = request.getParameter("source");
        String activityId = request.getParameter("activityId");
        String contactsId = request.getParameter("contactsId");
        String description = request.getParameter("description");
        String contactSummary = request.getParameter("contactSummary");
        String nextContactTime = request.getParameter("nextContactTime");
        Tran t = new Tran();
        t.setId(id);
        t.setContactsId(contactsId);
        t.setType(type);
        t.setMoney(money);
        t.setName(name);
        t.setExpectedDate(expectedDate);
        t.setStage(stage);
        t.setActivityId(activityId);
        t.setCreateTime(createTime);
        t.setCreateBy(createBy);
        t.setSource(source);
        t.setOwner(owner);
        t.setNextContactTime(nextContactTime);
        t.setDescription(description);
        t.setContactSummary(contactSummary);
        TranService tranService =(TranService) ServiceFactory.getService(new TranServiceImPl());
        boolean flag = tranService.save(t,customerName);
        if (flag){
            response.sendRedirect(request.getContextPath() + "/workbench/transaction/index.jsp");
        }
    }

    private void getCustomerName(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("1dfe13089e584b31b1390d3ff107a30d");
        CustomerService service =(CustomerService) ServiceFactory.getService(new CustomerServiceImPl());
        String name = request.getParameter("name");
        List<String> list= service.getCustomerName(name);
        PrintJson.printJsonObj(response,list);
    }

    private void ShowContactsList(HttpServletRequest request, HttpServletResponse response) {
        TranService service =(TranService) ServiceFactory.getService(new TranServiceImPl());
        String name = request.getParameter("name");
        List<Contacts> contactsList = service.ShowContactsList(name);
        PrintJson.printJsonObj(response,contactsList);
    }

    private void ShowActivityList(HttpServletRequest request, HttpServletResponse response) {
        TranService service =(TranService) ServiceFactory.getService(new TranServiceImPl());
        String name = request.getParameter("name");
        List<Activity> activityList=service.ShowActivityList(name);
        PrintJson.printJsonObj(response,activityList);
    }

    private void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        TranService service =(TranService) ServiceFactory.getService(new TranServiceImPl());
        List<User> userlist = service.getUserList();
        request.setAttribute("userlist",userlist);
        request.getRequestDispatcher("/workbench/transaction/save.jsp").forward(request,response);

    }
}
