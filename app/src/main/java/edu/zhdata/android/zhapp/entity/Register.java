package edu.zhdata.android.zhapp.entity;

/**
 * Created by ck on 2017/3/12.
 */

public class Register {

    /**
     * Sname : 王小明
     * Sid : 163678
     * password : 123456
     */

    private String Sname;
    private String Sid;
    private String password;

    public String getSname() {
        return Sname;
    }

    public void setSname(String Sname) {
        this.Sname = Sname;
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
}
