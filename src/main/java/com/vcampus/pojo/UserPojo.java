package com.vcampus.pojo;

import java.io.Serial;
import java.io.Serializable;

//用户基本信息类

public class UserPojo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String uId;
    private String uName;
    private int  uAge;
    private String uSex;
    private String uPwd;
    private String uRole;
    private double uBalance;

    public UserPojo(String id, String name, int age, String sex, String pwd, String role) {
        uId=id;
        uName = name;
        uAge=age;
        uSex=sex;
        uPwd=pwd;
        uRole=role;
        uBalance=200;
    }

    public UserPojo() {
    }
    public String getuId() {
        return uId;
    }
    public void setuId(String uId) {
        this.uId = uId;
    }
    public String getuName() {
        return uName;
    }
    public void setuName(String uName) {
        this.uName = uName;
    }
    public int getuAge() {
        return uAge;
    }
    public void setuAge(int uAge) {
        this.uAge = uAge;
    }
    public String getuSex() {
        return uSex;
    }
    public void setuSex(String uSex) {
        this.uSex = uSex;
    }
    public String getuPwd() {
        return uPwd;
    }
    public void setuPwd(String uPwd) {
        this.uPwd = uPwd;
    }
    public String getuRole() {
        return uRole;
    }
    public void setuRole(String uRole) {
        this.uRole = uRole;
    }
    public double getuBalance() {
        return uBalance;
    }
    public void setuBalance(double uBalance) {
        this.uBalance = uBalance;
    }

    public String getUId() {
        return uId;
    }
    public void setUId(String uId) {
        this.uId = uId;
    }
    public String getUName() {
        return uName;
    }
    public void setUName(String uName) {
        this.uName = uName;
    }
    public int getUAge() {
        return uAge;
    }
    public void setUAge(int uAge) {
        this.uAge = uAge;
    }
    public String getUSex() {
        return uSex;
    }
    public void setUSex(String uSex) {
        this.uSex = uSex;
    }
    public String getUPwd() {
        return uPwd;
    }
    public void setUPwd(String uPwd) {
        this.uPwd = uPwd;
    }
    public String getURole() {
        return uRole;
    }
    public void setURole(String uRole) {
        this.uRole = uRole;
    }
    public double getUBalance() {
        return uBalance;
    }
    public void setUBalance(double uBalance) {
        this.uBalance = uBalance;
    }
}
