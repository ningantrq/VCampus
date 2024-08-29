package com.vcampus;


import com.vcampus.dao.DbHelper;
import com.vcampus.func.server.UserRespond;
import com.vcampus.message.Message;
import com.vcampus.net.ServerNet;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;

public class ServerApplication {

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
                System.out.println("已连接客户端" + clientSocket);
                Message receivedMessage= ServerNet.receiveObject(new Message(),clientSocket);//把message对象接收
                switch(receivedMessage.getOperationType())//比对message的操作类型
                {
                    case Login://登录
                        UserRespond.login(receivedMessage, clientSocket);//调用对应的响应函数
                        break;

                    case Register://注册
                        UserRespond.register(receivedMessage, clientSocket);
                        //对应的响应函数
                        break;

                    case ChangePassword://修改密码
                        UserRespond.ChangePassword(receivedMessage,clientSocket);
                        break;

                    default://错误类型的处理
                        System.out.println("wrong operationType");
                        break;
                }

            }
        }catch(IOException e)
        {
            e.printStackTrace();
        }
    }
}
