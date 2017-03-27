package edu.zhdata.android.zhapp.entity;

import java.sql.Date;

/**
 * Created by pink2 on 2017/2/27.
 */
public class User {
    /**
     * Sid : 163680
     * password : 654321
     * major : 1
     * college : 2
     * safequestion1 : 0
     * safequestion2 : 0
     * image : image.png
     * phonenum : 654321
     * Eaddress : 987654@qq.com
     * sex : 1
     * birthday : 一月 1, 1900
     * Sname : 张三
     */

    private String Sid;
    private String password;
    private int major;
    private int college;
    private int safequestion1;
    private int safequestion2;
    private String safekey1;
    private String safekey2;
    private String phonenum;
    private String Eaddress;
    private int sex;
    private Date birthday;

    public String getSafekey1() {
        return safekey1;
    }

    public void setSafekey1(String safekey1) {
        this.safekey1 = safekey1;
    }

    public String getSafekey2() {
        return safekey2;
    }

    public void setSafekey2(String safekey2) {
        this.safekey2 = safekey2;
    }

    private String Sname;
    private String image;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSid() {
        return Sid;
    }

    public void setSid(String Sid) {
        this.Sid = Sid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getMajor() {
        return major;
    }

    public void setMajor(int major) {
        this.major = major;
    }

    public int getCollege() {
        return college;
    }

    public void setCollege(int college) {
        this.college = college;
    }

    public int getSafequestion1() {
        return safequestion1;
    }

    public void setSafequestion1(int safequestion1) {
        this.safequestion1 = safequestion1;
    }

    public int getSafequestion2() {
        return safequestion2;
    }

    public void setSafequestion2(int safequestion2) {
        this.safequestion2 = safequestion2;
    }

    public String getPhonenum() {
        return phonenum;
    }

    public void setPhonenum(String phonenum) {
        this.phonenum = phonenum;
    }

    public String getEaddress() {
        return Eaddress;
    }

    public void setEaddress(String Eaddress) {
        this.Eaddress = Eaddress;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getSname() {
        return Sname;
    }

    public void setSname(String Sname) {
        this.Sname = Sname;
    }
}
