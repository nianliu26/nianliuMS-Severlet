package com.xxx.Controller;

import com.xxx.Mapper.UserInterface;
import com.xxx.Module.Employee;
import com.xxx.Module.Manager;
import com.xxx.Util.DataHandle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


/**
 * 1、用户登录接口，需要采用Get提交方式
 * 2、提交的参数有companyId、eid、password
 * 3、获取到参数之后查找数据库是否有相应的companyId和eid
 *      如果没有则发送“没有找到”的标识符login_flag=0以及空的用户数据user_information=null给客户端
 *      如果有eid，则判断password是否符合
 *          password不符合则发送“密码错误”的标识符login_flag=-1以及空的用户数据user_information=null给客户端
 *          如果符合则发送“密码正确”的标识符login_flag=1以及查询到的用户数据以json格式保存user_information={}给客户端
 */
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String companyId = req.getParameter("companyId");
        String eid = req.getParameter("eid");
        String password = req.getParameter("password");

        //访问成功
        resp.setStatus(200);
        resp.setContentType("text/json;charset=utf-8");
        PrintWriter w = resp.getWriter();



        //管理员登录
        if (companyId.equals("000")){
            Manager manager = UserInterface.FindManager(eid);

            if (manager == null){
                // 发送“没有找到”的标识符login_flag="0"以及空的用户数据user_information=null给客户端
                resp.setHeader("login_flag","0");//构建响应头，在响应头中发送登录状态
                w.write("");
            }else {
                //如果存在就判断密码是否正确
                if (!password.equals(manager.getCrpassword())) {
                    // 发送“密码错误”的标识符login_flag=-1以及空的用户数据user_information=null给客户端
                    resp.setHeader("login_flag", "-1");//构建密码错误的响应头
                    w.write("");
                } else {
                    // 发送“密码正确”的标识符login_flag=1以及查询到的用户数据以json格式保存user_information={}给客户端
                    String feedback = String.valueOf(DataHandle.structureJSON(manager));

                    resp.setHeader("login_flag", "2");//构建管理员密码正确的响应头
                    w.write(feedback);
                }
            }

            return;

        }

        String companyName = UserInterface.FindCompany(companyId);

        // 查询数据库
        if (companyName != null){

            //Employee employee = UserInterface.FindEmployeeByEid("javawebtest",eid);//测试数据
            Employee employee = UserInterface.FindEmployeeByEid(companyName,eid);

            if (employee == null){
                // 发送“没有找到”的标识符login_flag="0"以及空的用户数据user_information=null给客户端
                resp.setHeader("login_flag","0");//构建响应头，在响应头中发送登录状态
                w.write("");
            }else{
                //如果存在就判断密码是否正确
                if(!password.equals(employee.getPassword())){
                    // 发送“密码错误”的标识符login_flag=-1以及空的用户数据user_information=null给客户端
                    resp.setHeader("login_flag","-1");//构建密码错误的响应头
                    w.write("");
                }else {
                    // 发送“密码正确”的标识符login_flag=1以及查询到的用户数据以json格式保存user_information={}给客户端
                    String feedback = String.valueOf(DataHandle.structureJSON(employee));

                    resp.setHeader("login_flag","1");//构建密码正确的响应头
                    w.write(feedback);
                }
            }


        }else {
            System.out.println("未查询到公司");
            // 发送“没有找到”的标识符login_flag="0"以及空的用户数据user_information=null给客户端
            resp.setHeader("login_flag","0");//构建响应头，在响应头中发送登录状态
            w.write("");
        }

    }
}
