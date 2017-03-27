package edu.zhdata.android.zhapp.entity;

/**
 * Created by pink2 on 2017/3/10.
 */

public class STopic {
    /**
     * Tintroduce : 睡觉很重要
     * Tname : 睡觉
     * Tid : 6789
     * F_TopicFlag : false
     */

    private String Tintroduce;
    private String Tname;
    private int Tid;
    private boolean F_TopicFlag;

    public String getTintroduce() {
        return Tintroduce;
    }

    public void setTintroduce(String Tintroduce) {
        this.Tintroduce = Tintroduce;
    }

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

    public boolean isF_TopicFlag() {
        return F_TopicFlag;
    }

    public void setF_TopicFlag(boolean F_TopicFlag) {
        this.F_TopicFlag = F_TopicFlag;
    }
}
