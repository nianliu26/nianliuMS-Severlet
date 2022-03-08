package com.xxx.Mapper;

import com.xxx.Module.Employee;
import com.xxx.Module.Manager;
import com.xxx.Module.Times;
import com.xxx.Util.*;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class UserInterface {


/*
    //TODO 待重建
    public boolean Login(String dbName,String userName,String password){
        String sql="select name,password from employee";
        ResultSet rs= MysqlHelper.executeQuery(dbName,sql);
        try {
            while(rs.next()){
                String name=rs.getString("name");
                String pwd=rs.getString("password");
                System.out.println("name:"+name+"/password:"+pwd);
                if(name.equals(userName) && pwd.equals(password)){
                    return true;
                }
            }
            rs.close();
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    //TODO 待重建
    public void addUser(User u){
        String sql="insert into tb_user (userName,userPWD) values (?,?)";//构建sql语句模板
        MysqlHelper.executeUpdate(sql,u.getUserName(),MD5Helper.GetMD5Code(u.getUserPWD()));
    }

    //TODO 待重建
    public void deleteUser(String dbName,int i){
        String sql="delete from tb_user where id=?";
        MysqlHelper.executeUpdate(dbName,sql,i);
    }

    //TODO 待重建
    public void modifyUser(String dbName,User u){
        String sql="update tb_user set id=?,userName=? where id=?";
        MysqlHelper.executeUpdate(dbName,sql,u.getUserID(),u.getUserName());
    }

*/


    public static Times FindTimes(String dbName){
        Times times = new Times();
        String sql = "select time from times";
        ResultSet rs = MysqlHelper.executeQuery(dbName,sql);
        try {
            if(rs.next()){
                String name = null;
                String methodName=null;
                Field[] fields=times.getClass().getDeclaredFields();//获取该类的所有属性

                //找到所有属性的命名，并获取其所有set方法的值
                for (Field field:fields){
                    name=field.getName();
                    methodName ="set"+name.substring(0,1).toUpperCase().concat(name.substring(1));//将属性首字母大写第一个
                    Method method=times.getClass().getMethod(methodName,String.class);//第二个参数必须写上set属性的类型，否则报noSuchMethodException的异常
                    method.invoke(times,rs.getString(name));
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return times;
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


    //TODO 待重建
    /*public List QueryUsers(String dbName){
        List<User> list=new ArrayList<User>();
        String sql="select * from tb_user";
        ResultSet rs=MysqlHelper.executeQuery(dbName,sql);
        try {
            while(rs.next()){
                //保存取出来的每一条记录
                User u=new User();
                u.setUserID(rs.getInt("userID"));
                u.setUserName(rs.getString("userName"));
                list.add(u);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }

        return list;
    }*/
}
