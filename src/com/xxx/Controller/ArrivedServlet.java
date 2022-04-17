package com.xxx.Controller;

import com.xxx.Mapper.UserInterface;
import com.xxx.Module.Employee;
import com.xxx.Module.Time;
import com.xxx.Util.DataHandle;
import com.xxx.Util.JdbcUtil;

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
        String dbName = companyName;
        Employee employee = UserInterface.FindEmployeeByEid(dbName,eid);
        //判断该员工是否存在
        if(employee != null){
            //获取当前时间
            Date date = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
            String nowDate = dateFormat.format(date);
            String feedback = null;

            dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String today = dateFormat.format(date);

            Time arriveTime = UserInterface.FindTimesByType(dbName,"上班时间");
            Time lateTime = UserInterface.FindTimesByType(dbName,"迟到时间");
            Time leaveTime = UserInterface.FindTimesByType(dbName,"下班时间");
            //System.out.println(today);
            //获取公司上班时间
            if(nowDate.compareTo(arriveTime.getTime()) >= 0 && nowDate.compareTo(lateTime.getTime()) < 0){
                // 到达上班时间并且没有迟到，即正常签到
                /**
                 * 实现签到功能需要：
                 * 1、查找该员工今日是否已签到，否则继续，是则返回签到失败（arrive_flag=0）
                 * 2、将签到类型、当前时间、签到员工eid添加入签到数据库
                 * 3、返回正常签到(arrive_flag = 1)
                 */
                System.out.println("员工可以正常签到");
                if(UserInterface.FindArrived(dbName,eid,today).getAid() == null){
                    if(UserInterface.AddArrived(dbName,arriveTime.getTimeId(),eid)){
                        //添加成功
                        resp.setHeader("arrive_flag","1");
                        //System.out.println("签到成功");
                        feedback = String.valueOf(DataHandle.structureJSON(UserInterface.FindArrived(dbName,eid,today)));
                        w.write(feedback);
                    }else{
                        resp.setHeader("arrive_flag","0");
                        w.write("");
                    }

                }else {
                    resp.setHeader("arrive_flag","0");
                    //System.out.println("签到失败");
                    w.write("");
                }

            }else if(nowDate.compareTo(lateTime.getTime()) >= 0 && nowDate.compareTo(leaveTime.getTime()) <0){
                // 已经迟到，但没有超过下班时间
                /**
                 * 实现迟到功能：
                 * 第1、2步于正常签到一致，只是第三步返回迟到(arrive_flag = 2)
                 */
                //System.out.println("员工已迟到");
                //System.out.println(UserInterface.FindArrived(dbName,eid,today).getATime());
                if(UserInterface.FindArrived(dbName,eid,today).getAid() == null){
                    if(UserInterface.AddArrived(dbName,lateTime.getTimeId(),eid)){
                        //添加成功
                        resp.setHeader("arrive_flag","2");
                        //System.out.println("签到成功");
                        feedback = String.valueOf(DataHandle.structureJSON(UserInterface.FindArrived(dbName,eid,today)));
                        w.write(feedback);
                    }else{
                        resp.setHeader("arrive_flag","0");
                        w.write("");
                    }

                }else {
                    resp.setHeader("arrive_flag","0");
                    //System.out.println("签到失败");
                    w.write("");
                }

            }else{
                // 超过下班时间，不可以签到
                /**
                 * 直接返回签到失败(arrive_flag = 0)
                 * 一般通过app正常访问应该不会触发下班时间签到，此处只是为了防止通过非正常途径进行签到
                 */
                resp.setHeader("arrive_flag","0");
                //System.out.println("已下班,签到失败");
                w.write("");
            }


        }
        w.close();

        JdbcUtil.closeOperation();



    }
}
