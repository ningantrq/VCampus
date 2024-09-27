package com.vcampus.pojo;

import java.io.Serializable;

public class OrderPojo implements Serializable {
    private static final long serialVersionUID = 3334265784430571707L;
    private String OrderId;
    private String uId;
    private String gId;
    private String gName;
    private double price;
    private int count;
    private double Total;
    private int isGood;

    public void setOrderId(String t) { OrderId = t; }
    public void setUId(String t) { uId = t; }
    public void setGId(String t) { gId = t; }
    public void setGName(String t) { gName = t; }
    public void setPrice(double t) { price = t; }
    public void setCount(int t) { count = t; }
    public void setTotal(double t) { Total = t; }
    public void setIsGood(int t) { isGood = t; }

    public String getOrderId() { return OrderId; }
    public String getUId() { return uId; }
    public String getGId() { return gId; }
    public String getGName() { return gName; }
    public double getPrice() { return price; }
    public int getCount() { return count; }
    public double getTotal() { return Total; }
    public int getIsGood() { return isGood; } // 修改为 getIsGood

    public OrderPojo() {}

    public OrderPojo(String o, String u, String g, String n, double p, int c, double t, int od) {
        OrderId = o;
        uId = u;
        gName = n;
        gId = g;
        price = p;
        count = c;
        Total = t;
        isGood = od;
    }
}