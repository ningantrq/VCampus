package com.vcampus.func.server;

import com.vcampus.dao.GoodDao;
import com.vcampus.dao.OrderDao;
import com.vcampus.message.Message;
import com.vcampus.net.ServerNet;
import com.vcampus.pojo.OrderPojo;

import java.io.IOException;
import java.net.Socket;

public class OrderRespond {
    public static void getOrderData(Message message, Socket clientSocket) throws IOException {
        OrderPojo[] temp = OrderDao.ReturnGoodsOrders();
        ServerNet.sendObject(temp, clientSocket);
    }

    public static void getStuOrderData(Message message, Socket clientSocket) throws IOException {
        String uId = (String)message.getObjectList().get(0);
        OrderPojo[] temp = OrderDao.showOrdersByuId(uId);
        ServerNet.sendObject(temp, clientSocket);
    }

    public static void buyAndCheck(Message message, Socket clientSocket) throws IOException {
        double money = (double)message.getObjectList().get(0);
        String uId = (String)message.getObjectList().get(1);
        String password = (String)message.getObjectList().get(2);
        boolean ok = GoodDao.checkAndDeductFunds(money, uId, password);
        ServerNet.sendObject(ok, clientSocket);
    }

    public static void addOrder (Message message, Socket clientSocket) throws IOException {
        String uId = (String)message.getObjectList().get(0);
        String gId = (String)message.getObjectList().get(1);
        String gName = (String)message.getObjectList().get(2);
        double price = (double)message.getObjectList().get(3);
        int count = (int)message.getObjectList().get(4);
        boolean ok = OrderDao.AddOrder(uId, gId, gName, price, count);
        ServerNet.sendObject(ok, clientSocket);
    }
}
