package com.xxx.Service;

import com.google.gson.Gson;
import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
import com.xxx.Controller.LinkServlet;
import com.xxx.Mapper.UserInterface;
import com.xxx.Module.Employee;
import com.xxx.Util.ImageUtil;
import net.sf.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Base64;

public class DoService {


    UserInterface user;

    public DoService(){
        this.user=new UserInterface();
    }


    /**
     * 执行登录的业务逻辑
     * 1、将传入的JSON数据进行处理
     * 2、使用解析之后的员工编号数据查询数据库
     * 3、对比解析后的密码数据与数据库查询到的密码数据
     * 4、相同则构建通过信息，不同构建不通过信息
     * @param jsonObject 经过处理后的json数据
     * @return 返回封装信息
     */
    public JSONObject loginService(JSONObject jsonObject){

        JSONObject feedback=new JSONObject();
        Gson gson=new Gson();
        Employee employee=gson.fromJson(String.valueOf(jsonObject),Employee.class);

        String name=employee.getName();
        String password=employee.getPassword();


        feedback.clear();//清楚所有数据
        //if (true){//TODO 数据库还未创建，创建后则应该更改UserInterface的内容
        /*if (user.Login(name,password)){
            feedback.put("vertify","1");//添加验证通过的数据
            System.out.println("验证通过");
        } else {
            feedback.put("vertify","-1");//添加验证不通过的数据
            System.out.println("验证不通过");
        }*/

        return feedback;
    }


    /**
     * 将传入的json文件数据存入事件待处理数据库
     * 1、解析json数据
     * 2、将数据存入数据库
     * 3、构建返回数据
     * @param jsonObject
     * @return
     */
    public static JSONObject doLogon(JSONObject jsonObject){

        JSONObject feedback=new JSONObject();
        Gson gson=new Gson();
        Employee employee=(Employee) gson.fromJson(String.valueOf(jsonObject),Employee.class);


        return null;
    }




    public JSONObject saveImage(JSONObject image) throws Base64DecodingException {

        String imageInput=image.getString("image");
        String imageName=image.getString("imageName");

        String imgPath= ImageUtil.GenerateImage(imageName+ "-" +System.currentTimeMillis()+".jpg",imageInput);
        JSONObject feedback=new JSONObject();
        feedback.put("handleFlag","image");
        feedback.put("imagePath",imgPath);



        return feedback;
    }

}
