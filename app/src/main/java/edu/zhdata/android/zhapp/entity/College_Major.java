package edu.zhdata.android.zhapp.entity;

import java.util.List;

/**
 * Created by pink2 on 2017/3/20.
 */

public class College_Major {
    /**
     * Cid : 1
     * Cname : 建筑学院
     * majorList : [{"Mid":1,"Mname":"建筑学"},{"Mid":2,"Mname":"城乡规划学"},{"Mid":3,"Mname":"风景园林学"},{"Mid":4,"Mname":"建筑学(专业学位)"},{"Mid":5,"Mname":"城市规划(专业学位)"},{"Mid":6,"Mname":"风景园林(专业学位)"},{"Mid":7,"Mname":"美术学"}]
     * majorNum : 7
     */

    private int Cid;
    private String Cname;
    private int majorNum;
    private List<MajorListBean> majorList;

    public int getCid() {
        return Cid;
    }

    public void setCid(int Cid) {
        this.Cid = Cid;
    }

    public String getCname() {
        return Cname;
    }

    public void setCname(String Cname) {
        this.Cname = Cname;
    }

    public int getMajorNum() {
        return majorNum;
    }

    public void setMajorNum(int majorNum) {
        this.majorNum = majorNum;
    }

    public List<MajorListBean> getMajorList() {
        return majorList;
    }

    public void setMajorList(List<MajorListBean> majorList) {
        this.majorList = majorList;
    }

    public static class MajorListBean {
        /**
         * Mid : 1
         * Mname : 建筑学
         */

        private int Mid;
        private String Mname;

        public int getMid() {
            return Mid;
        }

        public void setMid(int Mid) {
            this.Mid = Mid;
        }

        public String getMname() {
            return Mname;
        }

        public void setMname(String Mname) {
            this.Mname = Mname;
        }
    }
}
