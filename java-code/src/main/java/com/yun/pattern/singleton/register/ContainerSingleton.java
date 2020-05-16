package com.yun.pattern.singleton.register;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ContainerSingleton {
    private static Map instanceMaps = new ConcurrentHashMap<String,Object>();

    public static Object getInstance(String clazzName){
        if(!instanceMaps.containsKey(clazzName)) {
            synchronized(ContainerSingleton.class){
                try {
                    Class<?> clazz = Class.forName(clazzName);
                    Object o = clazz.newInstance();
        

                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
        return instanceMaps.get(clazzName);
    }
}
