package com.shoppingcart.Model;

public class Comments {
    public String cId,pname,name,comment,date,time,pid;

    public Comments() {
    }

    public Comments(String pid,String cId, String pname, String name, String comment, String date, String time) {
        this.cId = cId;
        this.pname = pname;
        this.name = name;
        this.comment = comment;
        this.date = date;
        this.time = time;
        this.pid = pid;
    }

    public String getpid() {
        return pid;
    }

    public void setpid(String pid) {
        this.pid = pid;
    }


    public String getcId() {
        return cId;
    }

    public void setcId(String cId) {
        this.cId = cId;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}