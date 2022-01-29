package com.xxx.Mapper;

import com.xxx.Module.User;
import com.xxx.Util.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserInterface {


    public boolean Login(String userName,String password){
        String sql="select name,password from employee";
        ResultSet rs= MysqlHelper.executeQuery(sql);
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

    public void addUser(User u){
        String sql="insert into tb_user (userName,userPWD) values (?,?)";//构建sql语句模板
        MysqlHelper.executeUpdate(sql,u.getUserName(),MD5Helper.GetMD5Code(u.getUserPWD()));
    }

    public void deleteUser(int i){
        String sql="delete from tb_user where id=?";
        MysqlHelper.executeUpdate(sql,i);
    }

    public void modifyUser(User u){
        String sql="update tb_user set id=?,userName=? where id=?";
        MysqlHelper.executeUpdate(sql,u.getUserID(),u.getUserName());
    }




    public User QueryUserByUserName(String userName){
        User u=new User();
        String sql="select * from tb_user where userName=?";
        ResultSet rs=MysqlHelper.executeQuery(sql,userName);
        try {
            if(rs.next()){
                u.setUserID(Integer.parseInt(rs.getObject("userID").toString()));
                u.setUserName(rs.getString("userName"));
            }
        } catch (NumberFormatException | SQLException e){
            e.printStackTrace();
        }
        return u;
    }


    public List QueryUsers(){
        List<User> list=new ArrayList<User>();
        String sql="select * from tb_user";
        ResultSet rs=MysqlHelper.executeQuery(sql);
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
    }
}
