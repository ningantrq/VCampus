package com.vcampus.func.server;

import com.vcampus.dao.BookDao;
import com.vcampus.dao.OrderDao;
import com.vcampus.message.Message;
import com.vcampus.net.ServerNet;
import com.vcampus.pojo.BankPojo;
import com.vcampus.dao.BankDao;
import com.vcampus.pojo.BookPojo;
import com.vcampus.pojo.OrderPojo;
import java.io.IOException;
import java.net.Socket;

public class BankRespond {
    public static void changeMoney(Message message, Socket clientsocket)throws IOException {
        BankPojo bank=(BankPojo) message.getObjectList().get(0);
        Double money=(Double) message.getObjectList().get(1);
        String password=(String) message.getObjectList().get(2);
        boolean ok = BankDao.changeMoney(bank, money, password);
        ServerNet.sendObject(ok, clientsocket);
    }

    public static void changeFroze(Message message, Socket clientsocket)throws IOException {
        BankPojo bank=(BankPojo) message.getObjectList().get(0);
        boolean ok = BankDao.changeFroze(bank);
        ServerNet.sendObject(ok, clientsocket);
    }

    public static void loadBankData(Message message, Socket clientsocket) throws IOException {
        BankPojo[] temp = BankDao.findallBankAccounts();
        ServerNet.sendObject(temp,clientsocket);
    }

    public static void searchBankData(Message message, Socket clientsocket) throws IOException {
        String bankname=(String)message.getObjectList().get(0);
        BankPojo temp = BankDao.findBankByuId(bankname);
        ServerNet.sendObject(temp,clientsocket);
    }
    public static void createRechargeOrder(Message message, Socket clientsocket)throws IOException {
        String id=(String) message.getObjectList().get(0);
        Double amount=(Double) message.getObjectList().get(1);
        String feeType=(String) message.getObjectList().get(2);
        boolean ok = BankDao.CreateRechargeOrder(id, amount, feeType);
        ServerNet.sendObject(ok, clientsocket);
    }
    public static void loadOrderData(Message message, Socket clientsocket) throws IOException {
        String id=(String) message.getObjectList().get(0);
        OrderPojo[] temp = BankDao.ReturnOrdersByuId(id);
        ServerNet.sendObject(temp,clientsocket);
    }

    public static void findOrder(Message message, Socket clientsocket) throws IOException {
        String id=(String) message.getObjectList().get(0);
        OrderPojo temp = BankDao.findOrder(id);
        ServerNet.sendObject(temp,clientsocket);
    }

    public static void  findAllOrders(Message message, Socket clientsocket) throws IOException {
        OrderPojo[] temp = BankDao.findAllOrders();
        ServerNet.sendObject(temp,clientsocket);
    }
}
