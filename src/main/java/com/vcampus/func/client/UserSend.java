package com.vcampus.func.client;

import com.vcampus.message.Message;
import com.vcampus.net.ClientNet;
import com.vcampus.pojo.UserPojo;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.UnknownHostException;
import java.sql.SQLException;

public class UserSend {

    // 用户登录
    public static UserPojo login(String id, String pwd) throws SQLException, InterruptedException, UnknownHostException, IOException {
        Message temp = new Message();
        temp.addObject(id);
        temp.addObject(pwd);
        temp.changeOperateType("Login");
        UserPojo userPojo = ClientNet.sendAndReceive(temp, new UserPojo());
        return userPojo;
    }

    // 用户注册
    public static boolean register(String id, String name, int age, String sex, String pwd, String role, BufferedImage photo) throws SQLException, UnknownHostException, IOException {
        UserPojo tempUser = new UserPojo(id,pwd,role);
        Message temp = new Message();
        temp.addObject(tempUser);
        temp.changeOperateType("Register");
        boolean ok = ClientNet.sendAndReceive(temp, false);
        return ok;
    }

    // 用户修改密码
    public static boolean changePassword(UserPojo userPojo, String newPwd) throws SQLException, UnknownHostException, IOException {
        userPojo.setuPwd(newPwd);
        Message temp = new Message();
        temp.addObject(userPojo);
        temp.changeOperateType("ChangePassword");
        Boolean ok = ClientNet.sendAndReceive(temp, false);

        if (ok == null) {
            System.out.println("Failed to receive a valid response from the server.");
            return false;
        }

        return ok.booleanValue();
    }

    // 用户登录
    public static UserPojo changepassword_helper(String id) throws SQLException,InterruptedException, UnknownHostException, IOException {
        Message temp = new Message();
        temp.addObject(id);
        temp.changeOperateType("Changepassword_helper");
        UserPojo userPojo = ClientNet.sendAndReceive(temp, new UserPojo());
        return userPojo;
    }


}
