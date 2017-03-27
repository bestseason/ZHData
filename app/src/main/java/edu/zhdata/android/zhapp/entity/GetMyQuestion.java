package edu.zhdata.android.zhapp.entity;

/**
 * Created by ck on 2017/3/9.
 */

public class GetMyQuestion {

    /**
     * Qname : 怎么睡个好觉
     * Qid : 123
     * Qtime : 2017.03.03
     */

    private String Qname;
    private String Qid;
    private String Qtime;

    public String getQname() {
        return Qname;
    }

    public void setQname(String Qname) {
        this.Qname = Qname;
    }

    public String getQid() {
        return Qid;
    }

    public void setQid(String Qid) {
        this.Qid = Qid;
    }

    public String getQtime() {
        return Qtime;
    }

    public void setQtime(String Qtime) {
        this.Qtime = Qtime;
    }
}
