package edu.zhdata.android.zhapp.entity;

/**
 * Created by ck on 2017/3/12.
 */

public class Encrypted {

    /**
     * safequestion1 : 你家住在哪儿
     * safekey1 : 苏州
     * safequestion2 : 你本科学校在那儿
     * safekey2 : 南京
     */

    private String safequestion1;
    private String safekey1;
    private String safequestion2;
    private String safekey2;

    public String getSafequestion1() {
        return safequestion1;
    }

    public void setSafequestion1(String safequestion1) {
        this.safequestion1 = safequestion1;
    }

    public String getSafekey1() {
        return safekey1;
    }

    public void setSafekey1(String safekey1) {
        this.safekey1 = safekey1;
    }

    public String getSafequestion2() {
        return safequestion2;
    }

    public void setSafequestion2(String safequestion2) {
        this.safequestion2 = safequestion2;
    }

    public String getSafekey2() {
        return safekey2;
    }

    public void setSafekey2(String safekey2) {
        this.safekey2 = safekey2;
    }
}
