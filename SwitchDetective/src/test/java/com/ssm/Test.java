package com.ssm;

import java.util.ArrayList;
import java.util.Iterator;

public class Test {

    public static void main(String[] args){
        ArrayList<Thread> threads = new ArrayList<>();
            for(int i=0;i<1;i++){
            threads.add(new Thread(new ConnectThread(i + "", "G:/wai.jpg")));
        }
//        threads.add(new Thread(new ConnectThread(1+"", "G:/bring.jpg")));
//        threads.add(new Thread(new ConnectThread(2+"", "G:/IMG_1009.JPG")));
//        threads.add(new Thread(new ConnectThread(3+"", "G:/IMG_0512.JPG")));
//        threads.add(new Thread(new ConnectThread(4+"", "G:/IMG_1004.JPG")));
//        threads.add(new Thread(new ConnectThread(5+"", "G:/IMG_1045.JPG")));
        for(Thread thread : threads){
            thread.start();
        }
        while(!threads.isEmpty()){
            Iterator<Thread> iterator = threads.listIterator();
            while(iterator.hasNext()){
                Thread thread = iterator.next();
                if(!thread.isAlive()){
                    iterator.remove();
                }
            }
        }
        System.out.println("所有请求线程执行完毕," + "总共线程数：" + ConnectThread.getCount_of_threads() + "，成功线程数:" + ConnectThread.getSuccess()+", 失败线程数: " + ConnectThread.getFailed() + "");
    }
}
