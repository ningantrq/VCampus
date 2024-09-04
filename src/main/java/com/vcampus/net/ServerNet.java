package com.vcampus.net;

import java.io.*;
import java.net.Socket;

//发送对象，将传入的obj发送给传进的clientSocket连接的客户端

public class ServerNet {
    public static <T> boolean sendObject(T obj,Socket clientSocket)
    {
        try {
            OutputStream theOut =clientSocket.getOutputStream();
            ObjectOutputStream oos =new ObjectOutputStream(theOut);
            oos.writeObject(obj);
            oos.flush();
            return true;
        }catch(IOException e)
        {
            e.printStackTrace();
        }

        return false;
    }

    //接收对象，将指定socket的obj接收并转化为对应的类型
    @SuppressWarnings("unchecked")
    public static <T>T receiveObject(T obj, Socket clientSocket)
    {
        Object receivedObj=transformObject(clientSocket);//调用下面写的类反序列化函数
        obj= (T)receivedObj;
        return obj;

    }


    //类反序列化函数
    static Object transformObject(Socket clientSocket)
    {
        try {
            // 接收服务器的对象
            InputStream theIn =clientSocket.getInputStream();
            ObjectInputStream ois=new ObjectInputStream(theIn);
            try {
                Object obj=ois.readObject();
                return obj;
            }catch(IOException | ClassNotFoundException e)
            {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }


}
