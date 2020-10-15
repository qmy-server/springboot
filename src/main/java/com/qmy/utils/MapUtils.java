package com.qmy.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Create by qiemengyan on 2019/4/17
 */
public class MapUtils {
    private final int code=200;
    private Map<Object,Object> map=null;

    public int getCode() {
        return code;
    }

    public Map<Object, Object> getMap() {
        return map;
    }

    public void setMap(Map<Object, Object> map) {
        this.map = map;
    }

    //正常返回成功
    public static  synchronized Object success(Object data){
            return new MapUtils().setSuccessData(data);
    }
    //错误返回状态码
    public static synchronized Object error(int code,Object data){
             return new MapUtils().setErrorData(code,data);
    }
    //正常返回方法
    public Map<Object,Object> setSuccessData(Object data){
        map=new HashMap<>();
        map.put("code",code);
        map.put("data",data);
        return map;
    }

    //错误返回方法
    public Map<Object,Object> setErrorData(int code ,Object data){
        map=new HashMap<>();
        map.put("code",code);
        map.put("data",data);
        return map;
    }

}
