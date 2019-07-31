package net.xcvideo.utils;

import java.security.MessageDigest;
import java.util.UUID;

public class CommonUtils {

    /**
     * 生成UUID，用来标识一笔订单
     * @return
     */
    public static String generateUUID(){
        return UUID.randomUUID().toString().replaceAll("-","").substring(0,32);
    }

    /**
     * MD5封装方法
     * @param data
     * @return
     */
    public static String MD5(String data) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] arr = md5.digest(data.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();
            for(byte a : arr){
                sb.append(Integer.toHexString((a & 0xFF) | 0x100).substring(1,3));
            }
            return sb.toString().toUpperCase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}