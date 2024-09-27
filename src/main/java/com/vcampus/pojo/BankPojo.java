package com.vcampus.pojo;

import java.io.Serializable;
import java.sql.Date;

/**导入所需要的StudentInfo类，包括uid,urole,uname,usex,uage,udate,uacademy**/
public class BankPojo implements Serializable {
    private String aId;
    private String aName;
    private double aMoney;
    private String isFroze;

    public BankPojo(String i, String n, double m, String f) {
        aId = i;
        aName = n;
        aMoney = m;
        isFroze = f;
    }

    public BankPojo() {
    }

    // Setter methods
    public void setAId(String i) {
        aId = i;
    }

    public void setAName(String r) {
        aName = r;
    }

    public void setAMoney(double s) {
        aMoney = s;
    }

    public void setIsFroze(String f) {
        isFroze = f;
    }

    // Getter methods
    public String getAId() {
        return aId;
    }

    public String getAName() {
        return aName;
    }

    public double getAMoney() {
        return aMoney;
    }

    public String getIsFroze() {
        return isFroze;
    }
}