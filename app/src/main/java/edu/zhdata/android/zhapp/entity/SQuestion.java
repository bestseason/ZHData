package edu.zhdata.android.zhapp.entity;

import java.util.List;

/**
 * Created by pink2 on 2017/3/9.
 */

public class SQuestion {
    /**
     * Tlist : [{"Tname":"睡觉","Tid":6789}]
     * Qdetail : 失眠的时候怎么能睡个好觉
     * F_Qflag : true
     * Qname : 怎么能睡个好觉
     * Alist : [{"Pname":"李四","Agoodflag":false,"Major":2,"Atime":"二月 27, 2017","Abadflag":false,"College_Major":1,"Pid":"163674","Agood":2,"Abad":0,"Aid":1,"Adetail":"晚上不睡觉！"},{"Pname":"王小明","Agoodflag":false,"Major":1,"Atime":"一月 5, 2017","Abadflag":false,"College_Major":2,"Pid":"163678","Agood":1,"Abad":1,"Aid":2,"Adetail":"准时睡觉"}]
     * F_Qnum : 3
     * Qid : 2
     */

    private String Qdetail;
    private boolean F_Qflag;
    private String Qname;
    private int F_Qnum;
    private int Qid;
    private List<TlistBean> Tlist;
    private List<AlistBean> Alist;

    public String getQdetail() {
        return Qdetail;
    }

    public void setQdetail(String Qdetail) {
        this.Qdetail = Qdetail;
    }

    public boolean isF_Qflag() {
        return F_Qflag;
    }

    public void setF_Qflag(boolean F_Qflag) {
        this.F_Qflag = F_Qflag;
    }

    public String getQname() {
        return Qname;
    }

    public void setQname(String Qname) {
        this.Qname = Qname;
    }

    public int getF_Qnum() {
        return F_Qnum;
    }

    public void setF_Qnum(int F_Qnum) {
        this.F_Qnum = F_Qnum;
    }

    public int getQid() {
        return Qid;
    }

    public void setQid(int Qid) {
        this.Qid = Qid;
    }

    public List<TlistBean> getTlist() {
        return Tlist;
    }

    public void setTlist(List<TlistBean> Tlist) {
        this.Tlist = Tlist;
    }

    public List<AlistBean> getAlist() {
        return Alist;
    }

    public void setAlist(List<AlistBean> Alist) {
        this.Alist = Alist;
    }

    public static class TlistBean {
        /**
         * Tname : 睡觉
         * Tid : 6789
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

    public static class AlistBean {
        /**
         * Pname : 李四
         * Agoodflag : false
         * Major : 2
         * Atime : 二月 27, 2017
         * Abadflag : false
         * College_Major : 1
         * Pid : 163674
         * Agood : 2
         * Abad : 0
         * Aid : 1
         * Adetail : 晚上不睡觉！
         */

        private String Pname;
        private boolean Agoodflag;
        private int Major;
        private String Atime;
        private boolean Abadflag;
        private int College;
        private String Pid;
        private int Agood;
        private int Abad;
        private int Aid;
        private String Adetail;

        public String getPname() {
            return Pname;
        }

        public void setPname(String Pname) {
            this.Pname = Pname;
        }

        public boolean isAgoodflag() {
            return Agoodflag;
        }

        public void setAgoodflag(boolean Agoodflag) {
            this.Agoodflag = Agoodflag;
        }

        public int getMajor() {
            return Major;
        }

        public void setMajor(int Major) {
            this.Major = Major;
        }

        public String getAtime() {
            return Atime;
        }

        public void setAtime(String Atime) {
            this.Atime = Atime;
        }

        public boolean isAbadflag() {
            return Abadflag;
        }

        public void setAbadflag(boolean Abadflag) {
            this.Abadflag = Abadflag;
        }

        public int getCollege() {
            return College;
        }

        public void setCollege(int College) {
            this.College = College;
        }

        public String getPid() {
            return Pid;
        }

        public void setPid(String Pid) {
            this.Pid = Pid;
        }

        public int getAgood() {
            return Agood;
        }

        public void setAgood(int Agood) {
            this.Agood = Agood;
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

        public String getAdetail() {
            return Adetail;
        }

        public void setAdetail(String Adetail) {
            this.Adetail = Adetail;
        }
    }
}
