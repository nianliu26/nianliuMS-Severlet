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


/**
 * 改变员工账号密码的接口
 * 请求方式：POST，参数：公司id-companyId，员工编号-eid，原密码-password，新密码-newPassword
 * 1、通过eid查询员工
 * 2、判断员工是否存在，以及原密码是否正确，若不正确返回空数据
 * 3、若正确返回新密码
 */
public class PasswordChangeServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //System.out.println("员工想要修改密码");

        //访问成功
        resp.setStatus(200);
        resp.setContentType("text/json;charset=utf-8");
        PrintWriter w = resp.getWriter();

        String companyId = req.getParameter("companyId");
        String eid = req.getParameter("eid");
        String password = req.getParameter("password");
        String newPassword = req.getParameter("newPassword");

        System.out.println(companyId+"/"+eid+"/"+password+"/"+newPassword);

        String dbName = UserInterface.FindCompany(companyId);
        Employee employee = UserInterface.FindEmployeeByEid(dbName,eid);

        if(employee != null && employee.getPassword().equals(password)
                && UserInterface.updateEmployeePassword(dbName,eid,newPassword)){
            w.write(newPassword);
        }else {
            w.write("");
        }
        w.close();
        JdbcUtil.closeOperation();

    }
}
