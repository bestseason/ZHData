package edu.zhdata.android.zhapp.entity;

/**
 * Created by ck on 2017/3/20.
 */

public class SecurityQuestion {

    /**
     * Sid : 163680
     * safequestion1 : 1
     * safekey1 : 苏州
     * safequestion2 : 2
     * safekey2 : 南京
     */

    private String Sid;
    private int safequestion1;
    private String safekey1;
    private int safequestion2;
    private String safekey2;

    public String getSid() {
        return Sid;
    }

    public void setSid(String Sid) {
        this.Sid = Sid;
    }

    public int getSafequestion1() {
        return safequestion1;
    }

    public void setSafequestion1(int safequestion1) {
        this.safequestion1 = safequestion1;
    }

    public String getSafekey1() {
        return safekey1;
    }

    public void setSafekey1(String safekey1) {
        this.safekey1 = safekey1;
    }

    public int getSafequestion2() {
        return safequestion2;
    }

    public void setSafequestion2(int safequestion2) {
        this.safequestion2 = safequestion2;
    }

    public String getSafekey2() {
        return safekey2;
    }

    public void setSafekey2(String safekey2) {
        this.safekey2 = safekey2;
    }
}
