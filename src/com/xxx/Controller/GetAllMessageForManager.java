package com.xxx.Controller;

import com.xxx.Mapper.UserInterface;
import com.xxx.Module.EmployeeAndZone;
import com.xxx.Module.Manager;
import com.xxx.Module.Message;
import com.xxx.Util.DataHandle;
import com.xxx.Util.JdbcUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class GetAllMessageForManager extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //访问成功
        resp.setStatus(200);
        resp.setContentType("text/json;charset=utf-8");
        PrintWriter w = resp.getWriter();
        String feedback = "";

        String companyId = req.getParameter("companyId");
        Manager manager = UserInterface.FindManager(companyId);
        String dbName = UserInterface.FindCompany(companyId);
        if(manager != null){
            List<Message> messages = UserInterface.FindMessage(dbName);
            feedback += "[";
            for(Message m:messages){
                feedback += String.valueOf(DataHandle.structureJSON(m))+",";
            }
            feedback = feedback.substring(0,feedback.length()-1);
            feedback+="]";
        }
        w.write(feedback);
        w.close();
        JdbcUtil.closeOperation();
    }
}
