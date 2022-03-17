package com.xxx.Controller;

import com.xxx.Mapper.UserInterface;
import com.xxx.Module.Employee;
import com.xxx.Util.ImageUtil;
import com.xxx.Util.JdbcUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class SaveImageServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        System.out.println("进入访问");

        String eid = req.getParameter("eid");
        String companyId = req.getParameter("companyId");
        String image = req.getParameter("image");

        String dbName = UserInterface.FindCompany(companyId);
        Employee employee = UserInterface.FindEmployeeByEid(dbName,eid);
        String imageName = employee.getName();

        System.out.println("eid="+eid+",companyId="+companyId+",imageName="+imageName);

        //访问成功
        resp.setStatus(200);
        resp.setContentType("text/json;charset=utf-8");
        PrintWriter w = resp.getWriter();

        if(employee != null){
            String imgPath= "img/"+ImageUtil.GenerateImage(System.currentTimeMillis()+".jpg",image);

            System.out.println("图片路径："+imgPath);
            if(!imgPath.equals("") && UserInterface.updateEmployeePhotoByEid(dbName,eid,imgPath)){
                System.out.println("添加成功，"+imgPath);
                w.write(imgPath);
            }else{
                System.out.println("添加失败，");
            }
        }else{
            System.out.println("employee为空");
            w.write("");
        }

        w.close();
        JdbcUtil.closeOperation();


    }
}
