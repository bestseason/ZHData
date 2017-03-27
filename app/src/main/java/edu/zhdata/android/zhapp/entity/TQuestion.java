package edu.zhdata.android.zhapp.entity;

/**
 * Created by ck on 2017/3/9.
 */

public class TQuestion {

    /**
     * Qid : 11111
     * Qname : 怎么睡个好觉
     * Qdetail : 失眠时怎么睡个好觉
     */

    private String Qid;
    private String Qname;
    private String Qdetail;

    public String getQid() {
        return Qid;
    }

    public void setQid(String Qid) {
        this.Qid = Qid;
    }

    public String getQname() {
        return Qname;
    }

    public void setQname(String Qname) {
        this.Qname = Qname;
    }

    public String getQdetail() {
        return Qdetail;
    }

    public void setQdetail(String Qdetail) {
        this.Qdetail = Qdetail;
    }
}
