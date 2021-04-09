package com.bjpowernode.crm.settings.web.controller;


import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.settings.service.ImPl.UserServiceImPl;
import com.bjpowernode.crm.settings.service.UserService;
import com.bjpowernode.crm.utils.MD5Util;
import com.bjpowernode.crm.utils.PrintJson;
import com.bjpowernode.crm.utils.ServiceFactory;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UserController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getServletPath();
        if("/setting/user/login.do".equals(path)){
           login(request,response);
        }else if("/setting/user/sava.do".equals(path)){
         //   save();
        }
    }

    private void login(HttpServletRequest request, HttpServletResponse response) {
        UserService userService = (UserService) ServiceFactory.getService(new UserServiceImPl());
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        password = MD5Util.getMD5(password);
        Map<String,Object> map = new HashMap<>();
        String addr = request.getRemoteAddr();
        try{
            User user = userService.login(username,password,addr);
            request.getSession().setAttribute("user",user);
            PrintJson.printJsonFlag(response,true);
        }catch (Exception e){
            e.printStackTrace();
            String message = e.getMessage();
            map.put("success",false);
            map.put("error",message);
            PrintJson.printJsonObj(response,map);
        }


    }


}
