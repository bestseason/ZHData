package edu.zhdata.android.zhapp.entity;

import java.sql.Date;

/**
 * Created by pink2 on 2017/2/27.
 */
public class Topic {
    private int Tid;
    private String Tname;
    private String Tintroduction;
    private String Sid;
    private Date Ttime;

    public Topic(int tid, String tname, String tintroduction, String sid, Date ttime) {
        Tid = tid;
        Tname = tname;
        Tintroduction = tintroduction;
        Sid = sid;
        Ttime = ttime;
    }
    public Topic(String tname, String tintroduction, String sid, Date ttime) {
        Tname = tname;
        Tintroduction = tintroduction;
        Sid = sid;
        Ttime = ttime;
    }

    public int getTid() {
        return Tid;
    }

    public void setTid(int tid) {
        Tid = tid;
    }

    public String getTname() {
        return Tname;
    }

    public void setTname(String tname) {
        Tname = tname;
    }

    public String getTintroduction() {
        return Tintroduction;
    }

    public void setTintroduction(String tintroduction) {
        Tintroduction = tintroduction;
    }

    public String getSid() {
        return Sid;
    }

    public void setSid(String sid) {
        Sid = sid;
    }

    public Date getTtime() {
        return Ttime;
    }

    public void setTtime(Date ttime) {
        Ttime = ttime;
    }
}
