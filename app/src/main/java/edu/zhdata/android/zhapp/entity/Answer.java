package edu.zhdata.android.zhapp.entity;

/**
 * 答案的实体类
 * Created by king on 17-2-27.
 */
public class Answer {
    private Integer Aid;
    private String Sid;
    private String Adetail;
    private Integer Agood;
    private Integer Abad;
    private String Atime;
    private Integer Isanonymous;
    private Integer Qid;

    public Integer getAid() {
        return Aid;
    }

    public void setAid(Integer aid) {
        Aid = aid;
    }

    public String getSid() {
        return Sid;
    }

    public void setSid(String sid) {
        Sid = sid;
    }

    public String getAdetail() {
        return Adetail;
    }

    public void setAdetail(String adetail) {
        Adetail = adetail;
    }

    public Integer getAgood() {
        return Agood;
    }

    public void setAgood(Integer agood) {
        Agood = agood;
    }

    public Integer getAbad() {
        return Abad;
    }

    public void setAbad(Integer abad) {
        Abad = abad;
    }

    public String  getAtime() {
        return Atime;
    }

    public void setAtime(String  atime) {
        Atime = atime;
    }

    public Integer getIsanonymous() {
        return Isanonymous;
    }

    public void setIsanonymous(Integer isanonymous) {
        Isanonymous = isanonymous;
    }

    public Integer getQid() {
        return Qid;
    }

    public void setQid(Integer qid) {
        Qid = qid;
    }

    public Answer( String sid, String adetail, Integer agood, Integer abd, String atime, Integer isanonymous, Integer qid) {
        Sid = sid;
        Adetail = adetail;
        Agood = agood;
        Abad = abd;
        Atime = atime;
        Isanonymous = isanonymous;
        Qid = qid;
    }

    public Answer(Integer aid, String sid, String adetail, Integer agood, Integer abad, String atime, Integer isanonymous, Integer qid) {
        Aid = aid;
        Sid = sid;
        Adetail = adetail;
        Agood = agood;
        Abad = abad;
        Atime = atime;
        Isanonymous = isanonymous;
        Qid = qid;
    }

    public Answer(){
        super();
    }
}
