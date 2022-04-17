package com.xxx.Module;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class InputEmployee {

    private String name;//员工姓名
    private String password;//登录密码：
    private String sex;//员工性别：
    private String birthday;//出生日期：
    private String photo;//用户照片的地址：
    private String phone;//联系电话：
    private String address;//家庭地址：
    private String zoneNumber;//负责区域：
    private String information;//员工备注：
    private String registered;//注册时间：


    public InputEmployee(){

    }

    public InputEmployee(String employeeStr) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        InputEmployee employee = new InputEmployee();

        String name = null;
        String methodName=null;
        Field[] fields=employee.getClass().getDeclaredFields();//获取该类的所有属性
        String[] ary = employeeStr.split("\\s+");
        int index = 0;

        //找到所有属性的命名，并获取其所有set方法的值
        for (Field field:fields){
            name=field.getName();
            methodName ="set"+name.substring(0,1).toUpperCase().concat(name.substring(1));//将属性首字母大写第一个
            Method method=employee.getClass().getMethod(methodName,String.class);//第二个参数必须写上set属性的类型，否则报noSuchMethodException的异常
            method.invoke(employee,ary[index++]);
        }
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZoneNumber() {
        return zoneNumber;
    }

    public void setZoneNumber(String zoneNumber) {
        this.zoneNumber = zoneNumber;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getRegistered() {
        return registered;
    }

    public void setRegistered(String registered) {
        this.registered = registered;
    }
}
