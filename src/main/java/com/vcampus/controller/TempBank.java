package com.vcampus.controller;

import com.vcampus.pojo.BankPojo;

public class TempBank {
    static private BankPojo Bank;
    public static BankPojo getBank()
    {
        return Bank;
    }


    public static void setBank(BankPojo bank)
    {
        Bank=bank;
    }
}

