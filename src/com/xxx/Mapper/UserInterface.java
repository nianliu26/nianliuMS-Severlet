package com.xxx.Mapper;

import com.xxx.Module.Arrive;
import com.xxx.Module.Employee;
import com.xxx.Module.Manager;
import com.xxx.Module.Time;
import com.xxx.Util.*;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.ResultSet;

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
