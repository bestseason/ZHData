package edu.zhdata.android.zhapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.zhdata.android.zhapp.entity.RQuestion;
import edu.zhdata.android.zhapp.tools.BaseNetwork;

/**
 * Created by ck on 2017/3/1.
 */

public class Answer1Activity extends Activity {
    private ImageView iv;
    private ArrayAdapter array;
    private ListView listView;
    private int item_num;
    private ArrayList<String> datalist= new ArrayList<>();
    private List<HashMap<String,Object>> getdatalist;
    private List<RQuestion> list;
    private String Sid;
    private SharedPreferences preferences;
    private LinearLayout linearLayout;

    Handler handler=new Handler(){
        public void handleMessage(Message msg){
            if(msg.what==1){
                array=new ArrayAdapter<String>(Answer1Activity.this,R.layout.support_simple_spinner_dropdown_item,datalist);
                listView.setAdapter(array);
                if (Sid.equals("-1")){
                    linearLayout.setBackgroundResource(R.drawable.empty);
                }
            }else if(msg.what==2){
                linearLayout.setBackgroundResource(R.drawable.empty);
                Toast.makeText(Answer1Activity.this,"请检查网络连接",Toast.LENGTH_SHORT).show();
            }
        }
    };

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.answer1);
        listView=(ListView)findViewById(R.id.answer1_list);
        linearLayout=(LinearLayout)findViewById(R.id.answer1bg);

        Intent intent=getIntent();
        preferences=getSharedPreferences("first_pref",MODE_PRIVATE);
        Sid=preferences.getString("sid"," ");

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    BaseNetwork base = new BaseNetwork();//调用方法从数据库能获取所需的数据
                    list = base.recommendQuestion(Sid);
                    for (int i = 0; i < list.size(); i++) {//从放回的数据中提取自己所需要的数据，并放到datalist
                        datalist.add(list.get(i).getQname());
                    }
                    Message ms = new Message();
                    ms.what = 1;
                    ms.obj = datalist;
                    handler.sendMessage(ms);
                }catch (NullPointerException e){
                    Message message = new Message();
                    message.what = 2;
                    handler.sendMessage(message);
                }
            }
        }).start();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {//点击问题列表中的问题进入回答界面
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(Answer1Activity.this,Answer2Activity.class);
                intent.putExtra("Qid",list.get(i).getQid());
//                intent.putExtra("Sid",Sid);
                startActivity(intent);
            }
        });
    }
}
