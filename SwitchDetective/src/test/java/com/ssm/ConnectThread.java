package com.ssm;

import com.ssm.demo.util.SocketClient;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Date;

public class ConnectThread implements Runnable {

    private String name;
    private String path;
    private static int count_of_threads = 0;
    private static int success = 0;
    private static int failed = 0;

    public static int getCount_of_threads() {
        return count_of_threads;
    }

    public static int getSuccess() {
        return success;
    }

    public static int getFailed() {
        return failed;
    }

    public ConnectThread(String name, String path){
        super();
        count_of_threads++;
        this.name = name;
        this.path = path;
    }

    @Override
    public void run() {
        Date date = new Date();
        long oldTime = date.getTime();
        System.out.println("第"+name+"线程开始执行：");
        SocketClient socketClient = new SocketClient();
        try {
            socketClient.startConnection("localhost",7000);
            String retMessage = socketClient.senMessage(path);
            System.out.println(retMessage);
            JSONObject root = new JSONObject(retMessage);
            String retPath = root.getString("result");
            System.out.println("解析JSON获取路径：" + retPath);
            socketClient.stopConnection();
            success++;
        } catch (IOException e) {
            e.printStackTrace();
            failed++;
        }
        date = new Date();
        long now = date.getTime();
        System.out.println("第"+name+"线程执行完毕,执行时间：" + (now - oldTime) + "ms");
    }
}
