package com.vcampus.dao;//package xxxx.xxxx.DAO;

//import xxxx.xxxx.StudentInfo;


import java.sql.*;
import java.io.File;


public class ImageDao {
    /**
     * 获取图片
     * @param imageId，即1，2，3，4，5（目前只有5张）
     * @return byte[] 图片信息，字节数组
     */
    public static byte[] getImage(String imageId)

    {
        String sqlString = "SELECT image FROM tblImages WHERE pId = ?";
String url="jdbc:sqlite:D:\\Ning An\\VCampus\\VCampus\\src\\main\\java\\com\\vcampus\\db\\my_db.db";
        try (Connection con = DriverManager.getConnection(url);
             PreparedStatement pstmt = con.prepareStatement(sqlString)) {

            pstmt.setString(1, imageId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getBytes("image"); // 获取 BLOB 数据
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // 如果没有找到图像，返回 null
    }


}
