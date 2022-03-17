package com.xxx.Controller;

import com.xxx.Mapper.UserInterface;
import com.xxx.Module.Employee;
import com.xxx.Util.DataHandle;
import com.xxx.Util.JdbcUtil;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * 通过员工eid获取数据
 * 访问方法：GET
 * 参数：eid，companyId，dataType
 * eid:员工编号
 * dataType:需要获取的数据类型
 * 步骤：
 * 1、先通过companyId和eid查询该员工是否存在
 * 2、再通过dataType选择要查询的数据
 * 3、将查询到的数据返回
 *
 * 返回的数据头：0-查询失败，1-查询成功
 * 返回的数据体：查询到的数据
 */
public class FindDataByEidServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String companyId = req.getParameter("companyId");
        String eid = req.getParameter("eid");
        String dataType = req.getParameter("dataType");
        String feedback = "";


        //访问成功
        resp.setStatus(200);
        resp.setContentType("text/json;charset=utf-8");
        PrintWriter w = resp.getWriter();


        String dbName = UserInterface.FindCompany(companyId);
        Employee employee = UserInterface.FindEmployeeByEid(dbName,eid);

        if(employee != null){
            if(dataType.equals("arrive")){
                Date date = new Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String today = dateFormat.format(date);
                feedback = String.valueOf(DataHandle.structureJSON(UserInterface.FindArrived(dbName,eid,today)));

            }else if(dataType.equals("check")){

            }else if(dataType.equals("message")){

            }else if(dataType.equals("tool")){

            }else if(dataType.equals("zone")){

            }
        }

        if(!feedback.equals(""))
            resp.setHeader("flag","1");
        else resp.setHeader("flag","0");
        w.write(feedback);
        w.close();

        JdbcUtil.closeOperation();

    }
}
