package com.xxx.Util;

import com.sun.org.apache.xml.internal.security.utils.Base64;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class ImageUtil {



    /**
     * 上传图片base64字符串转化成图片
     * @param fileName 文件名
     * @param imgStr 图片码流文件
     * @return
     */
    public static String GenerateImage(String fileName,String imgStr) { // 对字节数组字符串进行Base64解码并生成图片


        //TODO 部署到服务器时需要更改地址
        String imgFilePath = "D:/tomcatimg",imgPath="";
        //String fileName = System.currentTimeMillis()+DataUtil.getRond4()+".jpg";
        if (imgStr == null) // 图像数据为空
            return null;
        try{
            if(imgStr.indexOf(",")!=-1){
                imgStr=imgStr.substring(imgStr.indexOf(",")+1);
            }
            byte dataByte[] = Base64.decode(imgStr);

            File file = new File(imgFilePath);
            if (!file.exists()) {
                file.mkdirs();// 创建父目录地址
            }
// 生成jpeg图片
            imgPath=imgFilePath+"/"+fileName;//创建图片地址
            OutputStream out=new FileOutputStream(imgPath);

            out.write(dataByte);
            out.flush();
            out.close();
        }catch (Exception e){


        }
        return fileName;
    }
}
