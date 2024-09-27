package com.vcampus.func.client;

import com.vcampus.message.Message;
import com.vcampus.net.ClientNet;
import com.vcampus.pojo.GoodPojo;

import java.io.IOException;

public class GoodSend {
    public static GoodPojo[] getGoodData() throws IOException {
        Message temp=new Message();
        temp.changeOperateType("GetGoodData");
        System.err.println("GoodSend");
        GoodPojo[] res = ClientNet.sendAndReceive(temp, new GoodPojo[]{});
        return res;
    }


    public static boolean addGoodData(GoodPojo g) throws IOException {
        Message temp = new Message();
        temp.addObject(g);
        temp.changeOperateType("AddGoodData");
        Boolean ok = ClientNet.sendAndReceive(temp, false);
        return ok;
    }

    public static boolean deleteGoodData (String id) throws IOException {
        Message temp = new Message();
        temp.addObject(id);
        temp.changeOperateType("DeleteGoodData");
        Boolean ok = ClientNet.sendAndReceive(temp, false);
        return ok;
    }

    public static boolean modifyGoodData (String id, GoodPojo g) throws IOException {
        Message temp = new Message();
        temp.addObject(id);
        temp.addObject(g);
        temp.changeOperateType("ModifyGoodData");
        boolean ok = ClientNet.sendAndReceive(temp, false);
        return ok;
    }
}
