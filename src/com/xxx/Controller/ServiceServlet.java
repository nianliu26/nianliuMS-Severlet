package com.xxx.Controller;

import com.xxx.Mapper.UserInterface;
import com.xxx.Module.Arrive;
import com.xxx.Module.Employee;
import com.xxx.Module.Time;
import com.xxx.Module.Zone;
import com.xxx.Util.DataHandle;
import com.xxx.Util.JdbcUtil;
import net.sf.json.JSON;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * 1、判断当前可否签到
 * 2、判断当前可否签到
 * 3、判断今天是否已签到
 */
public class ServiceServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String companyId = req.getParameter("companyId");
        String eid = req.getParameter("eid");
        String password = req.getParameter("password");

        String dbName = UserInterface.FindCompany(companyId);
        Employee employee = UserInterface.FindEmployeeByEid(dbName,eid);
        Zone zone = UserInterface.FindZone(dbName,employee.getZoneNumber());


        //访问成功
        resp.setStatus(200);
        resp.setContentType("text/json;charset=utf-8");
        PrintWriter w = resp.getWriter();

        String feedback;

        if((employee.getEid()!=null) && employee.getPassword().equals(password) && zone.getZoneId()!= null){
            JSONObject data = new JSONObject();

            //构建用户信息数据
            JSONObject employeeData = DataHandle.structureJSON(employee);
            data.put("employee",String.valueOf(employeeData));

            JSONObject zoneData = DataHandle.structureJSON(zone);
            data.put("zone",String.valueOf(zoneData));


            Date date = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
            String now = dateFormat.format(date);
            dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String today = dateFormat.format(date);

            Arrive arrive = UserInterface.FindArrived(dbName,eid,today);
            if(arrive.getAid() == null){
                data.put("arrive",null);
                data.put("arrive_flag",0);
            }
            else{
                data.put("arrive",String.valueOf(DataHandle.structureJSON(arrive)));
                data.put("arrive_flag",Integer.valueOf(arrive.getAType()));
            }

            Time arriveTime = UserInterface.FindTimesByType(dbName,"上班时间");
            Time leaveTime = UserInterface.FindTimesByType(dbName,"下班时间");

            if(now.compareTo(arriveTime.getTime())<0){
                data.put("allow_arrive",false);
                data.put("allow_leave",false);
            }else if(now.compareTo(arriveTime.getTime())>=0 && now.compareTo(leaveTime.getTime())<0){
                data.put("allow_arrive",true);
                data.put("allow_leave",false);
            }else if(now.compareTo(leaveTime.getTime())>=0){
                data.put("allow_arrive",false);
                data.put("allow_leave",true);
            }

            feedback = String.valueOf(data);

            //System.out.println("service"+feedback);
        }else{
            feedback = "";
        }

        w.write(feedback);
        w.close();
        JdbcUtil.closeOperation();
    }
}
