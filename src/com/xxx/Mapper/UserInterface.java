package com.xxx.Mapper;

import com.xxx.Module.*;
import com.xxx.Util.*;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UserInterface {


    public static boolean sendResponse(String dbName,String messageId,String responseContent){
        String sql = "UPDATE message SET mresponse=?,mrtime=now() where messageid=?";
        int len = MysqlHelper.executeUpdate(dbName,sql,responseContent,messageId);
        if(len != 0) return true;
        return false;
    }

    /**
     * 修改员工密码
     * @param dbName 数据库名
     * @param eid 员工编号
     * @param newPassword 新的密码
     * @return
     */
    public static boolean updateEmployeePassword(String dbName,String eid,String newPassword){
        String sql = "UPDATE employee SET password = ? where eid = ?";
        int len = MysqlHelper.executeUpdate(dbName,sql,newPassword,eid);

        if (len != 0) return true;
        return false;
    }

    /**
     * 修改员工头像
     * @param dbName 数据库名
     * @param eid 员工编号
     * @param imagePath 图片地址
     * @return 返回是否修改成功
     */
    public static boolean updateEmployeePhotoByEid(String dbName,String eid,String imagePath){
        String sql = "UPDATE employee SET photo = ? where eid = ?";
        int len = MysqlHelper.executeUpdate(dbName,sql,imagePath,eid);

        if(len != 0) return  true;
        return false;
    }


    public static boolean AddEmployee(String dbName,String employeeStr){
        String[] ary = employeeStr.split("\\|");
        for(String a:ary) System.out.println(a);

        if(ary.length != 7) return false;

        String sql = "INSERT INTO employee(name,password,sex,birthday,photo,phone,address,zoneNumber,information,registered)"+
                "VALUES (?,'12345678',?,?,'img/111.jpg',?,?,?,?,now())";

        int len = MysqlHelper.executeUpdate(dbName,sql,ary[0],ary[1],ary[2],ary[3],ary[4],ary[5],ary[6]);
        if(len != 0) return true;
        return false;
    }

    /**
     * 添加签到信息
     * @param dbName 数据库/公司名
     * @param aType 签到类型
     * @param eid 员工id
     * @return 返回是否执行成功
     */
    public static boolean AddArrived(String dbName,String aType,String eid){
        String sql = "INSERT INTO arrive(aType,aTime,eid) VALUES(?,NOW(),?);";
        int len = MysqlHelper.executeUpdate(dbName,sql,aType,eid);

        if (len != 0) return true;
        return false;
    }

    public static boolean AddMessage(String dbName,String eid,String title,String content){
        String sql = "INSERT INTO message(mtitle,mbody,meid,mtime) VALUES(?,?,?,NOW());";
        int len = MysqlHelper.executeUpdate(dbName,sql,title,content,eid);
        if(len != 0) return true;
        return false;
    }

    public static String FindZoneIdByName(String dbName,String zoneName){
        String sql = "select zoneId FROM zone where zoneName=?";
        ResultSet rs = MysqlHelper.executeQuery(dbName,sql,zoneName);
        if(rs == null) return  null;
        String zoneId="";
        try {
            if(rs.next()) zoneId = rs.getString("zoneId");
        }catch (Exception e){
            System.out.println(e.toString());
        }
        return zoneId;
    }


    public static Zone FindZone(String dbName,String zid){
        Zone zone = new Zone();
        String sql = "select * from zone where zoneId = ?";
        ResultSet rs = MysqlHelper.executeQuery(dbName,sql,zid);
        if(rs == null) return null;
        try {
            if(rs.next()){
                String name = null;
                String methodName=null;
                Field[] fields=zone.getClass().getDeclaredFields();//获取该类的所有属性

                //找到所有属性的命名，并获取其所有set方法的值
                for (Field field:fields){
                    name=field.getName();
                    methodName ="set"+name.substring(0,1).toUpperCase().concat(name.substring(1));//将属性首字母大写第一个
                    Method method=zone.getClass().getMethod(methodName,String.class);//第二个参数必须写上set属性的类型，否则报noSuchMethodException的异常
                    method.invoke(zone,rs.getString(name));
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        return zone;
    }

    public static List<EmployeeAndZone> FindAllEmployee(String dbName){
        List<EmployeeAndZone> employeeAndZones = new ArrayList<>();
        String sql = "SELECT eid,name,password,sex,birthday,phone,address,information,registered,zoneName FROM employee,zone " +
                "WHERE employee.zoneNumber = zone.zoneId;";
        ResultSet rs = MysqlHelper.executeQuery(dbName,sql);
        if(rs == null) return null;
        try {
            while (rs.next()){
                EmployeeAndZone employeeAndZone = new EmployeeAndZone();
                String name = null;
                String methodName=null;
                Field[] fields=employeeAndZone.getClass().getDeclaredFields();//获取该类的所有属性

                //找到所有属性的命名，并获取其所有set方法的值
                for (Field field:fields){
                    name=field.getName();
                    methodName ="set"+name.substring(0,1).toUpperCase().concat(name.substring(1));//将属性首字母大写第一个
                    Method method=employeeAndZone.getClass().getMethod(methodName,String.class);//第二个参数必须写上set属性的类型，否则报noSuchMethodException的异常
                    method.invoke(employeeAndZone,rs.getString(name));
                }
                employeeAndZones.add(employeeAndZone);
            }

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

        return employeeAndZones;
    }

    public static List<Message> FindMessage(String dbName){
        List<Message> messages = new ArrayList<>();
        String sql = "select * from message ORDER BY mtime DESC,mrtime DESC";
        ResultSet rs = MysqlHelper.executeQuery(dbName,sql);
        if(rs == null) return null;
        try {
            while (rs.next()){
                Message message = new Message();
                String name = null;
                String methodName=null;
                Field[] fields=message.getClass().getDeclaredFields();//获取该类的所有属性

                //找到所有属性的命名，并获取其所有set方法的值
                for (Field field:fields){
                    name=field.getName();
                    methodName ="set"+name.substring(0,1).toUpperCase().concat(name.substring(1));//将属性首字母大写第一个
                    Method method=message.getClass().getMethod(methodName,String.class);//第二个参数必须写上set属性的类型，否则报noSuchMethodException的异常
                    method.invoke(message,rs.getString(name));
                }
                messages.add(message);
            }

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }


        return messages;
    }

    public static List<Message> FindMessage(String dbName, String eid){
        List<Message> messages = new ArrayList<>();
        String sql = "select * from message where meid = ? ORDER BY mtime DESC,mrtime DESC";
        ResultSet rs = MysqlHelper.executeQuery(dbName,sql,eid);
        if(rs == null) return null;
        try {
            while (rs.next()){
                Message message = new Message();
                String name = null;
                String methodName=null;
                Field[] fields=message.getClass().getDeclaredFields();//获取该类的所有属性

                //找到所有属性的命名，并获取其所有set方法的值
                for (Field field:fields){
                    name=field.getName();
                    methodName ="set"+name.substring(0,1).toUpperCase().concat(name.substring(1));//将属性首字母大写第一个
                    Method method=message.getClass().getMethod(methodName,String.class);//第二个参数必须写上set属性的类型，否则报noSuchMethodException的异常
                    method.invoke(message,rs.getString(name));
                }
                messages.add(message);
            }

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }


        return messages;
    }


    public static Arrive FindArrived(String dbName,String eid,String day){
        Arrive arrive = new Arrive();
        day = day+"%";
        String sql = "select * from arrive where eid=? AND aTime LIKE ?";
        ResultSet rs = MysqlHelper.executeQuery(dbName,sql,eid,day);
        if(rs == null) return null;
        try {
            if(rs.next()){
                String name = null;
                String methodName=null;
                Field[] fields=arrive.getClass().getDeclaredFields();//获取该类的所有属性

                //找到所有属性的命名，并获取其所有set方法的值
                for (Field field:fields){
                    name=field.getName();
                    methodName ="set"+name.substring(0,1).toUpperCase().concat(name.substring(1));//将属性首字母大写第一个
                    Method method=arrive.getClass().getMethod(methodName,String.class);//第二个参数必须写上set属性的类型，否则报noSuchMethodException的异常
                    method.invoke(arrive,rs.getString(name));
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

        return arrive;

    }




    /**
     * 根据签到类型名查找签到类型
     * @param dbName 数据库/公司名
     * @param timeType 签到类型
     * @return
     */
    public static Time FindTimesByType(String dbName, String timeType){
        Time time = new Time();
        String sql = "select * from times where Trim(timeType)=?";
        ResultSet rs = MysqlHelper.executeQuery(dbName,sql,timeType);
        try {
            if(rs.next()){
                String name = null;
                String methodName=null;
                Field[] fields= time.getClass().getDeclaredFields();//获取该类的所有属性

                //找到所有属性的命名，并获取其所有set方法的值
                for (Field field:fields){
                    name=field.getName();
                    methodName ="set"+name.substring(0,1).toUpperCase().concat(name.substring(1));//将属性首字母大写第一个
                    Method method= time.getClass().getMethod(methodName,String.class);//第二个参数必须写上set属性的类型，否则报noSuchMethodException的异常
                    method.invoke(time,rs.getString(name));
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return time;
    }



    //重建完成
    /**
     * 在指定数据库查找指定用户数据，通过反射将查找到的数据保存为一个Employee对象
     * @param dbName:数据库名
     * @param eid:用户的id
     * @return 返回值为一个Employee对象
     */
    public static Employee FindEmployeeByEid(String dbName,String eid){
        Employee u=new Employee();
        String sql="select * from employee where eid=?";
        ResultSet rs=MysqlHelper.executeQuery(dbName,sql,eid);
        try {
            if(rs.next()){

                String name = null;
                String methodName=null;
                Field[] fields=u.getClass().getDeclaredFields();//获取该类的所有属性

                //找到所有属性的命名，并获取其所有set方法的值
                for (Field field:fields){
                    name=field.getName();
                    methodName ="set"+name.substring(0,1).toUpperCase().concat(name.substring(1));//将属性首字母大写第一个
                    Method method=u.getClass().getMethod(methodName,String.class);//第二个参数必须写上set属性的类型，否则报noSuchMethodException的异常
                    method.invoke(u,rs.getString(name));
                }

            }
        } catch (Exception  e){
            e.printStackTrace();
        }
        return u;
    }


    /**
     * 查询管理员信息
     * @param mid：管理员账号
     * @return 返回一个管理员实体
     */
    public static Manager FindManager(String mid){
        Manager m = new Manager();
        String sql = "select * from companies where companyId=?";
        String dbName = "company";
        ResultSet rs = MysqlHelper.executeQuery(dbName,sql,mid);
        try {
            if (rs.next()){
                String name = null;
                String methodName=null;
                Field[] fields=m.getClass().getDeclaredFields();//获取该类的所有属性

                //找到所有属性的命名，并获取其所有set方法的值
                for (Field field:fields){
                    name=field.getName();
                    methodName ="set"+name.substring(0,1).toUpperCase().concat(name.substring(1));//将属性首字母大写第一个
                    Method method=m.getClass().getMethod(methodName,String.class);//第二个参数必须写上set属性的类型，否则报noSuchMethodException的异常
                    method.invoke(m,rs.getString(name));
                }
            }
        } catch (Exception throwables) {
            throwables.printStackTrace();
        }
        return m;

    }


    /**
     * 根据公司编号查询公司是否存在
     * @param companyId：公司编号
     * @return 返回公司名称
     */
    public static String FindCompany(String companyId){
        String sql = "select * from companies where companyId=?";
        String dbName="company";
        ResultSet rs = MysqlHelper.executeQuery(dbName,sql,companyId);
        try {
            String companyName=null;
            if (rs.next()){
                //System.out.println(rs.getInt("companyId"));
                companyName=rs.getString("cname");
            }else System.out.println("目标数据为空");
            return companyName;
        }catch (Exception e){
            System.out.println("查询失败");
            e.printStackTrace();
            return null;
        }
    }

}
