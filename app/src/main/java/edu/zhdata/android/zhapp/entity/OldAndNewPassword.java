package edu.zhdata.android.zhapp.entity;

/**
 * Created by ck on 2017/3/12.
 */

public class OldAndNewPassword {

    /**
     * oldPassword : 123456
     * newPassword : 654321
     */

    private String oldPassword;
    private String newPassword;

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
