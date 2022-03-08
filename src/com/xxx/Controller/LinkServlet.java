package com.xxx.Controller;

import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
import com.xxx.Module.StaticData;
import com.xxx.Service.DoService;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

import static com.xxx.Module.StaticData.*;

public class LinkServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");
        PrintWriter w= resp.getWriter();

        doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //对客户端数据进行接收
        //处理json内容
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/json");
        JSONObject acceptjson = new JSONObject();//接收信息
        JSONObject feedback=new JSONObject();//构建反馈信息
        StringBuffer sb = new StringBuffer("");
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader((ServletInputStream)req.getInputStream(), "utf-8"));
            String temp;
            while((temp = br.readLine()) != null){
                sb.append(temp);
            }
            br.close();
            //System.out.println(sb);
            //以上的过程都从request中读取json，并将json转换成string，这样可以显示出来。最终String类型的json就是acceptjson　　
            acceptjson = JSONObject.fromObject(sb.toString());
        } catch (Exception e) {
            // TODO: handle exception 给客户端反馈错误信息
            System.out.println(e);
            e.printStackTrace();
        }


        if (sb.toString()==""){

            //TODO 如果客户端发送了一个空数据，则向客户端发送反馈信息


        }else {
            //获取标识位
            int flag= acceptjson.getInt("handleFlag");
            //System.out.println(flag);
            acceptjson.remove("handleFlag");//删除标识位再做其他操作
            //System.out.println(acceptjson);

            //开启服务
            DoService doService=new DoService();
            OutputStream os=new BufferedOutputStream(resp.getOutputStream());

            //选择需要进行的业务逻辑
            switch (flag){
                //登录功能
                case LOGIN_FLAG:
                    System.out.println("用户想要登录...");

                    //调用登录服务，将返回的数据发送给客户端
                    feedback=doService.loginService(acceptjson);
                    os.write(String.valueOf(feedback).getBytes());
                    os.close();

                    break;
                //注册功能
                case LOGON_FLAG:
                    System.out.println("用户想要注册...");
                    System.out.println("用户发送的数据:"+acceptjson.toString());
                    //调用注册服务
                    feedback=DoService.doLogon(acceptjson);


                    break;

                //接收图片
                case IMAGE_FLAG:

                    try {
                        feedback=doService.saveImage(acceptjson);
                        os.write(String.valueOf(feedback).getBytes());
                        os.close();
                    } catch (Base64DecodingException e) {
                        e.printStackTrace();
                        os.close();
                    }


                    break;

            }
        }




    }
}
