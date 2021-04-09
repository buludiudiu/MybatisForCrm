package com.bjpowernode.crm.settings.service.ImPl;

import com.bjpowernode.crm.settings.dao.DicTypeDao;
import com.bjpowernode.crm.settings.dao.DicValueDao;
import com.bjpowernode.crm.settings.domain.DicType;
import com.bjpowernode.crm.settings.domain.DicValue;
import com.bjpowernode.crm.settings.service.DicService;
import com.bjpowernode.crm.utils.SqlSessionUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DicServiceImPl implements DicService {
    private DicTypeDao dicTypeDao = SqlSessionUtil.getSession().getMapper(DicTypeDao.class);
    private DicValueDao dicValueDao = SqlSessionUtil.getSession().getMapper(DicValueDao.class);

    @Override
    public Map<String, List<DicValue>> getAll() {
        Map<String, List<DicValue>> map = new HashMap<>();
        List<DicType> list = dicTypeDao.getAllType();
        for(DicType dicType :list){
            String code = dicType.getCode();
            List<DicValue> list1 = dicValueDao.getAllValue(code);
            map.put(code,list1);
        }
        return map;
    }
}
