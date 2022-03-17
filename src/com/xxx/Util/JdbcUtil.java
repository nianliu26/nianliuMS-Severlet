package com.xxx.Util;

import java.sql.*;

public class JdbcUtil {
    //先设置驱动器和数据库地址
    //private static String DBDriver="com.mysql.jdbc.Driver";
    private static String DBDriver="com.mysql.cj.jdbc.Driver";
    //private static String url="jdbc:mysql://localhost:3306/javawebtest?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC";
    private static String url = "jdbc:mysql://localhost:3306/";
    private static String encode="?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC";
    //设置用户名
    private static String user="root";
    private static String pwd="password";
    //设置连接
    static Connection con=null;
    static Statement sta=null;
    static PreparedStatement psta=null;

    /**
     * 连接数据库
     * @return 返回值为一个数据库的连接对象
     */
    public static Connection getCon(String dbName){
        String u1=url+dbName+encode;
        try {
            Class.forName(DBDriver);//注册驱动
            try {
                con= DriverManager.getConnection(u1,user,pwd);//获取连接
                return con;
            } catch (SQLException e){
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e1){
            e1.printStackTrace();
        }
        return null;
    }

    /**
     * 获取一个statement对象，用于操作数据库
     * @return 一个statement对象
     */
    public static Statement createStatement(String dbName){
        try {
            sta=getCon(dbName).createStatement();
            return sta;
        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 创造数据预处理对象
     * @param sql:需要执行的sql语句
     * @param dbName:需要连接的数据库名
     * @return 返回一个数据预处理对象
     */
    public static PreparedStatement createPreparedStatement(String dbName,String sql){
        try {
            psta=getCon(dbName).prepareStatement(sql);
            return psta;
        } catch (SQLException e){
            e.printStackTrace();
        }

        return psta;
    }


    /**
     * 关闭打开的所有资源
     */
    public static void closeOperation(){
        if (psta==null){
            try {
                psta.close();
            } catch (SQLException e){
                e.printStackTrace();
            }
        }
        if (sta==null){
            try {
                sta.close();
            } catch (SQLException e){
                e.printStackTrace();
            }
        }
        if (con==null){
            try {
                con.close();
            } catch (SQLException e){
                e.printStackTrace();
            }
        }
    }
}
