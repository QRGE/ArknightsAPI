package com.strelizia.arknights.util;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import sun.misc.BASE64Encoder;


/**
 * @author wangzy
 * @Date 2020/12/25 9:36
 **/
@Component
@Slf4j
public class ImageUtil {
    /**
     * 根据url下载文件到本地，返回本地路径
     * @param imgUrl
     * @return
     */
    public String getImageLocalPathFromUrl(String imgUrl){
        String dir = "/root/img/";
        File file = new File(dir);
        if (!file.exists()) {
            file.mkdirs();
        }
        URL url;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String imageName = sdf.format(new Date()) + ".jpg";
        String path = dir + imageName;
        InputStream is = null;
        FileOutputStream outStream = null;
        HttpURLConnection httpUrl = null;
        try{
            url = new URL(imgUrl);
            httpUrl = (HttpURLConnection) url.openConnection();
            httpUrl.connect();
            httpUrl.getInputStream();
            is = httpUrl.getInputStream();
            outStream = new FileOutputStream(new File(path));
            byte[] buffer = new byte[1024];
            int len = 0;
            while( (len=is.read(buffer)) != -1 ){
                outStream.write(buffer, 0, len);
            }
            log.info("{}图片下载成功",imageName);
            return path;
        }catch (Exception e) {
            log.info("图片下载失败");
            e.printStackTrace();
        }finally{
            if(is != null)
            {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(outStream != null)
            {
                try {
                    outStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(httpUrl != null)
            {
                httpUrl.disconnect();
            }
        }
        return imgUrl;
    }

    /**
     * 通过图片的url获取图片的base64字符串
     * @param imgUrl    图片url
     * @return    返回图片base64的字符串
     */
    public String getImageBase64ByUrl(String imgUrl) {
        URL url;
        InputStream is = null;
        ByteArrayOutputStream outStream = null;
        HttpURLConnection httpUrl = null;
        try{
            url = new URL(imgUrl);
            httpUrl = (HttpURLConnection) url.openConnection();
            httpUrl.connect();
            httpUrl.getInputStream();
            is = httpUrl.getInputStream();
            outStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while( (len=is.read(buffer)) != -1 ){
                outStream.write(buffer, 0, len);
            }
            return encode(outStream.toByteArray());
        }catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(is != null)
            {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(outStream != null)
            {
                try {
                    outStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(httpUrl != null)
            {
                httpUrl.disconnect();
            }
        }
        return imgUrl;
    }

    /**
     * 图片转字符串
     * @param image
     * @return
     */
    public String encode(byte[] image){
        BASE64Encoder decoder = new BASE64Encoder();
        return replaceEnter(decoder.encode(image));
    }

    public String replaceEnter(String str){
        String reg ="[\n-\r]";
        Pattern p = Pattern.compile(reg);
        Matcher m = p.matcher(str);
        return m.replaceAll("");
    }

    public static void main(String[] args) {
        ImageUtil imageUtil = new ImageUtil();
        System.out.println(imageUtil.getImageBase64ByUrl("http://gchat.qpic.cn/gchatpic_new/843853516/830405854-2534335053-925DAC7FE7CF96175B5DD5E69D4F4B1D/0?vuin=3022645754&term=255&pictype=0"));
    }
}