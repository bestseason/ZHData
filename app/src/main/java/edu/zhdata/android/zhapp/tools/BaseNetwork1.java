package edu.zhdata.android.zhapp.tools;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.zhdata.android.zhapp.entity.GetMyAnswer;
import edu.zhdata.android.zhapp.entity.GetMyQuestion;
import edu.zhdata.android.zhapp.entity.GetVote;
import edu.zhdata.android.zhapp.entity.MainList;
import edu.zhdata.android.zhapp.entity.MyFocusQuestion;
import edu.zhdata.android.zhapp.entity.MyFocusTopic;
import edu.zhdata.android.zhapp.entity.MyTopic;
import edu.zhdata.android.zhapp.entity.PeopleFocusUser;
import edu.zhdata.android.zhapp.entity.QAnswer;
import edu.zhdata.android.zhapp.entity.TQuestion;
import edu.zhdata.android.zhapp.entity.TopicHotQlist;
import edu.zhdata.android.zhapp.entity.TopicQlist;
import edu.zhdata.android.zhapp.entity.TopicWaitQlist;
import edu.zhdata.android.zhapp.entity.User;
import edu.zhdata.android.zhapp.entity.UserFocusPeople;

/**
 * Created by ck on 2017/3/9.
 */

public class BaseNetwork1 {
    private static String IP_ADDRESS="http://182.254.216.178:8080/";
//    private static String IP_ADDRESS="http://192.168.137.1:8080/";
    private String connectNetworkByPost(String method,String json){
        String result="";
        try {
            // 请求的地址
            String spec = IP_ADDRESS+method;
            // 根据地址创建URL对象
            URL url = new URL(spec);
            // 根据URL对象打开链接
            HttpURLConnection urlConnection = (HttpURLConnection) url
                    .openConnection();
            // 设置请求的方式
            urlConnection.setRequestMethod("POST");
            // 设置请求的超时时间
            urlConnection.setReadTimeout(25000);
            urlConnection.setConnectTimeout(25000);
            // 传递的数据
//            String data = "{\n" +
//                    "            \"user_id\": \"163680\"\n" +
//                    "        }";
            String data=json;
            // 设置请求的头
            urlConnection.setRequestProperty("Accept", "application/json");
            // 设置请求的头
            urlConnection.setRequestProperty("Content-Type",
                    "application/json");

            urlConnection.setDoOutput(true); // 发送POST请求必须设置允许输出
            urlConnection.setDoInput(true); // 发送POST请求必须设置允许输入
            //setDoInput的默认值就是true
            //获取输出流
            OutputStream os = urlConnection.getOutputStream();
            os.write(data.getBytes());
            os.flush();
            if (urlConnection.getResponseCode() == 200) {
                // 获取响应的输入流对象
                InputStream is = urlConnection.getInputStream();
                // 创建字节输出流对象
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                // 定义读取的长度
                int len = 0;
                // 定义缓冲区
                byte buffer[] = new byte[1024];
                // 按照缓冲区的大小，循环读取
                while ((len = is.read(buffer)) != -1) {
                    // 根据读取的长度写入到os对象中
                    baos.write(buffer, 0, len);
                }
                // 释放资源
                os.close();
                is.close();
                baos.close();
                // 返回字符串
                result = new String(baos.toByteArray());
                System.out.println(result);

            } else {
                System.out.println(urlConnection.getResponseCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;

    }

    public List<MyTopic> selectTidTnameByQid(int qid){
        List<MyTopic> list;
        JSONObject jo=new JSONObject();
        try {
            jo.put("Qid",qid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String data=connectNetworkByPost("AselectTidTnameByQid.do",jo.toString());
        Type type = new TypeToken<ArrayList<MyTopic>>() {}.getType();
        Gson gson=new Gson();
        List<MyTopic> myTopic=new ArrayList<MyTopic>();
        myTopic=gson.fromJson(data,type);
        return myTopic;
    }//问题页面获取Tid,Tname
    public List<TQuestion> selectQuestionByQid(int qid){
        List<TQuestion> list;
        JSONObject jo=new JSONObject();
        try {
            jo.put("Qid",qid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String data=connectNetworkByPost("AselectQuestionByQid.do",jo.toString());
        Type type = new TypeToken<ArrayList<TQuestion>>() {}.getType();
        Gson gson=new Gson();
        List<TQuestion> tQuestions =new ArrayList<TQuestion>();
        tQuestions =gson.fromJson(data,type);
        return tQuestions;
    }//问题页面获取Qid,Qname,Qdetail
    public List<QAnswer> selectAnswerByQid(int qid){
        List<QAnswer> list;
        JSONObject jo=new JSONObject();
        try {
            jo.put("Qid",qid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String data=connectNetworkByPost("AselectAnswerByQid.do",jo.toString());
        Type type = new TypeToken<ArrayList<QAnswer>>() {}.getType();
        Gson gson=new Gson();
        List<QAnswer> qAnswers =new ArrayList<QAnswer>();
        qAnswers =gson.fromJson(data,type);
        return qAnswers;
    }//问题页面获取Aid,Sid,image,Sname,Adetail,Atime,Agood,Abad

    public List<GetMyAnswer> getAnswerList(String sid){
        List<GetMyAnswer> list;
        JSONObject jo=new JSONObject();
        try {
            jo.put("Sid",sid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String data=connectNetworkByPost("AgetAnswerList.do",jo.toString());
        Type type=new TypeToken<ArrayList<GetMyAnswer>>(){}.getType();
        Gson gson=new Gson();
        List<GetMyAnswer> getMyAnswers=new ArrayList<GetMyAnswer>();
        getMyAnswers=gson.fromJson(data,type);
        return getMyAnswers;
    }//更多页面我的回答获取Tname,Tid,Qname,Qid,Sid,image,Sname,major,Adetail
    public List<GetMyQuestion> getQuestionList(String sid){
        List<GetMyAnswer> list;
        JSONObject jo=new JSONObject();
        try {
            jo.put("Sid",sid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String data=connectNetworkByPost("AgetQuestionList.do",jo.toString());
        Type type=new TypeToken<ArrayList<GetMyQuestion>>(){}.getType();
        Gson gson=new Gson();
        List<GetMyQuestion> getMyQuestions=new ArrayList<GetMyQuestion>();
        getMyQuestions=gson.fromJson(data,type);
        return getMyQuestions;
    }//更多页面我的问题获取Qname,Qid,Qtime
    public List<MyFocusQuestion> getMyFocusQuestion(String sid){
        List<GetMyAnswer> list;
        JSONObject jo=new JSONObject();
        try {
            jo.put("Sid",sid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String data=connectNetworkByPost("AselectQidQnameBySid.do",jo.toString());
        Type type=new TypeToken<ArrayList<MyFocusQuestion>>(){}.getType();
        Gson gson=new Gson();
        List<MyFocusQuestion> myFocusQuestions=new ArrayList<MyFocusQuestion>();
        myFocusQuestions=gson.fromJson(data,type);
        return myFocusQuestions;
    }//更多页面我的关注中关注的问题获取Qname,Qid
    public List<MyFocusTopic> getMyFocusTopic(String sid){
        List<GetMyAnswer> list;
        JSONObject jo=new JSONObject();
        try {
            jo.put("Sid",sid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String data=connectNetworkByPost("AselectTidTnameBySid.do",jo.toString());
        Type type=new TypeToken<ArrayList<MyFocusTopic>>(){}.getType();
        Gson gson=new Gson();
        List<MyFocusTopic> myFocusTopics=new ArrayList<MyFocusTopic>();
        myFocusTopics=gson.fromJson(data,type);
        return myFocusTopics;
    }//更多页面我的关注中关注的话题获取Tname,Tid
    public List<UserFocusPeople> getUserFocusPeople(String sid){
        List<UserFocusPeople> list;
        JSONObject jo=new JSONObject();
        try {
            jo.put("Sid",sid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String data=connectNetworkByPost("AgetUserFocusList.do",jo.toString());
        Type type=new TypeToken<ArrayList<UserFocusPeople>>(){}.getType();
        Gson gson=new Gson();
        List<UserFocusPeople> userFocusPeoples=new ArrayList<UserFocusPeople>();
        userFocusPeoples=gson.fromJson(data,type);
        return userFocusPeoples;
    }
    public List<PeopleFocusUser> getPeopleFocusUser(String sid){
        List<PeopleFocusUser> list;
        JSONObject jo=new JSONObject();
        try {
            jo.put("Sid",sid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String data=connectNetworkByPost("AgetFocusUserList.do",jo.toString());
        Type type=new TypeToken<ArrayList<PeopleFocusUser>>(){}.getType();
        Gson gson=new Gson();
        List<PeopleFocusUser> peopleFocusUsers=new ArrayList<PeopleFocusUser>();
        peopleFocusUsers=gson.fromJson(data,type);
        return peopleFocusUsers;
    }
    public List<User> getUserInformation(String sid){
        List<PeopleFocusUser> list;
        JSONObject jo=new JSONObject();
        try {
            jo.put("Sid",sid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String data=connectNetworkByPost("AgetUserInformation.do",jo.toString());
        Type type=new TypeToken<ArrayList<User>>(){}.getType();
        Gson gson=new Gson();
        List<User> users=new ArrayList<User>();
        users=gson.fromJson(data,type);
        return users;
    }

    public List<TopicQlist> getTopicQlist(int tid){
        JSONObject jo=new JSONObject();
        try {
            jo.put("Tid",tid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String data=connectNetworkByPost("AsimpleSelectQuestionByTopic.do",jo.toString());
        Type type=new TypeToken<ArrayList<TopicQlist>>(){}.getType();
        Gson gson=new Gson();
        List<TopicQlist> topicQlists=new ArrayList<TopicQlist>();
        topicQlists=gson.fromJson(data,type);
        return topicQlists;
    }
    public List<TopicHotQlist> getTopicHotQlist(String sid,int tid){
        JSONObject jo=new JSONObject();
        try {
            jo.put("Sid",sid);
            jo.put("Tid",tid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String data=connectNetworkByPost("AdynamicHotQuestion.do",jo.toString());
        Type type=new TypeToken<ArrayList<TopicHotQlist>>(){}.getType();
        Gson gson=new Gson();
        List<TopicHotQlist> topicHotQlists=new ArrayList<TopicHotQlist>();
        topicHotQlists=gson.fromJson(data,type);
        return topicHotQlists;
    }
    public List<TopicWaitQlist> getTopicWaitQlist(int tid){
        JSONObject jo=new JSONObject();
        try {
            jo.put("Tid",tid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String data=connectNetworkByPost("Await2answer.do",jo.toString());
        Type type=new TypeToken<ArrayList<TopicWaitQlist>>(){}.getType();
        Gson gson=new Gson();
        List<TopicWaitQlist> topicWaitQlists=new ArrayList<TopicWaitQlist>();
        topicWaitQlists=gson.fromJson(data,type);
        return topicWaitQlists;
    }

    public boolean addAnswer(String Sid,int Qid,String Adetail,Date Atime){
        JSONObject jo=new JSONObject();
        try {
            jo.put("Sid",Sid);
            jo.put("Qid",Qid);
            jo.put("Adetail",Adetail);
            jo.put("Atime",Atime);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String data=connectNetworkByPost("AaddAnswer.do",jo.toString());
        Type type=new TypeToken<Boolean>(){}.getType();
        Gson gson=new Gson();

        if(data.equals("true")){
            return true;
        }else {
         return false;
        }
    }
    public boolean focusQuestion(int qid,String sid){
        JSONObject jo=new JSONObject();
        try {
            jo.put("Sid",sid);
            jo.put("Qid",qid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String data=connectNetworkByPost("AfocusQuestion.do",jo.toString());
        Type type=new TypeToken<Boolean>(){}.getType();
        Gson gson=new Gson();

        if(data.equals("true")){
            return true;
        }else {
            return false;
        }
    }
    public boolean undofocusQuestion(int qid,String sid){
        JSONObject jo=new JSONObject();
        try {
            jo.put("Sid",sid);
            jo.put("Qid",qid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String data=connectNetworkByPost("AundoFocusQuestion.do",jo.toString());
        Type type=new TypeToken<Boolean>(){}.getType();
        Gson gson=new Gson();

        if(data.equals("true")){
            return true;
        }else {
            return false;
        }
    }

    public boolean Login(String sid,String pwd){
        JSONObject jo=new JSONObject();
        try {
            jo.put("Sid",sid);
            jo.put("password",pwd);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String data=connectNetworkByPost("Alogin.do",jo.toString());
        Type type=new TypeToken<Boolean>(){}.getType();
        Gson gson=new Gson();

        if(data.equals("true")){
            return true;
        }else {
            return false;
        }
    }
    public boolean Register(String sname,String sid,String pwd){
        JSONObject jo=new JSONObject();
        try {
            jo.put("Sname",sname);
            jo.put("Sid",sid);
            jo.put("password",pwd);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String data=connectNetworkByPost("Aregister.do",jo.toString());
        Type type=new TypeToken<Boolean>(){}.getType();
        Gson gson=new Gson();

        if(data.equals("true")){
            return true;
        }else {
            return false;
        }
    }

    public List<MainList> userDynamicTopicList(List<Integer> list,String Sid){
        JSONObject jo = new JSONObject();
        try {
            jo.put("topicList",list);
            jo.put("Sid", Sid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String data = connectNetworkByPost("AuserDynamicTopicList.do", jo.toString());
        Type type = new TypeToken<ArrayList<MainList>>() {
        }.getType();
        Gson gson = new Gson();
        List<MainList> mainList= gson.fromJson(data, type);
        return mainList;
    }

    public List<MainList> userDynamicTopicList(List<Integer> list,String Sid,int startrow,int length){
        JSONObject jo = new JSONObject();
        try {
            jo.put("topicList",list);
            jo.put("Sid", Sid);
            jo.put("startRow",startrow);
            jo.put("length",length);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String data = connectNetworkByPost("AuserDynamicTopicListLimited.do", jo.toString());
        Type type = new TypeToken<ArrayList<MainList>>() {
        }.getType();
        Gson gson = new Gson();
        List<MainList> mainList= gson.fromJson(data, type);
        return mainList;
    }

    public boolean addVote(int aid,String sid,int vote){
        JSONObject jo=new JSONObject();
        try {
            jo.put("Sid",sid);
            jo.put("Aid",aid);
            jo.put("vote",vote);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String data=connectNetworkByPost("AaddVote.do",jo.toString());
        Type type=new TypeToken<Boolean>(){}.getType();
        Gson gson=new Gson();

        if(data.equals("true")){
            return true;
        }else {
            return false;
        }
    }

    public boolean deleteVote(int aid,String sid){
        JSONObject jo=new JSONObject();
        try {
            jo.put("Sid",sid);
            jo.put("Aid",aid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String data=connectNetworkByPost("AdeleteVote.do",jo.toString());
        Type type=new TypeToken<Boolean>(){}.getType();
        Gson gson=new Gson();

        if(data.equals("true")){
            return true;
        }else {
            return false;
        }
    }
    public GetVote getVoteState(int aid, String sid){
        JSONObject jo=new JSONObject();
        try {
            jo.put("Aid",aid);
            jo.put("Sid",sid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String data=connectNetworkByPost("AgetVoteState.do",jo.toString());
        Type type=new TypeToken<GetVote>(){}.getType();
        Gson gson=new Gson();
        GetVote map=new GetVote();
        map=gson.fromJson(data,type);
        return map;
    }

    public int addQuestion(String qname, String qdetail, java.sql.Date qtime,List<String> topiclist,String psid){
        JSONObject jo=new JSONObject();
        try {
            jo.put("Qname",qname);
            jo.put("Qdetail",qdetail);
            jo.put("Qtime",qtime);
            jo.put("topicList",topiclist);
            jo.put("P_Sid",psid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String data=connectNetworkByPost("AaddQuestion.do",jo.toString());
        Type type=new TypeToken<Integer>(){}.getType();
        Gson gson=new Gson();

        int qid;
        qid=gson.fromJson(data,type);
        return qid;
    }

    public boolean focusUser(String sid,String F_sid){
        JSONObject jo=new JSONObject();
        try {
            jo.put("Sid",sid);
            jo.put("F_Sid",F_sid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String data=connectNetworkByPost("AfocusUser.do",jo.toString());
        Type type=new TypeToken<Boolean>(){}.getType();
        Gson gson=new Gson();

        if(data.equals("true")){
            return true;
        }else {
            return false;
        }
    }
    public boolean undoFocusUser(String sid,String F_sid){
        JSONObject jo=new JSONObject();
        try {
            jo.put("Sid",sid);
            jo.put("F_Sid",F_sid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String data=connectNetworkByPost("AundoFocusUser.do",jo.toString());
        Type type=new TypeToken<Boolean>(){}.getType();
        Gson gson=new Gson();

        if(data.equals("true")){
            return true;
        }else {
            return false;
        }
    }

    public boolean focusTopic(int tid,String F_sid){
        JSONObject jo=new JSONObject();
        try {
            jo.put("Tid",tid);
            jo.put("F_Sid",F_sid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String data=connectNetworkByPost("AfocusTopic.do",jo.toString());
        Type type=new TypeToken<Boolean>(){}.getType();
        Gson gson=new Gson();

        if(data.equals("true")){
            return true;
        }else {
            return false;
        }
    }
    public boolean undoFocusTopic(int tid,String F_sid){
        JSONObject jo=new JSONObject();
        try {
            jo.put("Tid",tid);
            jo.put("F_Sid",F_sid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String data=connectNetworkByPost("AundoFocusTopic.do",jo.toString());
        Type type=new TypeToken<Boolean>(){}.getType();
        Gson gson=new Gson();

        if(data.equals("true")){
            return true;
        }else {
            return false;
        }
    }


    public boolean isFocusQuestion(int qid,String F_sid){
        JSONObject jo=new JSONObject();
        try {
            jo.put("Qid",qid);
            jo.put("F_Sid",F_sid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String data=connectNetworkByPost("AisFocusQuestion.do",jo.toString());
        Type type=new TypeToken<Boolean>(){}.getType();
        Gson gson=new Gson();

        if(data.equals("true")){
            return true;
        }else {
            return false;
        }
    }
    public boolean isTopicFocus(int tid,String F_sid){
        JSONObject jo=new JSONObject();
        try {
            jo.put("Tid",tid);
            jo.put("F_Sid",F_sid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String data=connectNetworkByPost("AisTopicFocus.do",jo.toString());
        Type type=new TypeToken<Boolean>(){}.getType();
        Gson gson=new Gson();

        if(data.equals("true")){
            return true;
        }else {
            return false;
        }
    }
    public boolean isUserFocus(String sid,String F_sid){
        JSONObject jo=new JSONObject();
        try {
            jo.put("Sid",sid);
            jo.put("F_Sid",F_sid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String data=connectNetworkByPost("AisUserFocus.do",jo.toString());
        Type type=new TypeToken<Boolean>(){}.getType();
        Gson gson=new Gson();

        if(data.equals("true")){
            return true;
        }else {
            return false;
        }
    }

    public boolean modifyUserInformation(String sid, String password, int major, int college, int safequestion1, int safequestion2, String safekey1, String safekey2, String image, String phonenum, String eaddress, int sex, java.sql.Date birthday,String sname){
        JSONObject jo=new JSONObject();
        try {
            jo.put("Sid",sid);
            jo.put("password",password);
            jo.put("major",major);
            jo.put("Sname",sname);
            jo.put("college",college);
            jo.put("safequestion1",safequestion1);
            jo.put("safequestion2",safequestion2);
            jo.put("safekey1",safekey1);
            jo.put("safekey2",safekey2);
            jo.put("image",image);
            jo.put("phonenum",phonenum);
            jo.put("eaddress",eaddress);
            jo.put("sex",sex);
            jo.put("birthday",birthday);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String data=connectNetworkByPost("AmodifyUserInformation.do",jo.toString());
        Type type=new TypeToken<Boolean>(){}.getType();
        Gson gson=new Gson();

        if(data.equals("true")){
            return true;
        }else {
            return false;
        }
    }

//    public boolean securityQuestion(String sid,int safequestion1,int safequestion2,String safekey1,String safekey2){
//        JSONObject jo=new JSONObject();
//        try {
//            jo.put("Sid",sid);
//            jo.put("safequestion1",safequestion1);
//            jo.put("safequestion2",safequestion2);
//            jo.put("safekey1",safekey1);
//            jo.put("safekey2",safekey2);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        String data=connectNetworkByPost("AlostPwd.do",jo.toString());
//        Type type=new TypeToken<Boolean>(){}.getType();
//        Gson gson=new Gson();
//
//        if(data.equals("true")){
//            return true;
//        }else {
//            return false;
//        }
//    }
}

