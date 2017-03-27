package edu.zhdata.android.zhapp.entity;

/**
 * Created by ck on 2017/3/9.
 */

public class UserFocusPeople {

    /**
     * Sid : 163737
     * image : head.jpg
     * Sname : coco
     * major : software
     * Tid : 1234
     * Tname : 睡觉
     */

    private String Sid;
    private String image;
    private String Sname;
    private String major;
    private String Tid;
    private String Tname;

    public String getSid() {
        return Sid;
    }

    public void setSid(String Sid) {
        this.Sid = Sid;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSname() {
        return Sname;
    }

    public void setSname(String Sname) {
        this.Sname = Sname;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getTid() {
        return Tid;
    }

    public void setTid(String Tid) {
        this.Tid = Tid;
    }

    public String getTname() {
        return Tname;
    }

    public void setTname(String Tname) {
        this.Tname = Tname;
    }
}
