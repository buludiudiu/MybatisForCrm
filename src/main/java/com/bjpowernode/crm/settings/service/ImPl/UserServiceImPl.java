package com.bjpowernode.crm.settings.service.ImPl;

import com.bjpowernode.crm.exception.LoginException;
import com.bjpowernode.crm.settings.dao.UserDao;
import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.settings.service.UserService;
import com.bjpowernode.crm.utils.DateTimeUtil;
import com.bjpowernode.crm.utils.SqlSessionUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class UserServiceImPl implements UserService {
    private UserDao userDao = SqlSessionUtil.getSession().getMapper(UserDao.class);


    @Override
    public User login(String username, String password, String addr) throws LoginException {
        Map<String,String> map = new HashMap<>();
        map.put("username",username);
        map.put("password",password);
        User user = userDao.login(map);
        String sysTime = DateTimeUtil.getSysTime();
        if(user==null){
            throw new LoginException("对不起，账号或密码错误");
        }else if ("0".equals(user.getLockState())) {
            throw new LoginException("对不起，账号已锁定");
        }else if (sysTime.compareTo( user.getExpireTime())>0){
            throw new LoginException("对不起，账号已失效");
        } else if (!user.getAllowIps().contains(addr)) {
            throw new LoginException("你的ip地址不能访问");
        }
        return user;
    }

    @Override
    public List<User> getUserList() {
        List<User> list= userDao.getUserList();
        return list;
    }
}
