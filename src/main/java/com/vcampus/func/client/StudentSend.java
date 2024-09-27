package com.vcampus.func.client;

import com.vcampus.message.Message;
import com.vcampus.net.ClientNet;
import com.vcampus.pojo.StudentInfo;

import java.io.IOException;

public class StudentSend {
    public static StudentInfo[] loadStudentData() throws IOException {
        Message temp=new Message();
        temp.changeOperateType("GetStudentData");
        StudentInfo[] res = ClientNet.sendAndReceive(temp, new StudentInfo[]{});
        //System.out.println("StudentSend : " + res.length);
        return res;
    }

    public static boolean addStudentData (StudentInfo stu) throws IOException {
        Message temp = new Message();
        temp.addObject(stu);
        temp.changeOperateType("AddStudentData");
        Boolean ok = ClientNet.sendAndReceive(temp, false);
        return ok;
    }

    public static boolean deleteStudentData (String id) throws IOException {
        Message temp = new Message();
        temp.addObject(id);
        temp.changeOperateType("DeleteStudentData");
        Boolean ok = ClientNet.sendAndReceive(temp, false);
        return ok;
    }

    public static boolean modifyStudentData (String id, StudentInfo stu) throws IOException {
        Message temp = new Message();
        temp.addObject(id);
        temp.addObject(stu);
        temp.changeOperateType("ModifyStudentData");
        boolean ok = ClientNet.sendAndReceive(temp, false);
        return ok;
    }

    public static StudentInfo getById (String id) throws IOException {
        Message temp = new Message();
        temp.addObject(id);
        temp.changeOperateType("GetById");
        StudentInfo stu = ClientNet.sendAndReceive(temp, new StudentInfo());
        return stu;
    }
}
