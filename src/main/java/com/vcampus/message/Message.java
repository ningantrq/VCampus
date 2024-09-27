package com.vcampus.message;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 *
 */
public class Message implements Serializable{


    private List<Object> objectList;//用于储存需要发送的数据


    public Message() {
        this.objectList = new ArrayList<>();
    }

    public enum operationType//自定义类型，用来表示操作类型
    {
        //登录功能

        Login,//登录
        Register,//注册
        ChangePassword,//更改密码
        Changepassword_helper,
        GetBookData,
        SearchBookData,
        BorrowBook,
        BorrowBook_show,
        LoadBookBorrowData,
        ReturnBook,
        ReturnBook_show,
        AddBook,
        DeleteBook,
        ModifyBook,
        RenewBook,
        ReturnWarning,
        LoadStuBookBorrowData,
        SearchByBookNumber,
        SearchByType,
        GetStudentData,//获取学生信息
        AddStudentData,//增加学生信息
        DeleteStudentData,//删除学生信息
        ModifyStudentData,//修改学生信息
        GetById,//通过id获取学生信息
        GetGoodData,//获取所有商品的信息
        AddGoodData,//增加商品信息
        DeleteGoodData,//删除商品信息
        ModifyGoodData,//修改商品信息
        GetOrderData,//获取订单信息
        CheckAndDeductFunds,//购买商品
        AddOrder,//增加订单
        GetStuOrderData,//获取某个学生的订单信息
        ShowAllCourses,//显示所有课程信息
        AddCourse,//管理员新增课程
        DeleteCourse,//管理员删除课程
        UpdateCourse,//管理员修改课程信息
        ChooseCourse,//学生选课
        DropCourse,//学生退课
        ChooseCoursePN,//选课成功后课程人数+1
        DropCoursePN,//退课成功后课程人数-1
        ShowChooseCourses,//显示学生已选课信息
        ShowTeacherCourses,//教师端显示自己所有授课信息
        ChangeMoney,
        ChangeFroze,
        GetBankData,
        SearchBankData,
        CreateRechargeOrder,
        GetOrderData2,
        FindOrder,
        FindAllOrders;

    }


    private operationType theOperationType;//一个私有的操作类型对象

    /**
     *
     * @param obj
     * @return
     * @param <T>
     */

    public <T > boolean addObject (T obj)//往Objectlist里面加入一条任意类型的数据
    {
        if (obj != null) {
            this.objectList.add(obj);
            return true;
        } else
            return false;
    }

    /**
     *
     * @return
     */
    //获取并返回一个message对象的Objectlist
    public List<Object> getObjectList () {
        return this.objectList;
    }

    /**
     *
     * @param objlist
     */
    public void setObjectList (List < Object > objlist)
    {
        this.objectList = objlist;
    }

    /**
     *
     * @param operationType0
     * @return
     */
    //把一个message类对象的操作类型改成传进的参数
    public boolean changeOperateType (operationType operationType0){
        if (operationType0 != null) {
            this.theOperationType = operationType0;
            return true;
        }
        return false;
    }

    /**
     *
     * @param operationType0
     * @return
     */
    //用一个String类型对象改变操作类型
    public boolean changeOperateType (String operationType0){
        if (operationType0 != null) {
            try {
                this.theOperationType = operationType.valueOf(operationType0);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }

            return true;
        }
        return false;
    }

    /**
     *
     * @return
     */
    public operationType getOperationType () {
        return this.theOperationType;
    }

    public String getOperationTypeString ()
    {
        return this.theOperationType.toString();
    }

}
