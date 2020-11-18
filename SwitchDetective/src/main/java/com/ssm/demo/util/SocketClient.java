package com.ssm.demo.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SocketClient {
    private Socket clientSocket;
    private PrintWriter printWriter;
    private BufferedReader bufferedReader;

    public void startConnection(String ip, int port) throws IOException {
        clientSocket = new Socket(ip, port);
        printWriter = new PrintWriter(clientSocket.getOutputStream(), true);
        bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    public String senMessage(String path_Image_ToBeDetected) throws IOException {
        printWriter.println(path_Image_ToBeDetected);
        return bufferedReader.readLine();
    }

    public void stopConnection() throws IOException {
        printWriter.close();
        bufferedReader.close();
        clientSocket.close();
    }
}