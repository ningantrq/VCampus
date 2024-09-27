package com.vcampus.func.server;

import com.vcampus.dao.GoodDao;
import com.vcampus.message.Message;
import com.vcampus.net.ServerNet;
import com.vcampus.pojo.GoodPojo;

import java.io.IOException;
import java.net.Socket;

public class GoodRespond {
    public static void getGoodData(Message message, Socket clientSocket) throws IOException {
        GoodPojo[] temp = GoodDao.showAllGoods();
        System.err.println("GoodRespond");
        ServerNet.sendObject(temp, clientSocket);
    }

    public static void addGoodData(Message message, Socket clientSocket) throws IOException {
        GoodPojo g = (GoodPojo) message.getObjectList().get(0);
        boolean ok = GoodDao.AddGood(g);
        ServerNet.sendObject(ok, clientSocket);
    }

    public static void deleteGoodData(Message message, Socket clientSocket) throws IOException {
        String id = (String) message.getObjectList().get(0);
        boolean ok = GoodDao.deleteGoodById(id);
        ServerNet.sendObject(ok, clientSocket);
    }

    public static void ModifyGoodData(Message message, Socket clientSocket) throws IOException {
        String id = (String) message.getObjectList().get(0);
        GoodPojo g = (GoodPojo) message.getObjectList().get(1);
        boolean ok = GoodDao.deleteGoodById(id);
        ok &= GoodDao.AddGood(g);
        ServerNet.sendObject(ok, clientSocket);
    }
}
