package com.xxx.Util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Helper {

    private static String bytesToHex(byte[] bytes){
        StringBuffer md5str=new StringBuffer();
        //把数组每一字节换成16进制连成md5字符串
        int digital;
        for (int i=0;i<bytes.length;i++){
            digital=bytes[i];
            if (digital<0){
                digital +=256;
            }
            if (digital<16){
                md5str.append("0");
            }
            md5str.append(Integer.toHexString(digital));
        }
        return md5str.toString().toLowerCase();
    }

    public static String GetMD5Code(String strObj){
        String resultString=null;
        try {
            resultString=new String(strObj);
            MessageDigest md=MessageDigest.getInstance("MD5");
            //md.digest(); //该函数返回值为存放哈希值结果的byte数组
            byte b[]=md.digest(strObj.getBytes());
            resultString=bytesToHex(b);
        } catch (NoSuchAlgorithmException ex){
            ex.printStackTrace();
        }
        return resultString;
    }
}
