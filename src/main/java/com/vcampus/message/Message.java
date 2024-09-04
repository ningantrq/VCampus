package com.vcampus.message;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Message implements Serializable{


    private List<Object> objectList;//用于储存需要发送的数据

public Message() {
        this.objectList = new ArrayList<>();
    }

    public enum operationType//自定义类型，用来表示操作类型
    {
        //登录功能

        Login,//登录
        Register,//注册
        ChangePassword;//更改密码

    }
    private operationType theOperationType;//一个私有的操作类型对象


    public <T > boolean addObject (T obj)//往Objectlist里面加入一条任意类型的数据
    {
        if (obj != null) {
            this.objectList.add(obj);
            return true;
        } else
            return false;
    }

    //获取并返回一个message对象的Objectlist
    public List<Object> getObjectList () {
        return this.objectList;
    }

    public void setObjectList (List < Object > objlist)
    {
        this.objectList = objlist;
    }

    //把一个message类对象的操作类型改成传进的参数
    public boolean changeOperateType (operationType operationType0){
        if (operationType0 != null) {
            this.theOperationType = operationType0;
            return true;
        }
        return false;
    }

    //用一个String类型对象改变操作类型
    public boolean changeOperateType (String operationType0){
        if (operationType0 != null) {
            try {
                this.theOperationType = operationType.valueOf(operationType0);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }

            return true;
        }
        return false;
    }
    public operationType getOperationType () {
        return this.theOperationType;
    }

    public String getOperationTypeString ()
    {
        return this.theOperationType.toString();
    }

}
