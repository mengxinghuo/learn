package com.truck.util;

import com.google.common.collect.Maps;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

/**
 * Created by
 */
public  class PostUtil {

    private static  final Logger log = LoggerFactory.getLogger(PostUtil.class);

/*    //图片扫描
    public static void main(String[] args) {
        Map map = Maps.newHashMap();
        map.put("destination","/home/uftp/a6a1a0a6-b155-4f78-a8b5-7c4b6457376c.jpeg");
        String json = jsonPost("http://39.104.179.20:5000",map);
        JSONObject jsonObject = JSONObject.fromObject(json);
        System.out.println(jsonObject);
    }*/

    //获取hr信息
    public static void main(String[] args) {
        Map map = Maps.newHashMap();
        map.put("adminName","windy");
        map.put("password","123456");
        String json = jsonPost("http://39.104.61.50:8089/admin/login.do",map);
//        String json = jsonPost("http://39.104.61.50:8089//manage/employee/ge、t_employee_list.do",map);
        System.out.println(json);
        JSONObject jsonObject = JSONObject.fromObject(json);
        System.out.println(jsonObject);
    }

    /**
     * 发送HttpPost请求
     *
     * @param strURL
     *            服务地址
     * @param params
     *
     * @return 成功:返回json字符串<br/>
     */
    public static String jsonPost(String strURL, Map<String, String> params) {
        try {
            log.info("url=={}",strURL);
            log.info("params=={}",params);
            URL url = new URL(strURL);// 创建连接
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestMethod("POST"); // 设置请求方式
            connection.setRequestProperty("Accept", "application/json"); // 设置接收数据的格式
            connection.setRequestProperty("Content-Type", "application/json"); // 设置发送数据的格式
            connection.connect();
            OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), "UTF-8"); // utf-8编码
            out.append(JsonUtil.obj2String(params));
            out.flush();
            out.close();

            int code = connection.getResponseCode();
            InputStream is = null;
            if (code == 200) {
                is = connection.getInputStream();
            } else {
                is = connection.getErrorStream();
            }

            // 读取响应
            int length = (int) connection.getContentLength();// 获取长度
            if (length != -1) {
                byte[] data = new byte[length];
                byte[] temp = new byte[512];
                int readLen = 0;
                int destPos = 0;
                while ((readLen = is.read(temp)) > 0) {
                    System.arraycopy(temp, 0, data, destPos, readLen);
                    destPos += readLen;
                }
                String result = new String(data, "UTF-8"); // utf-8编码
                return result;
            }

        } catch (IOException e) {
            log.error("Exception occur when send http post request!", e);
        }
        return "error"; // 自定义错误信息
    }
}