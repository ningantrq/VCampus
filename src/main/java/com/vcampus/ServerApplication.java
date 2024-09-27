package com.vcampus;


import com.vcampus.dao.DbHelper;
import com.vcampus.func.client.LibSend;
import com.vcampus.func.server.*;
import com.vcampus.message.Message;
import com.vcampus.net.ServerNet;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;

import static com.vcampus.func.server.LibRespond.loadStuBookBorrowData;

public class ServerApplication {
    private ArrayList<ServerThread> serverThreads;


    public static void main(String[] args) throws IOException, SQLException {
        DbHelper.connect();//连接数据库
        start();//调用服务器启动函数
    }

    static void start() throws SQLException//服务器启动函数
    {
        try (ServerSocket serverSocket = new ServerSocket(54321)){
            System.out.println("服务器已启动，等待客户端连接...");
            while(true)
            {
                Socket clientSocket = serverSocket.accept(); // 等待客户端连接
                ServerThread serverThread = new ServerThread(clientSocket);
                serverThread.start();
            }
        }catch(IOException e)
        {
            e.printStackTrace();
        }
    }



}