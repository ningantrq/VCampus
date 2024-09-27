package com.vcampus.func.client;

import com.vcampus.message.Message;
import com.vcampus.net.ClientNet;
import com.vcampus.pojo.OrderPojo;

import java.io.IOException;

public class OrderSend  {
    public static OrderPojo[] getOrderData() throws IOException {
        Message temp=new Message();
        temp.changeOperateType("GetOrderData");
        OrderPojo[] res = ClientNet.sendAndReceive(temp, new OrderPojo[]{});
        return res;
    }

    public static OrderPojo[] getStuOrderData(String uId) throws IOException {
        Message temp=new Message();
        temp.addObject(uId);
        temp.changeOperateType("GetStuOrderData");
        OrderPojo[] res = ClientNet.sendAndReceive(temp, new OrderPojo[]{});
        return res;
    }

    public static boolean buyAndCheck(double money, String uId, String password) throws IOException {
        Message temp = new Message();
        temp.addObject(money);
        temp.addObject(uId);
        temp.addObject(password);
        temp.changeOperateType("CheckAndDeductFunds");
        boolean ok = ClientNet.sendAndReceive(temp, false);
        return ok;
    }

    public static boolean addOrder (String uId, String gId, String gName, double price, int count) throws IOException {
        Message temp = new Message();
        temp.addObject(uId);
        temp.addObject(gId);
        temp.addObject(gName);
        temp.addObject(price);
        temp.addObject(count);
        temp.changeOperateType("AddOrder");
        boolean ok = ClientNet.sendAndReceive(temp, false);
        return ok;
    }
}
