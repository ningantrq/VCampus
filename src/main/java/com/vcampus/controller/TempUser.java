package com.vcampus.controller;

import com.vcampus.pojo.UserPojo;

public class TempUser {
    static private UserPojo TUser;
    public static UserPojo getTUser()
    {
        return TUser;
    }


    public static void setTUser(UserPojo tUser)
    {
        TUser=tUser;
    }
}
