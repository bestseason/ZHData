package edu.zhdata.android.zhapp.entity;

/**
 * Created by ck on 2017/3/14.
 */

public class GetVote {

    /**
     * AgoodPersonal : true
     * Agood : 1
     * Abad : 0
     * AbadPersonal : false
     */

    private boolean AgoodPersonal;
    private int Agood;
    private int Abad;
    private boolean AbadPersonal;

    public boolean isAgoodPersonal() {
        return AgoodPersonal;
    }

    public void setAgoodPersonal(boolean AgoodPersonal) {
        this.AgoodPersonal = AgoodPersonal;
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

    public boolean isAbadPersonal() {
        return AbadPersonal;
    }

    public void setAbadPersonal(boolean AbadPersonal) {
        this.AbadPersonal = AbadPersonal;
    }
}
