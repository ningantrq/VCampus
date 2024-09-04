package com.vcampus;

import com.vcampus.func.server.UserRespond;
import com.vcampus.message.Message;
import com.vcampus.net.ServerNet;

import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;

public class ServerThread extends Thread{

    private Socket clientSocket=null;
    private boolean isDone=false;

    ServerThread(Socket clientSocket)
    {
        this.clientSocket=clientSocket;
    }

    public void run()
    {
        while(!isDone)
        {
            try {
                threadRun(this.clientSocket);
            } catch (SQLException | IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void threadRun(Socket clientSocket) throws SQLException, IOException {
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
                UserRespond.ChangePassword(receivedMessage, clientSocket);
                break;

            default://错误类型的处理
                System.out.println("wrong operationType");
                break;
        }
        close();
    }

    public void close() throws IOException {
        isDone=true;
        clientSocket.close();
    }

}
