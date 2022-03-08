package com.xxx.Controller;

import com.xxx.Mapper.UserInterface;
import com.xxx.Module.Employee;
import com.xxx.Module.Manager;
import com.xxx.Module.Times;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * 用户签到网络接口：
 * 访问方式：GET
 * 业务逻辑：
 *  1、接收用户传入的参数
 *  2、根据传入的公司名访问对应的数据库
 *  3、获取当前系统时间
 *  4、计算签到时间与上班时间或者迟到时间的差别，判断签到类型，即是否迟到
 *  5、根据员工id以及签到类型添加签到数据
 *  6、将签到类型发送回客户端
 */
public class ArrivedServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String companyId = req.getParameter("companyId");
        String eid = req.getParameter("eid");

        //访问成功
        resp.setStatus(200);
        resp.setContentType("text/json;charset=utf-8");
        PrintWriter w = resp.getWriter();

        String companyName = UserInterface.FindCompany(companyId);
        Employee employee = UserInterface.FindEmployeeByEid(companyName,eid);
        if(employee != null){
            //获取当前时间
            Date date = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");
            String nowDate = dateFormat.format(date);

            //获取公司上班时间
            Times times = UserInterface.FindTimes(companyName);
            if(nowDate.compareTo(times.getArrivedTime()) >= 0 && nowDate.compareTo(times.getLateTime()) < 0){
                //TODO 到达上班时间并且没有迟到

            }else if(nowDate.compareTo(times.getLateTime()) >= 0 && nowDate.compareTo(times.getLeaveTime()) <0){
                //TODO 已经迟到，但没有超过下班时间

            }else if (nowDate.compareTo(times.getLeaveTime()) >= 0){
                //TODO 超过下班时间，不可以签到
            }


        }





    }
}
