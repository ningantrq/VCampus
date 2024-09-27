package com.vcampus.pojo;

import java.io.Serial;
import java.io.Serializable;

public class BookPojo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String bId;
    private String bName;
    private String author;
    private int totalNum;
    private int freeNum;
    private int borrowNum;
    private String isBorrow;
    private String bType;
    private String bPlace;

    public BookPojo(String id, String name, String type, String bookauthor,String place,String borrow) {
        bId = id;
        bName = name;
        author = bookauthor;
        isBorrow = borrow;
        bType = type;
        bPlace = place;
    }
    public BookPojo() {
    }

    public String getBId() {
        return bId;
    }
    public void setbId(String bId) {
        this.bId = bId;
    }
    public String getBName() {return bName;}
    public void setbName(String bName) {this.bName = bName;}
    public String getAuthor() {return author;}
    public void setAuthor(String author) {this.author = author;}
    public int getTotalNum() {return totalNum;}
    public void setTotalNum(int totalNum) {this.totalNum = totalNum;}
    public int getFreeNum() {return freeNum;}
    public void setFreeNum(int freeNum) {this.freeNum = freeNum;}
    public int getBorrowNum() {return borrowNum;}
    public void setBorrowNum(int borrowNum) {this.borrowNum = borrowNum;}
    public void setIsBorrow(String isborrow) {this.isBorrow = isborrow;}
    public String getIsBorrow() {return isBorrow;}
    public String getType() {return bType;}
    public void setType(String bType) {this.bType = bType;}
    public String getPlace() {return bPlace;}
    public void setPlace(String bPlace) {this.bPlace = bPlace;}
}
