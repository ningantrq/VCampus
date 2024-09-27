package com.vcampus.controller;
import com.vcampus.pojo.BankPojo;
import com.vcampus.pojo.OrderPojo;


public class TempOrder {
    static private OrderPojo Order;
    public static OrderPojo getOrder()
    {
        return Order;
    }


    public static void setOrder(OrderPojo order)
    {
        Order=order;
    }
}
