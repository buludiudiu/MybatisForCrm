package com.bjpowernode.crm.workbench.web.controller;

import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.settings.service.ImPl.UserServiceImPl;
import com.bjpowernode.crm.settings.service.UserService;
import com.bjpowernode.crm.utils.*;
import com.bjpowernode.crm.vo.PaginationVO;
import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.domain.Clue;
import com.bjpowernode.crm.workbench.domain.Tran;
import com.bjpowernode.crm.workbench.service.ClueService;
import com.bjpowernode.crm.workbench.service.ImPl.ClueServiceImPl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ClueController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getServletPath();
        if("/workbench/Clue/getUserList.do".equals(path)){
            getUserList(request,response);
        }else if("/workbench/Clue/saveClue.do".equals(path)){
            saveClue(request,response);
        }else if("/workbench/Clue/pageList.do".equals(path)){
            pageList(request,response);
        }else if("/workbench/Clue/detail.do".equals(path)){
            detail(request,response);
        }else if("/workbench/Clue/ShowActivityList.do".equals(path)){
            ShowActivityList(request,response);
        }else if("/workbench/Clue/deleteActivityList.do".equals(path)){
            deleteActivityList(request,response);
        }else if("/workbench/Clue/getActivity.do".equals(path)){
            getActivity(request,response);
        }else if("/workbench/Clue/Bound.do".equals(path)){
            Bound(request,response);
        }else if("/workbench/Clue/getActivityByName.do".equals(path)){
            getActivityByName(request,response);
        }else if("/workbench/Clue/convert.do".equals(path)){
            convert(request,response);
        }

    }

    private void convert(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String flag = request.getParameter("flag");
        String clueId = request.getParameter("clueId");
        Tran t = null;
        String createBy = ((User) request.getSession().getAttribute("user")).getName();
        if("aaa".equals(flag)){
            String money = request.getParameter("money");
            String name = request.getParameter("name");
            String expectedDate = request.getParameter("expectedDate");
            String stage = request.getParameter("stage");
            String activityId = request.getParameter("activityId");
            String uuid = UUIDUtil.getUUID();
            String createTime = DateTimeUtil.getSysTime();
            t = new Tran();
            t.setId(uuid);
            t.setMoney(money);
            t.setName(name);
            t.setExpectedDate(expectedDate);
            t.setStage(stage);
            t.setActivityId(activityId);
            t.setCreateTime(createTime);
            t.setCreateBy(createBy);
        }
        ClueService clueService =(ClueService) ServiceFactory.getService(new ClueServiceImPl());
        boolean flag1 = clueService.convert(clueId,t,createBy);
        if(flag1){
            response.sendRedirect(request.getContextPath()+"/workbench/clue/index.jsp");
        }

    }

    private void getActivityByName(HttpServletRequest request, HttpServletResponse response) {
        ClueService clueService =(ClueService) ServiceFactory.getService(new ClueServiceImPl());
        String name = request.getParameter("name");
        List<Activity> list = clueService.getActivityByName(name);
        PrintJson.printJsonObj(response,list);
    }

    private void Bound(HttpServletRequest request, HttpServletResponse response) {
        ClueService clueService =(ClueService) ServiceFactory.getService(new ClueServiceImPl());
        String[] ids = request.getParameterValues("id");
        String clueId = request.getParameter("clueId");
        clueService.Bound(ids,clueId);
        PrintJson.printJsonFlag(response,true);
    }

    private void getActivity(HttpServletRequest request, HttpServletResponse response) {
        ClueService clueService =(ClueService) ServiceFactory.getService(new ClueServiceImPl());
        String name = request.getParameter("name");
        String clueId = request.getParameter("clueId");
        Map<String,String> map = new HashMap<>();
        map.put("name",name);
        map.put("clueId",clueId);
        List<Activity> list = clueService.getActivity(map);
        PrintJson.printJsonObj(response,list);
    }

    private void deleteActivityList(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        ClueService clueService =(ClueService) ServiceFactory.getService(new ClueServiceImPl());
        boolean flag =clueService.deleteActivityList(id);
        PrintJson.printJsonFlag(response,flag);
    }

    private void ShowActivityList(HttpServletRequest request, HttpServletResponse response) {
        ClueService clueService =(ClueService) ServiceFactory.getService(new ClueServiceImPl());
        String id = request.getParameter("id");
        List<Activity> list = clueService.ShowActivityList(id);
        PrintJson.printJsonObj(response,list);
    }

    private void detail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ClueService clueService =(ClueService) ServiceFactory.getService(new ClueServiceImPl());
        String id = request.getParameter("id");
        Clue clue = clueService.detail(id);
        request.setAttribute("clue",clue);
        request.getRequestDispatcher("/workbench/clue/detail.jsp").forward(request,response);
    }

    private void pageList(HttpServletRequest request, HttpServletResponse response) {
        String pageNoStr = request.getParameter("pageNo");
        String pageSizeStr = request.getParameter("pageSize");
        int  pageNo = Integer.valueOf(pageNoStr);
        int  pageSize = Integer.valueOf(pageSizeStr);
        int skipCount = (pageNo-1)*pageSize;
        String fullname = request.getParameter("fullname");
        String company = request.getParameter("company");
        String phone = request.getParameter("phone");
        String source = request.getParameter("source");
        String owner = request.getParameter("owner");
        String mphone = request.getParameter("mphone");
        String state = request.getParameter("state");
        Map<String,Object> map = new HashMap<>();
        map.put("pageSize",pageSize);
        map.put("skipCount",skipCount);
        map.put("fullname",fullname);
        map.put("company",company);
        map.put("phone",phone);
        map.put("source",source);
        map.put("owner",owner);
        map.put("mphone",mphone);
        map.put("state",state);
        ClueService clueService =(ClueService) ServiceFactory.getService(new ClueServiceImPl());
        PaginationVO<Clue> vo = clueService.pageList(map);
        PrintJson.printJsonObj(response,vo);
    }

    private void saveClue(HttpServletRequest request, HttpServletResponse response) {
        ClueService clueService =(ClueService) ServiceFactory.getService(new ClueServiceImPl());
        String id = UUIDUtil.getUUID();
        User user =(User) request.getSession().getAttribute("user");
        String createBy = user.getName();
        String createTime = DateTimeUtil.getSysTime();
        String fullname = request.getParameter("fullname");
        String appellation = request.getParameter("appellation");
        String owner = request.getParameter("owner");
        String company = request.getParameter("company");
        String job = request.getParameter("job");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String website = request.getParameter("website");
        String mphone = request.getParameter("mphone");
        String state = request.getParameter("state");
        String source = request.getParameter("source");
        String description = request.getParameter("description");
        String contactSummary = request.getParameter("contactSummary");
        String nextContactTime = request.getParameter("nextContactTime");
        String address = request.getParameter("address");
        Clue clue = new Clue();
        clue.setId(id);
        clue.setCreateBy(createBy);
        clue.setCreateTime(createTime);
        clue.setFullname(fullname);
        clue.setAppellation(appellation);
        clue.setOwner(owner);
        clue.setCompany(company);
        clue.setJob(job);
        clue.setEmail(email);
        clue.setPhone(phone);
        clue.setWebsite(website);
        clue.setMphone(mphone);
        clue.setState(state);
        clue.setSource(source);
        clue.setDescription(description);
        clue.setContactSummary(contactSummary);
        clue.setNextContactTime(nextContactTime);
        clue.setAddress(address);
        boolean flag =clueService.saveClue(clue);
        PrintJson.printJsonFlag(response,flag);
    }

    private void getUserList(HttpServletRequest request, HttpServletResponse response) {
        UserService userService = (UserService) ServiceFactory.getService(new UserServiceImPl());
        List<User> list =userService.getUserList();
        PrintJson.printJsonObj(response,list);
    }
}
