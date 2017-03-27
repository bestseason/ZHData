package edu.zhdata.android.zhapp.entity;

import java.util.List;

/**
 * Created by pink2 on 2017/3/8.
 */

public class MainList {
    /**
     * Tlist : [{"Tname":"程序员","Tid":5},{"Tname":"计算机","Tid":6792}]
     * AgoodPersonal : true
     * Atime : 2017-03-08
     * FocusQuestion : false
     * P_Sid : 163678
     * Qid : 7
     * Adetail : 多读书
     * Sid : 163680
     * Qname : 程序员修炼之道
     * P_major : 1
     * A_Sname : 张三
     * Agood : 1
     * P_Sname : 王小明
     * Abad : 0
     * Aid : 5
     * AbadPersonal : false
     */

    private boolean AgoodPersonal;
    private String Atime;
    private boolean FocusQuestion;
    private String P_Sid;
    private int Qid;
    private String Adetail;
    private String Sid;
    private String Qname;
    private int P_major;
    private String A_Sname;
    private int Agood;
    private String P_Sname;
    private int Abad;
    private int Aid;
    private boolean AbadPersonal;
    private List<TlistBean> Tlist;

    public boolean isAgoodPersonal() {
        return AgoodPersonal;
    }

    public void setAgoodPersonal(boolean AgoodPersonal) {
        this.AgoodPersonal = AgoodPersonal;
    }

    public String getAtime() {
        return Atime;
    }

    public void setAtime(String Atime) {
        this.Atime = Atime;
    }

    public boolean isFocusQuestion() {
        return FocusQuestion;
    }

    public void setFocusQuestion(boolean FocusQuestion) {
        this.FocusQuestion = FocusQuestion;
    }

    public String getP_Sid() {
        return P_Sid;
    }

    public void setP_Sid(String P_Sid) {
        this.P_Sid = P_Sid;
    }

    public int getQid() {
        return Qid;
    }

    public void setQid(int Qid) {
        this.Qid = Qid;
    }

    public String getAdetail() {
        return Adetail;
    }

    public void setAdetail(String Adetail) {
        this.Adetail = Adetail;
    }

    public String getSid() {
        return Sid;
    }

    public void setSid(String Sid) {
        this.Sid = Sid;
    }

    public String getQname() {
        return Qname;
    }

    public void setQname(String Qname) {
        this.Qname = Qname;
    }

    public int getP_major() {
        return P_major;
    }

    public void setP_major(int P_major) {
        this.P_major = P_major;
    }

    public String getA_Sname() {
        return A_Sname;
    }

    public void setA_Sname(String A_Sname) {
        this.A_Sname = A_Sname;
    }

    public int getAgood() {
        return Agood;
    }

    public void setAgood(int Agood) {
        this.Agood = Agood;
    }

    public String getP_Sname() {
        return P_Sname;
    }

    public void setP_Sname(String P_Sname) {
        this.P_Sname = P_Sname;
    }

    public int getAbad() {
        return Abad;
    }

    public void setAbad(int Abad) {
        this.Abad = Abad;
    }

    public int getAid() {
        return Aid;
    }

    public void setAid(int Aid) {
        this.Aid = Aid;
    }

    public boolean isAbadPersonal() {
        return AbadPersonal;
    }

    public void setAbadPersonal(boolean AbadPersonal) {
        this.AbadPersonal = AbadPersonal;
    }

    public List<TlistBean> getTlist() {
        return Tlist;
    }

    public void setTlist(List<TlistBean> Tlist) {
        this.Tlist = Tlist;
    }

    public static class TlistBean {
        /**
         * Tname : 程序员
         * Tid : 5
         */

        private String Tname;
        private int Tid;

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
    }
}
