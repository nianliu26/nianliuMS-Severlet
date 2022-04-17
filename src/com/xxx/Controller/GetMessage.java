package com.xxx.Controller;

import com.xxx.Mapper.UserInterface;
import com.xxx.Module.Employee;
import com.xxx.Module.Message;
import com.xxx.Util.DataHandle;
import com.xxx.Util.JdbcUtil;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class GetMessage extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String eid = req.getParameter("eid");
        String companyId = req.getParameter("companyId");
        String feedback = "";

        //访问成功
        resp.setStatus(200);
        resp.setContentType("text/json;charset=utf-8");
        PrintWriter w = resp.getWriter();


        String dbName = UserInterface.FindCompany(companyId);
        Employee employee = UserInterface.FindEmployeeByEid(dbName,eid);

        if(employee != null){
            List<Message> messages = UserInterface.FindMessage(dbName,eid);
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
