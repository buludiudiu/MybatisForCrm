package com.bjpowernode.crm.web.listener;

import com.bjpowernode.crm.settings.domain.DicValue;
import com.bjpowernode.crm.settings.service.DicService;
import com.bjpowernode.crm.settings.service.ImPl.DicServiceImPl;
import com.bjpowernode.crm.utils.ServiceFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.*;

public class SysInitListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("上下文域对象创建了");
        ServletContext application = sce.getServletContext();
        DicService service =(DicService) ServiceFactory.getService(new DicServiceImPl());
        Map<String, List<DicValue>> map = service.getAll();
        Set<String> set = map.keySet();
        for(String s : set){
            List<DicValue> list = map.get(s);
            application.setAttribute(s,list);
        }
        Map<String,String> pMap = new HashMap<>();
        ResourceBundle rb = ResourceBundle.getBundle("Stage2Possibility");
        Enumeration<String> keys = rb.getKeys();
        while (keys.hasMoreElements()){
            String key = keys.nextElement();
            pMap.put(key,rb.getString(key));
        }
        application.setAttribute("pMap",pMap);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
