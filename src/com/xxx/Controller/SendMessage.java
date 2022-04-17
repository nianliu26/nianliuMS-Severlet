package com.xxx.Controller;

import com.xxx.Mapper.UserInterface;
import com.xxx.Module.Employee;
import com.xxx.Util.JdbcUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class SendMessage extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String companyId = req.getParameter("companyId");
        String eid = req.getParameter("eid");
        String messageTitle = req.getParameter("title");
        String messageContent = req.getParameter("content");

        //访问成功
        resp.setStatus(200);
        resp.setContentType("text/json;charset=utf-8");
        PrintWriter w = resp.getWriter();
        String feedback = "";

        String dbName = UserInterface.FindCompany(companyId);
        Employee employee = UserInterface.FindEmployeeByEid(dbName,eid);

        if(employee!=null){
            if(UserInterface.AddMessage(dbName,eid,messageTitle,messageContent)){
                feedback = "1";
            }
        }
        w.write(feedback);
        w.close();
        JdbcUtil.closeOperation();


    }
}
