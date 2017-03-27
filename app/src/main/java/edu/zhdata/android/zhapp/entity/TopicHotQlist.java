package edu.zhdata.android.zhapp.entity;

/**
 * Created by ck on 2017/3/11.
 */

public class TopicHotQlist {

    /**
     * Qid : Q163671
     * Tlist : {"Tid":"T163671","Tname":"睡觉"}
     * bestAnswer : {"A_id":"A163671","P_id":"P163671","Adetail":"晚上睡觉","Fquestion":"true or false","Atime":"2017-3-3","Agood":"300","Abad":"300"}
     */

    private String Qid;
    private TlistBean Tlist;
    private BestAnswerBean bestAnswer;

    public String getQid() {
        return Qid;
    }

    public void setQid(String Qid) {
        this.Qid = Qid;
    }

    public TlistBean getTlist() {
        return Tlist;
    }

    public void setTlist(TlistBean Tlist) {
        this.Tlist = Tlist;
    }

    public BestAnswerBean getBestAnswer() {
        return bestAnswer;
    }

    public void setBestAnswer(BestAnswerBean bestAnswer) {
        this.bestAnswer = bestAnswer;
    }

    public static class TlistBean {
        /**
         * Tid : T163671
         * Tname : 睡觉
         */

        private String Tid;
        private String Tname;

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

    public static class BestAnswerBean {
        /**
         * A_id : A163671
         * P_id : P163671
         * Adetail : 晚上睡觉
         * Fquestion : true or false
         * Atime : 2017-3-3
         * Agood : 300
         * Abad : 300
         */

        private String A_id;
        private String P_id;
        private String Adetail;
        private String Fquestion;
        private String Atime;
        private String Agood;
        private String Abad;

        public String getA_id() {
            return A_id;
        }

        public void setA_id(String A_id) {
            this.A_id = A_id;
        }

        public String getP_id() {
            return P_id;
        }

        public void setP_id(String P_id) {
            this.P_id = P_id;
        }

        public String getAdetail() {
            return Adetail;
        }

        public void setAdetail(String Adetail) {
            this.Adetail = Adetail;
        }

        public String getFquestion() {
            return Fquestion;
        }

        public void setFquestion(String Fquestion) {
            this.Fquestion = Fquestion;
        }

        public String getAtime() {
            return Atime;
        }

        public void setAtime(String Atime) {
            this.Atime = Atime;
        }

        public String getAgood() {
            return Agood;
        }

        public void setAgood(String Agood) {
            this.Agood = Agood;
        }

        public String getAbad() {
            return Abad;
        }

        public void setAbad(String Abad) {
            this.Abad = Abad;
        }
    }
}
