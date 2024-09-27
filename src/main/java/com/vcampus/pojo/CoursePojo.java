package com.vcampus.pojo;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.Serial;
import java.io.Serializable;

//课程信息类

public class CoursePojo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String courseId;
    private  String courseName ;
    private String courseTeacher;
    private  String courseCredit ;
    private String courseDate ;
    private  String courseStart ;
    private String courseEnd ;
    private String courseLocation ;
    private String courseCapacity ;
    private String coursePeopleNumber ;

    private String courseTime;

    public CoursePojo(String courseId, String courseName, String courseTeacher, String courseCredit, String courseDate, String courseStart, String courseEnd, String courseLocation, String courseCapacity, String coursePeopleNumber) {
        setCourseId(courseId);
        setCourseName(courseName);
        setCourseTeacher(courseTeacher);
        setCourseCredit(courseCredit);
        setCourseDate(courseDate);
        setCourseStart(courseStart);
        setCourseEnd(courseEnd);
        setCourseLocation(courseLocation);
        setCourseCapacity(courseCapacity);
        setCoursePeopleNumber(coursePeopleNumber);
        String time="周"+courseDate+" "+courseStart+"-"+courseEnd;
        this.courseTime=time;
    }

    public CoursePojo(String courseId, String courseName, String courseTeacher, String courseCredit, String courseTime, String courseLocation, String courseCapacity, String coursePeopleNumber) {
        setCourseId(courseId);
        setCourseName(courseName);
        setCourseTeacher(courseTeacher);
        setCourseCredit(courseCredit);
        setCourseTime(courseTime);
        setCourseLocation(courseLocation);
        setCourseCapacity(courseCapacity);
        setCoursePeopleNumber(coursePeopleNumber);
        String[] split = courseTime.split("[周 -]");
        String date=split[1];
        String start=split[2];
        String end=split[3];
        setCourseDate(date);
        setCourseStart(start);
        setCourseEnd(end);
    }

    public CoursePojo(){}
    // Getters and Setters

    public void setCourseId(String id)
    {
        this.courseId = id;
    }
    public void setCourseName(String name)
    {
        this.courseName = name;
    }
    public void setCourseTeacher(String teacher)
    {
        this.courseTeacher = teacher;
    }
    public void setCourseCredit(String credit)
    {
        this.courseCredit = credit;
    }
    public void setCourseDate(String date)
    {
        this.courseDate = date;
    }
    public void setCourseStart(String start)
    {
        this.courseStart = start;
    }
    public void setCourseEnd(String end)
    {
        this.courseEnd = end;
    }
    public void setCourseLocation(String location)
    {
        this.courseLocation = location;
    }
    public void setCourseCapacity(String capacity)
    {
        this.courseCapacity = capacity;
    }
    public void setCoursePeopleNumber(String peopleNumber)
    {
        this.coursePeopleNumber=peopleNumber;
    }
    public void setCourseTime(String time)
    {
        this.courseTime=time;
    }

    public String getCourseId() {
        return courseId;
    }
    public String getCourseName() {
        return courseName;
    }
    public String getCourseTeacher() {
        return courseTeacher;
    }
    public String getCourseCredit() {
        return courseCredit;
    }
    public String getCourseDate() {
        return courseDate;
    }
    public String getCourseStart() {
        return courseStart;
    }
    public String getCourseEnd() {
        return courseEnd;
    }
    public String getCourseLocation() {
        return courseLocation;
    }
    public String getCourseCapacity() {
        return courseCapacity;
    }
    public String getCoursePeopleNumber() {
        return coursePeopleNumber;
    }
    public String getCourseTime() {
        return courseTime;
    }
}

