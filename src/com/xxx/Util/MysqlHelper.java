package com.xxx.Util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MysqlHelper {

    /**
     * 执行增删改 用可变参数，来接受传递过来的参数，参数的个数可以不确定
     * @param sql:sql语句模板参数用？占位
     * @param objects：具体参数
     * @return 返回执行命令的次数
     */
    public static int executeUpdate(String sql,Object...objects){
        int len=0;
        PreparedStatement psta=JdbcUtil.createPreparedStatement(sql);
        try {
            for(int i=0;i< objects.length;i++){
                psta.setObject(i+1,objects[i]);
            }
            len=psta.executeUpdate();
            return len;
        } catch (SQLException e){
            e.printStackTrace();
        }

        return len;
    }

    /**
     * 执行查询
     * @param sql:sql语句模板
     * @param objects:sql语句参数具体值
     * @return 返回查询到的内容
     */
    public static ResultSet executeQuery(String sql,Object...objects){
        ResultSet rs=null;
        PreparedStatement psta=JdbcUtil.createPreparedStatement(sql);
        try {
            for (int i=0;i<objects.length;i++){
                psta.setObject(i+1,objects[i]);
            }
            rs= psta.executeQuery();
            return rs;
        } catch (SQLException e){
            e.printStackTrace();
        }finally {
            //JdbcUtil.closeOperation();
        }
        return rs;
    }
}
