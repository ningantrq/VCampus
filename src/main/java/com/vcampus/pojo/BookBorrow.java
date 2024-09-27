package com.vcampus.pojo;
import com.vcampus.dao.BookDao;
import java.io.Serial;
import java.io.Serializable;

import static com.vcampus.dao.BookDao.findBookBybId;
import static com.vcampus.dao.UserDao.findUserByuId;

public class BookBorrow implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String bId;        // Book ID (书号)
    private String bName;
    private String uId;        // User ID (一卡通号)
    private String borrowTime; // Borrow time (借书时间)
    private String outdateTime; // Outdate time (过期时间)

    // Constructor with parameters
    public BookBorrow(String uId, String bId, String borrowTime, String outdateTime) {
        this.uId = uId;
        UserPojo tempuser = findUserByuId(uId);
        this.bId = bId;
        BookPojo[] temp2 = findBookBybId(bId);
        BookPojo temp = temp2[0];
        this.bName = temp.getBName();
        this.borrowTime = borrowTime;
        this.outdateTime = outdateTime;
    }


    // Default constructor
    public BookBorrow() {
    }

    // Getters and setters
    public String getBId() {
        return bId;
    }

    public void setbId(String bId) {
        this.bId = bId;
    }

    public String getBName() {return bName;}
    public void setbName(String bName) {
        this.bName = bName;
    }


    public String getUId() {
        return uId;
    }

    public void setUId(String uId) {
        this.uId = uId;
    }

    public String getBorrowTime() {
        return borrowTime;
    }

    public void setBorrowTime(String borrowTime) {
        this.borrowTime = borrowTime;
    }

    public String getOutdateTime() {
        return outdateTime;
    }

    public void setOutdateTime(String outdateTime) {
        this.outdateTime = outdateTime;
    }

    @Override
    public String toString() {
        return "BookBorrow{" +
                "uId='" + uId + '\'' +
                ", bId='" + bId + '\'' +
                ", borrowTime='" + borrowTime + '\'' +
                ", outdateTime='" + outdateTime + '\'' +
                '}';
    }
}
