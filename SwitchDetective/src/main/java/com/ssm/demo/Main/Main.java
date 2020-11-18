package com.ssm.demo.Main;

import com.ssm.demo.util.SocketClient;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        SocketClient client = new SocketClient();
        client.startConnection("localhost",7000);
        System.out.println(client.senMessage("G:/bring.jpg"));
    }
}
