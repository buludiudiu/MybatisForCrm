package com.bjpowernode.crm.workbench.web.controller;

import com.bjpowernode.crm.settings.dao.UserDao;
import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.settings.service.ImPl.UserServiceImPl;
import com.bjpowernode.crm.settings.service.UserService;
import com.bjpowernode.crm.utils.DateTimeUtil;
import com.bjpowernode.crm.utils.PrintJson;
import com.bjpowernode.crm.utils.ServiceFactory;
import com.bjpowernode.crm.utils.UUIDUtil;
import com.bjpowernode.crm.vo.PaginationVO;
import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.domain.ActivityRemark;
import com.bjpowernode.crm.workbench.service.ActivityService;
import com.bjpowernode.crm.workbench.service.ImPl.ActivityServiceImPl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ActivityController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getServletPath();
        if("/workbench/Activity/getUserList.do".equals(path)){
            getUserList(request,response);
        }else if("/workbench/Activity/saveActivity.do".equals(path)){
            saveActivity(request,response);
        }else if("/workbench/Activity/pageList.do".equals(path)){
            pageList(request,response);
        }else if("/workbench/Activity/delete.do".equals(path)){
            deleteActivity(request,response);
        }else if("/workbench/Activity/getUserListandActivityById.do".equals(path)){
            getUserListandActivityById(request,response);
        }else if("/workbench/Activity/update.do".equals(path)){
            update(request,response);
        }else if("/workbench/Activity/detail.do".equals(path)){
            detail(request,response);
        }else if("/workbench/Activity/getRemarkList.do".equals(path)){
            getRemarkList(request,response);
        }else if("/workbench/Activity/deleteById.do".equals(path)){
            deleteById(request,response);
        }else if("/workbench/Activity/deleteRemark.do".equals(path)){
            deleteRemark(request,response);
        }else if("/workbench/Activity/saveRemark.do".equals(path)){
            saveRemark(request,response);
        }else if("/workbench/Activity/updateRemark.do".equals(path)){
            updateRemark(request,response);
        }

    }

    private void updateRemark(HttpServletRequest request, HttpServletResponse response) {
        ActivityService service =(ActivityService) ServiceFactory.getService(new ActivityServiceImPl());
        String id = request.getParameter("id");
        String noteContent = request.getParameter("noteContent");
        String editTime = DateTimeUtil.getSysTime();
        User user =(User) request.getSession().getAttribute("user");
        String editBy = user.getName();
        String editFlag = "1";
        ActivityRemark activityRemark = new ActivityRemark();
        activityRemark.setId(id);
        activityRemark.setNoteContent(noteContent);
        activityRemark.setEditTime(editTime);
        activityRemark.setEditBy(editBy);
        activityRemark.setEditFlag(editFlag);
        boolean flag =service.updateRemark(activityRemark);
        PrintJson.printJsonFlag(response,flag);
    }

    private void saveRemark(HttpServletRequest request, HttpServletResponse response) {
        ActivityService service =(ActivityService) ServiceFactory.getService(new ActivityServiceImPl());
        String id = UUIDUtil.getUUID();
        String noteContent = request.getParameter("noteContent");
        String createTime = DateTimeUtil.getSysTime();
        User user =(User) request.getSession().getAttribute("user");
        String createBy = user.getName();
        String editFlag = "0";
        String activityId = request.getParameter("activityId");
        ActivityRemark activityRemark = new ActivityRemark();
        activityRemark.setId(id);
        activityRemark.setNoteContent(noteContent);
        activityRemark.setCreateTime(createTime);
        activityRemark.setCreateBy(createBy);
        activityRemark.setEditFlag(editFlag);
        activityRemark.setActivityId(activityId);
        boolean flag =service.saveRemark(activityRemark);
        PrintJson.printJsonFlag(response,flag);
    }

    private void deleteRemark(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        ActivityService service =(ActivityService) ServiceFactory.getService(new ActivityServiceImPl());
        boolean flag = service.deleteRemark(id);
        PrintJson.printJsonFlag(response,flag);
    }

    private void deleteById(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ActivityService service =(ActivityService) ServiceFactory.getService(new ActivityServiceImPl());
        String id = request.getParameter("id");
        service.deleteById(id);
        response.sendRedirect("/crm/workbench/activity/index.jsp");
    }

    private void getRemarkList(HttpServletRequest request, HttpServletResponse response) {
        ActivityService service =(ActivityService) ServiceFactory.getService(new ActivityServiceImPl());
        String id = request.getParameter("id");
        List<ActivityRemark> list = service.getRemarkList(id);
        PrintJson.printJsonObj(response,list);
    }

    private void detail(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException{
        ActivityService service =(ActivityService) ServiceFactory.getService(new ActivityServiceImPl());
        String id = request.getParameter("id");
        Activity activity = service.detail(id);
        System.out.println(activity.getName());
        request.setAttribute("activity",activity);
        request.getRequestDispatcher("/workbench/activity/detail.jsp").forward(request,response);
    }

    private void update(HttpServletRequest request, HttpServletResponse response) {
        ActivityService service =(ActivityService) ServiceFactory.getService(new ActivityServiceImPl());
        String id = request.getParameter("id");
        String owner = request.getParameter("owner");
        String name = request.getParameter("name");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String cost = request.getParameter("cost");
        String description = request.getParameter("description");
        String editTime = DateTimeUtil.getSysTime();
        User user =(User) request.getSession().getAttribute("user");
        String editBy = user.getName();
        boolean flag = service.update(id,owner,name,startDate,endDate,cost,description,editTime,editBy);
        PrintJson.printJsonFlag(response,flag);
    }

    private void getUserListandActivityById(HttpServletRequest request, HttpServletResponse response) {
        ActivityService service =(ActivityService) ServiceFactory.getService(new ActivityServiceImPl());
        String id = request.getParameter("id");
        Map<String,Object> map = service.getUserListandActivityById(id);
        PrintJson.printJsonObj(response,map);
    }

    private void deleteActivity(HttpServletRequest request, HttpServletResponse response) {
        ActivityService service =(ActivityService) ServiceFactory.getService(new ActivityServiceImPl());
        String[] activityids = request.getParameterValues("activityid");
        boolean flag =service.deleteActivity(activityids);
        PrintJson.printJsonFlag(response,flag);
    }

    private void pageList(HttpServletRequest request, HttpServletResponse response) {
        ActivityService service =(ActivityService) ServiceFactory.getService(new ActivityServiceImPl());
        String pageNoStr = request.getParameter("pageNo");
        String pageSizeStr = request.getParameter("pageSize");
        int pageNo = Integer.valueOf(pageNoStr);
        int pageSize = Integer.valueOf(pageSizeStr);
        int skipCount = (pageNo-1)*pageSize;
        String name = request.getParameter("search-name");
        String owner = request.getParameter("search-owner");
        String startDate = request.getParameter("search-startDate");
        String endDate = request.getParameter("search-endDate");
        Map<String,Object> map = new HashMap<>();
        map.put("pageSize",pageSize);
        map.put("skipCount",skipCount);
        map.put("name",name);
        map.put("owner",owner);
        map.put("startDate",startDate);
        map.put("endDate",endDate);
        PaginationVO<Activity> vo = service.pageList(map);
        List<Activity> list = vo.getList();
        System.out.println(list);
        PrintJson.printJsonObj(response,vo);
    }

    private void saveActivity(HttpServletRequest request, HttpServletResponse response) {
        ActivityService service =(ActivityService) ServiceFactory.getService(new ActivityServiceImPl());
        String id = UUIDUtil.getUUID();
        String owner = request.getParameter("owner");
        String name = request.getParameter("name");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String cost = request.getParameter("cost");
        String description = request.getParameter("description");
        String createTime = DateTimeUtil.getSysTime();
        User user =(User) request.getSession().getAttribute("user");
        String createBy = user.getName();
        service.saveActivity(id,owner,name,startDate,endDate,cost,description,createTime,createBy);
        PrintJson.printJsonFlag(response,true);
    }

    private void getUserList(HttpServletRequest request, HttpServletResponse response) {
        UserService service =(UserService) ServiceFactory.getService(new UserServiceImPl());
        List<User> list = service.getUserList();
        PrintJson.printJsonObj(response,list);
    }
}
