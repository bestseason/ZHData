package edu.zhdata.android.zhapp.entity;

/**
 * Created by ck on 2017/3/9.
 */

public class QAnswer {

    /**
     * Aid : 456
     * Sid : 163737
     * image : head.jpg
     * Sname : coco
     * Adetail : 睡前喝杯热牛奶
     * Atime : 2017.03.03
     * Agod : 22
     * Abad : 2
     */

    private int Aid;
    private String Sid;
    private String image;
    private String Sname;
    private String Adetail;
    private String Atime;
    private int Agood;
    private int Abad;

    public int getAid() {
        return Aid;
    }

    public void setAid(int Aid) {
        this.Aid = Aid;
    }

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

    public String getAdetail() {
        return Adetail;
    }

    public void setAdetail(String Adetail) {
        this.Adetail = Adetail;
    }

    public String getAtime() {
        return Atime;
    }

    public void setAtime(String Atime) {
        this.Atime = Atime;
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
}
