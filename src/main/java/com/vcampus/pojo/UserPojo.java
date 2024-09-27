package com.vcampus.pojo;

import java.io.Serial;
import java.io.Serializable;

//用户基本信息类

public class UserPojo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String uId;
    private String uPwd;
    private String uRole;

    public UserPojo(String id, String pwd, String role) {
        uId=id;
        uPwd=pwd;
        uRole=role;
    }

    public UserPojo() {
    }
    public String getuId() {
        return uId;
    }
    public void setuId(String uId) {
        this.uId = uId;
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

    public String getUId() {
        return uId;
    }
    public void setUId(String uId) {
        this.uId = uId;
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
}
