package com.xxx.Controller;

import com.xxx.Mapper.UserInterface;
import com.xxx.Module.Manager;
import com.xxx.Util.JdbcUtil;
import com.xxx.Util.MysqlHelper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class UpdataEmployeeInformation extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String mid = req.getParameter("mid");

        String name = req.getParameter("name");
        String number = req.getParameter("number");
        String password = req.getParameter("password");
        String sex = req.getParameter("sex");
        String birthday = req.getParameter("birthday");
        String phone = req.getParameter("phone");
        String address = req.getParameter("address");
        String zone = req.getParameter("zone");
        String register = req.getParameter("register");
        String information = req.getParameter("information");

        //访问成功
        resp.setStatus(200);
        resp.setContentType("text/json;charset=utf-8");
        PrintWriter w = resp.getWriter();
        String feedback = "";

        Manager manager = UserInterface.FindManager(mid);
        String dbName = UserInterface.FindCompany(manager.getCompanyId());
        String zoneId = UserInterface.FindZoneIdByName(dbName,zone);
        if(manager != null && zoneId != null){
            String sql = "UPDATE employee SET name=?,password=?,sex=?,birthday=?,phone=?,address=?,zoneNumber=?,information=?,registered=? where eid = ?";
            if(MysqlHelper.executeUpdate(dbName,sql,name,password,sex,birthday,phone,address,zoneId,information,register,number) != 0){
                feedback = "1";
            }
        }
        w.write(feedback);
        w.close();
        JdbcUtil.closeOperation();

    }
}
