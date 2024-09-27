package com.vcampus.pojo;

import java.io.Serializable;

public class GoodPojo implements Serializable {
    private String gId;
    private String gName;
    private double gPrice;
    private String category;
    private int gStock;

    public GoodPojo (String id, String name, Double price, String c, Integer stock) {
        gId = id;
        gName = name;
        gPrice = price;
        category = c;
        gStock = stock;
    }

    public GoodPojo () {

    }

    public String getGId () {
        return gId;
    }
    public String getGName () {
        return gName;
    }
    public double getGPrice () {
        return gPrice;
    }
    public String getCategory () {
        return category;
    }
    public int getGStock () {
        return gStock;
    }

    public void setGId (String id) {
        gId = id;
    }
    public void setGName (String name) {
        gName = name;
    }
    public void setGPrice (double p) {
        gPrice = p;
    }
    public void setCategory (String c) {
        category = c;
    }
    public void setGStock (int s) {
        gStock = s;
    }

}
