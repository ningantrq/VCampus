package com.vcampus.dao;


import com.vcampus.pojo.UserPojo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDao {
    public static UserPojo findById(String uId) throws SQLException {
        String sqlString = "select * from tblUser where uId = '" + uId + "'";
        ResultSet rs = DbHelper.read(sqlString);
        UserPojo user = new UserPojo();
        if(rs==null)
        {
            user=null;
        }
        else if(rs.next()) {
            user.setuId(rs.getString("uId"));
            user.setuName(rs.getString("uName"));
            user.setuAge(rs.getInt("uAge"));
            user.setuSex(rs.getString("uSex"));
            user.setuPwd(rs.getString("uPwd"));
            user.setuRole(rs.getString("uRole"));
            user.setuBalance(rs.getDouble("uBalance"));
        }
        return user;
    }
    public static ArrayList<UserPojo> findByName(String uName) throws SQLException {
        String sqlString = "select * from tblUser where uName = '" + uName + "'";
        ResultSet rs = DbHelper.read(sqlString);
        ArrayList<UserPojo> list = new ArrayList<>();
        if(rs==null)return null;
        else {
            while(rs.next()) {
                UserPojo user = new UserPojo();
                user.setuId(rs.getString("uId"));
                user.setuName(rs.getString("uName"));
                user.setuAge(rs.getInt("uAge"));
                user.setuSex(rs.getString("uSex"));
                user.setuPwd(rs.getString("uPwd"));
                user.setuRole(rs.getString("uRole"));
                user.setuBalance(rs.getDouble("uBalance"));
                list.add(user);
            }
            return list;
        }

    }
    public static ArrayList<UserPojo> display(int limit,int offset) throws SQLException
    {
        ArrayList<UserPojo> list = new ArrayList<>();
        String sqlString = String.format("select * from tblUser limit %d offset %d",limit,offset);
        ResultSet rs = DbHelper.read(sqlString);
        if(rs==null)return null;
        else {
            while(rs.next()) {
                UserPojo user = new UserPojo();
                user.setuId(rs.getString("uId"));
                user.setuName(rs.getString("uName"));
                user.setuAge(rs.getInt("uAge"));
                user.setuSex(rs.getString("uSex"));
                user.setuPwd(rs.getString("uPwd"));
                user.setuRole(rs.getString("uRole"));
                user.setuBalance(rs.getDouble("uBalance"));
                list.add(user);
            }
            return list;
        }
    }
    public static boolean create(UserPojo user) throws SQLException {
        String sqlString = String.format("insert into tblUser (uId,uName,uAge,uSex,uPwd,uRole,uBalance) values ('%s','%s',%d,'%s','%s','%s',%f)",user.getuId(),user.getuName(),user.getuAge(),user.getuSex(),user.getuPwd(),user.getuRole(),user.getuBalance());
        return DbHelper.create(sqlString);
    }
    public static boolean update(UserPojo user) throws SQLException {
        String sqlString = String.format("update tblUser set uName='%s',uAge=%d,uSex='%s',uPwd='%s',uRole='%s',uBalance=%f where uId='%s'",user.getuName(),user.getuAge(),user.getuSex(),user.getuPwd(),user.getuRole(),user.getuBalance(),user.getuId());
        return DbHelper.update(sqlString);
    }
    public static boolean deleteById(String uId) throws SQLException {
        String sqlString = "delete from tblUser where uId = '" + uId + "'";
        return DbHelper.delete(sqlString);
    }
}
