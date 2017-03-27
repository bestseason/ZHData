package edu.zhdata.android.zhapp.tools;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import edu.zhdata.android.zhapp.entity.College_Major;
import edu.zhdata.android.zhapp.entity.MainList;
import edu.zhdata.android.zhapp.entity.MyTopic;
import edu.zhdata.android.zhapp.entity.RQuestion;
import edu.zhdata.android.zhapp.entity.SQuestion;
import edu.zhdata.android.zhapp.entity.STopic;
import edu.zhdata.android.zhapp.entity.SUser;
import edu.zhdata.android.zhapp.entity.User;

import static android.content.ContentValues.TAG;
import static android.provider.Telephony.Mms.Part.CHARSET;

/**
 * Created by pink2 on 2017/3/2.
 */

public class BaseNetwork {
    public static String IP_ADDRESS="http://182.254.216.178:8080/";
//    public static String IP_ADDRESS="http://192.168.137.1:8080/";

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

    public List<MyTopic> selectTidTnameBySid(String Sid){
        JSONObject jo=new JSONObject();
        try {
            jo.put("Sid",Sid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String data= connectNetworkByPost("AselectTidTnameBySid.do",jo.toString());
        Type type = new TypeToken<ArrayList<MyTopic>>() {}.getType();
        Gson gson=new Gson();
        List<MyTopic> myTopic=new ArrayList<MyTopic>();
        myTopic=gson.fromJson(data,type);
        return myTopic;
    }
    public List<MainList> mainpagedataBypost(String Sid,int startRow,int length){
        List<MyTopic> myTopicList=selectTidTnameBySid(Sid);
        Iterator it=myTopicList.iterator();
        List<String> topicList=new ArrayList<>();
        while (it.hasNext()) {
            MyTopic myTopic= (MyTopic) it.next();
            topicList.add(String.valueOf(myTopic.getTid()));
        }
        JSONObject jo = new JSONObject();
        try {
            jo.put("topicList",topicList);
            jo.put("Sid", Sid);
            jo.put("startRow",startRow);
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
    public List<MainList> mainpagedataBypost(String Sid){
        List<MyTopic> myTopicList=selectTidTnameBySid(Sid);
        Iterator it=myTopicList.iterator();
        List<String> topicList=new ArrayList<>();
        while (it.hasNext()) {
            MyTopic myTopic= (MyTopic) it.next();
            topicList.add(String.valueOf(myTopic.getTid()));
        }
        JSONObject jo = new JSONObject();
        try {
            jo.put("topicList",topicList);
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

    public List<MainList> dynamicHotQuestionLimited(String Sid,int Tid,int startRow,int length){
        JSONObject jo = new JSONObject();
        try {
            jo.put("Sid", Sid);
            jo.put("Tid",Tid);
            jo.put("startRow",startRow);
            jo.put("length",length);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String data = connectNetworkByPost("AdynamicHotQuestionLimited.do", jo.toString());
        Type type = new TypeToken<ArrayList<MainList>>() {
        }.getType();
        Gson gson = new Gson();
        List<MainList> mainList= gson.fromJson(data, type);
        return mainList;
    }

public List<MainList> dynamicHotQuestion(String Sid,int Tid){
    JSONObject jo = new JSONObject();
    try {
        jo.put("Sid", Sid);
        jo.put("Tid",Tid);
    } catch (JSONException e) {
        e.printStackTrace();
    }
    String data = connectNetworkByPost("AdynamicHotQuestion.do", jo.toString());
    Type type = new TypeToken<ArrayList<MainList>>() {
    }.getType();
    Gson gson = new Gson();
    List<MainList> mainList= gson.fromJson(data, type);
    return mainList;
}

    public List<RQuestion> recommendQuestion(String Sid){
        JSONObject jo = new JSONObject();
        try {
            jo.put("Sid", Sid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String data = connectNetworkByPost("ArecommendQuestion.do", jo.toString());
        Type type = new TypeToken<ArrayList<RQuestion>>() {
        }.getType();
        Gson gson = new Gson();
        List<RQuestion> questionList= gson.fromJson(data, type);
        return questionList;
    }

    public List<User> getUserInformation(String Sid){
        JSONObject jo = new JSONObject();
        try {
            jo.put("Sid", Sid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String data = connectNetworkByPost("AgetUserInformation.do", jo.toString());
        Type type = new TypeToken<ArrayList<User>>() {
        }.getType();
        Gson gson = new Gson();
        List<User> user= gson.fromJson(data, type);
        return user;
    }

    public List<SQuestion> searchQuestion(String keyword, String Sid){
        JSONObject jo = new JSONObject();
        try {
            jo.put("keyword",keyword);
            jo.put("Sid", Sid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String data = connectNetworkByPost("AsearchQuestion.do", jo.toString());
        Type type = new TypeToken<ArrayList<SQuestion>>() {
        }.getType();
        Gson gson = new Gson();
        List<SQuestion> qlist= gson.fromJson(data, type);
        return qlist;
    }

    public List<STopic> searchTopic(String keyword, String Sid){
        JSONObject jo = new JSONObject();
        try {
            jo.put("keyword",keyword);
            jo.put("Sid", Sid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String data = connectNetworkByPost("AsearchTopic.do", jo.toString());
        Type type = new TypeToken<ArrayList<STopic>>() {
        }.getType();
        Gson gson = new Gson();
        List<STopic> tlist= gson.fromJson(data, type);
        return tlist;
    }

    public List<SUser> searchUser(String keyword, String Sid){
        JSONObject jo = new JSONObject();
        try {
            jo.put("keyword",keyword);
            jo.put("Sid", Sid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String data = connectNetworkByPost("AsearchUser.do", jo.toString());
        Type type = new TypeToken<ArrayList<SUser>>() {
        }.getType();
        Gson gson = new Gson();
        List<SUser> ulist= gson.fromJson(data, type);
        return ulist;
    }

    public List<MainList> myAnswerListLimited(String Sid,int startRow,int length){
        JSONObject jo = new JSONObject();
        try {
            jo.put("Sid", Sid);
            jo.put("startRow",startRow);
            jo.put("length",length);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String data = connectNetworkByPost("AmyAnswerListLimited.do", jo.toString());
        Type type = new TypeToken<ArrayList<MainList>>() {
        }.getType();
        Gson gson = new Gson();
        List<MainList> mainList= gson.fromJson(data, type);
        return mainList;
    }

    public Bitmap getImage(String address) throws IOException{
        Bitmap bitmap=BitmapFactory.decodeStream(getClass().getResourceAsStream("/res/drawable/modechange.png"));
        InputStream inputStream = null;
        URL url = new URL(IP_ADDRESS.substring(0,IP_ADDRESS.length()-1)+address);                    //服务器地址
        if (url != null) {
            //打开连接
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setConnectTimeout(25000);//设置网络连接超时的时间为25秒
            httpURLConnection.setRequestMethod("GET");        //设置请求方法为GET
            // 设置请求的头
            httpURLConnection.setRequestProperty("Accept", "application/json");
            // 设置请求的头
            httpURLConnection.setRequestProperty("Content-Type",
                    "application/json");
            httpURLConnection.setDoInput(true);                //打开输入流
            int responseCode = httpURLConnection.getResponseCode();    // 获取服务器响应值
            if (responseCode == HttpURLConnection.HTTP_OK) {        //正常连接
                inputStream = httpURLConnection.getInputStream();        //获取输入流
                bitmap = BitmapFactory.decodeStream(inputStream);
            }
        }
        return bitmap;
    }



    public static List<College_Major> getCollege_Major(Context context){
        String jsonString="";
        String resultString="";
        List<College_Major> college_major;
        try {
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(
                    context.getResources().getAssets().open("c2m.json")));
            while ((jsonString=bufferedReader.readLine())!=null) {
                resultString+=jsonString;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Type type = new TypeToken<ArrayList<College_Major>>() {
        }.getType();
        Gson gson = new Gson();
        college_major=gson.fromJson(resultString,type);
        return college_major;
    }

    public String uploadFile(String method,Context context,Bitmap bitmap,String fileName,String Sid){
        int res=0;
        String BOUNDARY = UUID.randomUUID().toString(); // 边界标识 随机生成
        String PREFIX = "--", LINE_END = "\r\n";
        String CONTENT_TYPE = "multipart/form-data"; // 内容类型

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
            // 设置请求的头
            urlConnection.setRequestProperty("Connection", "Keep-Alive");
            // 设置请求的头
            urlConnection.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary="+ BOUNDARY);

            urlConnection.setDoOutput(true); // 发送POST请求必须设置允许输出
            urlConnection.setDoInput(true); // 发送POST请求必须设置允许输入
            //setDoInput的默认值就是true
            if (true) {
                /**
                 * 当文件不为空时执行上传
                 */
                DataOutputStream dos = new DataOutputStream(urlConnection.getOutputStream());
                StringBuffer sb = new StringBuffer();
                sb.append(PREFIX);
                sb.append(BOUNDARY);
                sb.append(LINE_END);
                /**
                 * 这里重点注意： name里面的值为服务器端需要key 只有这个key 才可以得到对应的文件
                 * filename是文件的名字，包含后缀名
                 */
                String myFileName=fileName.replaceAll("file:///..+/",Sid+"-user-");
                sb.append("Content-Disposition: form-data; name=\"file\"; filename=\""
                        + myFileName + "\"" + LINE_END);
                sb.append("Content-Type: application/octet-stream; charset="
                        + CHARSET + LINE_END);
                sb.append(LINE_END);
                dos.write(sb.toString().getBytes());

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                InputStream is = new ByteArrayInputStream(baos.toByteArray());
                //InputStream is = context.getResources().getAssets().open("iflytek/voice_full.png");
                byte[] bytes = new byte[1024];
                int len = 0;
                while ((len = is.read(bytes)) != -1) {
                    dos.write(bytes, 0, len);
                }
                is.close();
                dos.write(LINE_END.getBytes());
                byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINE_END)
                        .getBytes();
                dos.write(end_data);
                dos.flush();
                /**
                 * 获取响应码 200=成功 当响应成功，获取响应的流
                 */
                res = urlConnection.getResponseCode();
                Log.e(TAG, "response code:" + res);
                if (res == 200) {
                    Log.e(TAG, "request success");
                    InputStream input = urlConnection.getInputStream();
                    StringBuffer sb1 = new StringBuffer();
                    int ss;
                    while ((ss = input.read()) != -1) {
                        sb1.append((char) ss);
                    }
                    result = sb1.toString();
                    Log.e(TAG, "result : " + result);
                } else {
                    Log.e(TAG, "request error");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;

    }

    public boolean modifyImage(String Sid,String filePath){
        JSONObject jo=new JSONObject();
        try {
            jo.put("Sid",Sid);
            jo.put("filePath",filePath);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String data=connectNetworkByPost("AmodifyImage.do",jo.toString());
        if(data.equals("true")){
            return true;
        }else {
            return false;
        }
    }

//    public boolean firstadddataBypost(ArrayList<String> arrayList){
//        try {
//
//            // 请求的地址
//            String spec = "http://10.6.12.195:8085/getStudent.do";
//            // 根据地址创建URL对象
//            URL url = new URL(spec);
//            // 根据URL对象打开链接
//            HttpURLConnection urlConnection = (HttpURLConnection) url
//                    .openConnection();
//            // 设置请求的方式
//            urlConnection.setRequestMethod("POST");
//            // 设置请求的超时时间
//            urlConnection.setReadTimeout(5000);
//            urlConnection.setConnectTimeout(5000);
//            // 传递的数据
//            String arraydata="{\"Tlist\":";
//            for(int i=0;i<arrayList.size();i++){
//                if(i==(arrayList.size()-1)){
//                    arraydata=arraydata+"{\"Tname\":arrayList.get(i)}"+"}";
//                }
//                arraydata=arraydata+"{\"Tname\":arrayList.get(i)}"+",";
//            }
//            // 设置请求的头
//            urlConnection.setRequestProperty("Accept", "application/json");
//            // 设置请求的头
//            urlConnection.setRequestProperty("Content-Type",
//                    "application/json");
//
//            urlConnection.setDoOutput(true); // 发送POST请求必须设置允许输出
//            urlConnection.setDoInput(true); // 发送POST请求必须设置允许输入
//            //setDoInput的默认值就是true
//            //获取输出流
//            OutputStream os = urlConnection.getOutputStream();
//            os.write(arraydata.getBytes());
//            os.flush();
//            if (urlConnection.getResponseCode() == 200) {
//                return true;
//
//            } else {
//                System.out.println(urlConnection.getResponseCode());
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return false;
//    }


}
