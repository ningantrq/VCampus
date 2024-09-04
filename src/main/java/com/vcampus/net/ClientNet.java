package com.vcampus.net;


import com.vcampus.util.ClientOption;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientNet {

//    private static Socket clientSocket;
//
//    static {
//        try {
//            clientSocket = new Socket(ClientOption.getHost(),ClientOption.getPort());
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }


    //集成了下面的发送和接收函数，使得socket不会在链接中途销毁
    public static <T,P>P sendAndReceive(T obj1,P obj2) throws UnknownHostException, IOException
    {
        Socket clientSocket=new Socket("localhost",54321);
        sendObject(obj1,clientSocket);
        P answer=receiveObject(obj2,clientSocket);
        clientSocket.close();
        return answer;
    }


    //将传入的obj发送到传入的Socket所连接到的服务器
    static <T> boolean sendObject(T obj,Socket clientSocket)
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



    //将用该clientSocket所连接上的服务器发送的数据接收并转化为对应的obj类型
    @SuppressWarnings("unchecked")
    static <T>T receiveObject(T obj,Socket clientSocket)
    {
        Object receivedObj=transformObject(clientSocket);//调用下面的类反序列化函数
        obj= (T)receivedObj;
        return obj;
    }



    //类序列化函数
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
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;

    }

//    public static void closeSocket() throws IOException {
//        clientSocket.close();
//    }
}
