package com.vcampus.controller;
import com.vcampus.pojo.BookPojo;
import java.util.ArrayList;
import java.util.List;

public class TempBook {
    static private BookPojo[] BUser;
    public static BookPojo[] getBUser()
    {
        return BUser;
    }


    public static void setBUser(BookPojo[] bUser)
    {
        BUser=bUser;
    }

}
