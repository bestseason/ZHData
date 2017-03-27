package edu.zhdata.android.zhapp.entity;

import java.util.List;

/**
 * Created by pink2 on 2017/3/10.
 */

public class SUser {
    /**
     * Pname : 张三
     * Major : 1
     * College_Major : 2
     * F_person : true
     * P_id : 163680
     * F_topiclist : [{"Tname":"睡觉","Tid":6789},{"Tname":"睡觉","Tid":6789}]
     */

    private String Pname;
    private int Major;
    private int College;
    private boolean F_person;
    private String P_id;
    private List<FTopiclistBean> F_topiclist;

    public String getPname() {
        return Pname;
    }

    public void setPname(String Pname) {
        this.Pname = Pname;
    }

    public int getMajor() {
        return Major;
    }

    public void setMajor(int Major) {
        this.Major = Major;
    }

    public int getCollege() {
        return College;
    }

    public void setCollege(int College) {
        this.College = College;
    }

    public boolean isF_person() {
        return F_person;
    }

    public void setF_person(boolean F_person) {
        this.F_person = F_person;
    }

    public String getP_id() {
        return P_id;
    }

    public void setP_id(String P_id) {
        this.P_id = P_id;
    }

    public List<FTopiclistBean> getF_topiclist() {
        return F_topiclist;
    }

    public void setF_topiclist(List<FTopiclistBean> F_topiclist) {
        this.F_topiclist = F_topiclist;
    }

    public static class FTopiclistBean {
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
}
