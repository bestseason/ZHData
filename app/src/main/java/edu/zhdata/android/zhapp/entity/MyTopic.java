package edu.zhdata.android.zhapp.entity;

/**
 * Created by pink2 on 2017/3/8.
 */

public class MyTopic {
    private int Tid;
    private String Tname;

    public int getTid() {
        return Tid;
    }

    public void setTid(int Tid) {
        this.Tid = Tid;
    }

    public String getTname() {
        return Tname;
    }

    public void setTname(String Tname) {
        this.Tname = Tname;
    }
    public String toString(){
        return "Tid: "+Tid+" "+"Tname: "+Tname;
    }
}
