package edu.zhdata.android.zhapp.entity;

/**
 * 问题的实体类
 * Created by king on 17-2-27.
 */
public class Question {
    private Integer Qid;
    private String Qname;
    private String Qdetail;
    private String F_Sid;
    private String Qtime;
    private Integer Tid;
    private String P_Sid;

    public Integer getQid() {
        return Qid;
    }

    public void setQid(Integer qid) {
        Qid = qid;
    }

    public String getQname() {
        return Qname;
    }

    public void setQname(String qname) {
        Qname = qname;
    }

    public String getQdetail() {
        return Qdetail;
    }

    public void setQdetail(String qdetail) {
        Qdetail = qdetail;
    }

    public String getF_Sid() {
        return F_Sid;
    }

    public void setF_Sid(String f_Sid) {
        F_Sid = f_Sid;
    }

    public String getQtime() {
        return Qtime;
    }

    public void setQtime(String qtime) {
        Qtime = qtime;
    }

    public Integer getTid() {
        return Tid;
    }

    public void setTid(Integer tid) {
        Tid = tid;
    }

    public String getP_Sid() {
        return P_Sid;
    }

    public void setP_Sid(String p_Sid) {
        P_Sid = p_Sid;
    }

    /**
     * 所有问题的列表
     * @param qid
     * @param qname
     * @param qdetail
     * @param f_Sid
     * @param qtime
     * @param tid
     * @param p_Sid
     */

    public Question(Integer qid, String qname, String qdetail, String f_Sid, String qtime, Integer tid, String p_Sid) {
        Qid = qid;
        Qname = qname;
        Qdetail = qdetail;
        F_Sid = f_Sid;
        Qtime = qtime;
        Tid = tid;
        P_Sid = p_Sid;
    }


    /**
     * 增加关注的问题
     * @param qname
     * @param qdetail
     * @param f_Sid
     * @param qtime
     * @param tid
     */
    public Question(String qname, String qdetail, String f_Sid, String qtime, Integer tid) {
        Qname = qname;
        Qdetail = qdetail;
        F_Sid = f_Sid;
        Qtime = qtime;
        Tid = tid;
    }

    /**
     * 增加提交的问题
     * @param qname
     * @param qdetail
     * @param qtime
     * @param tid
     * @param p_Sid
     */
    public Question(String qname, String qdetail, String qtime, Integer tid, String p_Sid) {
        Qname = qname;
        Qdetail = qdetail;
        Qtime = qtime;
        Tid = tid;
        P_Sid = p_Sid;
    }

    /**
     * 返回关注问题列表
     * @param qid
     * @param qname
     * @param qdetail
     * @param f_Sid
     * @param qtime
     * @param tid
     */
    public Question(Integer qid, String qname, String qdetail, String f_Sid, String qtime, Integer tid) {
        Qid = qid;
        Qname = qname;
        Qdetail = qdetail;
        F_Sid = f_Sid;
        Qtime = qtime;
        Tid = tid;
    }

    /**
     * 返回提交问题列表
     * @param qid
     * @param qname
     * @param qdetail
     * @param qtime
     * @param tid
     * @param p_Sid
     */
    public Question(Integer qid, String qname, String qdetail, String qtime, Integer tid, String p_Sid) {
        Qid = qid;
        Qname = qname;
        Qdetail = qdetail;
        Qtime = qtime;
        Tid = tid;
        P_Sid = p_Sid;
    }

    public Question() {
        super();
    }
}
