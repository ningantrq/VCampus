package com.vcampus.func.server;



import com.vcampus.dao.UserDao;
import com.vcampus.message.Message;
import com.vcampus.net.ServerNet;
import com.vcampus.pojo.UserPojo;

import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;

public class UserRespond {

    //登录
    public static void login(Message message, Socket clientSocket) throws SQLException, IOException
    {
        String id=(String)message.getObjectList().get(0);
        String pwd=(String)message.getObjectList().get(1);
        UserPojo tempUser = UserDao.findById(id);
        if(tempUser!=null&&tempUser.getuPwd()!=null&&tempUser.getuPwd().equals(pwd))
        {
//            if(tempUser.getuRole().equals("student"))
//            {
//                Student stu=StudentDao.findById(tempUser.getuId());
//                if(stu==null) ServerNet.sendObject(null,clientSocket);
//                else	ServerNet.sendObject(tempUser,clientSocket);
//            }
//            else
                ServerNet.sendObject(tempUser,clientSocket);
        }
        else
        {
            ServerNet.sendObject(null,clientSocket);
        }
    }

    //注册
    public static void register(Message message,Socket clientSocket) throws SQLException
    {
        UserPojo tempUser=(UserPojo)message.getObjectList().get(0);

        boolean ok=UserDao.create(tempUser);
        if(ok==true)
            ServerNet.sendObject(true,clientSocket);
        else
            ServerNet.sendObject(false,clientSocket);
    }

    //修改密码
    public static void ChangePassword(Message message,Socket clientSocket)throws SQLException
    {
        //User tempUser = (User)message.getObjectList().get(0);
        String id=(String)message.getObjectList().get(0);
        String pwd=(String)message.getObjectList().get(1);
        UserPojo tempUser=UserDao.findById(id);
        tempUser.setuPwd(pwd);
        boolean ok = UserDao.update(tempUser);
        if(ok==true)
            ServerNet.sendObject(true,clientSocket);
        else
            ServerNet.sendObject(false,clientSocket);
    }

}
