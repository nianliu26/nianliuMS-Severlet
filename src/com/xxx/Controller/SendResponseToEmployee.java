package com.xxx.Controller;

import com.xxx.Mapper.UserInterface;
import com.xxx.Module.Manager;
import com.xxx.Util.JdbcUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class SendResponseToEmployee extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String mid = req.getParameter("mid");
        String messageId = req.getParameter("messageId");
        String responseContent = req.getParameter("response");

        //访问成功
        resp.setStatus(200);
        resp.setContentType("text/json;charset=utf-8");
        PrintWriter w = resp.getWriter();
        String feedback = "";

        Manager manager = UserInterface.FindManager(mid);
        String dbName = UserInterface.FindCompany(manager.getCompanyId());
        if(manager != null){
            if(UserInterface.sendResponse(dbName,messageId,responseContent)){
                feedback = "1";
            }
        }
        w.write(feedback);
        w.close();
        JdbcUtil.closeOperation();

    }
}
