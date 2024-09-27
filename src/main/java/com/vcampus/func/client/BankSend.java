package com.vcampus.func.client;
import com.vcampus.message.Message;
import com.vcampus.net.ClientNet;
import com.vcampus.pojo.BankPojo;
import com.vcampus.dao.BankDao;
import com.vcampus.pojo.BookPojo;
import com.vcampus.pojo.OrderPojo;

import java.awt.print.Book;
import java.io.IOException;

import java.io.IOException;

public class BankSend {
    public static boolean changeMoney(BankPojo bank,Double money, String password) throws IOException {
        Message temp = new Message();
        temp.addObject(bank);
        temp.addObject(money);
        temp.addObject(password);
        temp.changeOperateType("ChangeMoney");
        Boolean ok = ClientNet.sendAndReceive(temp,false);
        return ok;
    }

    public static boolean loadBank(BankPojo bank,Double money, String password) throws IOException {
        Message temp = new Message();
        temp.addObject(bank);
        temp.addObject(money);
        temp.addObject(password);
        temp.changeOperateType("ChangeMoney");
        Boolean ok = ClientNet.sendAndReceive(temp,false);
        return ok;
    }

    public static boolean changeFroze(BankPojo bank) throws IOException {
        Message temp = new Message();
        temp.addObject(bank);
        temp.changeOperateType("ChangeFroze");
        Boolean ok = ClientNet.sendAndReceive(temp,false);
        return ok;
    }

    public static BankPojo[] loadBankData() throws IOException {
        Message temp=new Message();
        temp.changeOperateType("GetBankData");
        BankPojo[] res= ClientNet.sendAndReceive(temp,new BankPojo[]{});
        return res;
    }

    public static BankPojo searchBankData(String bankname) throws IOException {

        Message temp = new Message();
        temp.addObject(bankname);
        temp.changeOperateType("SearchBankData");
        BankPojo bank= ClientNet.sendAndReceive(temp,new BankPojo());
        return bank;


    }

    public static boolean createRechargeOrder(String id, Double amount, String feeType) throws IOException {
        Message temp = new Message();
        temp.addObject(id);
        temp.addObject(amount);
        temp.addObject(feeType);
        temp.changeOperateType("CreateRechargeOrder");
        Boolean ok = ClientNet.sendAndReceive(temp,false);
        return ok;
    }

    public static OrderPojo[] loadOrderData(String id) throws IOException {
        Message temp=new Message();
        temp.addObject(id);
        temp.changeOperateType("GetOrderData2");
        OrderPojo[] res= ClientNet.sendAndReceive(temp,new OrderPojo[]{});
        return res;
    }

    public static OrderPojo findOrder(String id) throws IOException {
        Message temp=new Message();
        temp.addObject(id);
        temp.changeOperateType("FindOrder");
        OrderPojo res= ClientNet.sendAndReceive(temp,new OrderPojo());
        return res;
    }

    public static OrderPojo[] findAllOrders() throws IOException {
        Message temp=new Message();
        temp.changeOperateType("FindAllOrders");
        OrderPojo[] res= ClientNet.sendAndReceive(temp,new OrderPojo[]{});
        return res;
    }

}
