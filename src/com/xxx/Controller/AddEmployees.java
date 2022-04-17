package com.xxx.Controller;

import com.xxx.Mapper.UserInterface;
import com.xxx.Util.JdbcUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

public class AddEmployees extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String companyId = req.getParameter("companyId");
        String employeeStr = req.getParameter("employees");

        System.out.println("进入访问："+companyId+"/"+employeeStr);

        //访问成功
        resp.setStatus(200);
        resp.setContentType("text/json;charset=utf-8");
        PrintWriter w = resp.getWriter();
        String feedback = "";

        String dbName = UserInterface.FindCompany(companyId);
        List<String> employeeList = Arrays.asList(employeeStr.split(","));
        int index = 0;
        StringBuffer sb = new StringBuffer();

        for(String employee:employeeList){
            if(!UserInterface.AddEmployee(dbName,employee)) sb.append("第"+index+"条数据格式有误");
            index++;
        }

        feedback = sb.toString();

        w.write(feedback);
        w.close();
        JdbcUtil.closeOperation();


    }
}
