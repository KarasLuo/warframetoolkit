package com.karas.wftoolkit.Mail;

import java.util.HashMap;

/**
 * Created by Hongliang Luo on 2019/8/7.
 **/
public final class EmailConstants {
    public static HashMap<String,String>constant=new HashMap<>();

    public static String getValue(String key){
        return constant.get(key);
    }

    public static void init(){
        constant.put("163.com","25");
        constant.put("126.com","25");
        constant.put("qq.com","25");
        constant.put("outlook.com","587");
    }
}
