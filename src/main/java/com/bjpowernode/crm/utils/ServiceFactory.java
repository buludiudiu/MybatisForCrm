package com.bjpowernode.crm.utils;

public class ServiceFactory {
    public static Object getService(Object service){
        return new TranscationInvocationHandle(service).getProxy();
    }
}
