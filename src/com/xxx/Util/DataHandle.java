package com.xxx.Util;

import net.sf.json.JSONObject;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class DataHandle {


    //构建一个json格式的字符串用于发送

    /**
     * 通过反射获取对象的所有属性以及get方法
     * 再将获取到的数据一一put到JSONObject中
     * @param object 一个实体对象
     * @return 返回一个JSONObject对象
     */
    public JSONObject structureJSON(Object object){

        JSONObject jsonObject=new JSONObject();

        String name=null;
        String methodName=null;

        Field[] fields=object.getClass().getDeclaredFields();//获取该类的所有属性
        //找到所有属性的命名，并获取其所有get方法的值，再全部放入jsonObject中
        try {
            for (Field field:fields){
                name=field.getName();
                methodName ="get"+name.substring(0,1).toUpperCase().concat(name.substring(1));//将属性首字母大写第一个

                Method method=object.getClass().getMethod(methodName);
                jsonObject.put(name,method.invoke(object));//构造json对象

            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        System.out.println("封装好的数据："+jsonObject);

        return jsonObject;
    }


}
