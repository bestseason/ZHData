package edu.zhdata.android.zhapp.entity;

/**
 * Created by pink2 on 2017/3/9.
 */

public class RQuestion {
    /**
     * Tname : 睡觉
     * Tid : 1234
     * Qname : 怎么睡个好觉
     * Qid : 123
     */

    private String Tname;
    private int Tid;
    private String Qname;
    private int Qid;

    public String getTname() {
        return Tname;
    }

    public void setTname(String Tname) {
        this.Tname = Tname;
    }

    public int getTid() {
        return Tid;
    }

    public void setTid(int Tid) {
        this.Tid = Tid;
    }

    public String getQname() {
        return Qname;
    }

    public void setQname(String Qname) {
        this.Qname = Qname;
    }

    public int getQid() {
        return Qid;
    }

    public void setQid(int Qid) {
        this.Qid = Qid;
    }
}
