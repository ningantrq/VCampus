package com.vcampus.pojo;

import java.io.Serializable;

/**导入所需要的StudentInfo类，包括uid,urole,uname,usex,uage,udate,uacademy**/
public class StudentInfo implements Serializable {
    //private static final long serialVersionUID = 1L;
    private String uid;
    private String urole;
    private String uname;
    private String usex;
    private int uage;
    private int udate;
    private String uacademy;

    public StudentInfo (String i, String r, String n, String s, int a, int d, String aa) {
        uid = i;
        urole = r;
        uname = n;
        usex = s;
        uage = a;
        udate = d;
        uacademy = aa;
    }
    public StudentInfo () {
    }

    public void setID (String i) {
        uid = i;
    }
    public void setRole (String r) {
        urole = r;
    }
    public void setName (String n) {
        uname = n;
    }
    public void setSex (String s) {
        usex = s;
    }
    public void setAge (int a) {
        uage = a;
    }
    public void setDate (int d) {
        udate = d;
    }
    public void setAcademy (String a) {
        uacademy = a;
    }

    public String getID () {
        return uid;
    }
    public String getRole () {
        return urole;
    }
    public String getName () {
        return uname;
    }
    public String getSex () {
        return usex;
    }
    public int getAge () {
        return uage;
    }
    public int getDate () {
        return udate;
    }
    public String getAcademy () {
        return uacademy;
    }

}
