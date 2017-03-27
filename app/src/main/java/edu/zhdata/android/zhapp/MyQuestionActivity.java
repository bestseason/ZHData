package edu.zhdata.android.zhapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.zhdata.android.zhapp.entity.GetMyQuestion;
import edu.zhdata.android.zhapp.tools.BaseNetwork1;

public class MyQuestionActivity extends Activity {

    Handler handler=new Handler(){
      public void handleMessage(Message message){
          if(message.what==1){
              SimpleAdapter sa=new SimpleAdapter(MyQuestionActivity.this,getDatalist,R.layout.activity_my_answer_listview,new String[]{"title","time"},new int[]{R.id.activity_my_answer_listview_tx1,R.id.activity_my_answer_listview_tx2});
              lv.setAdapter(sa);
              if (sid.equals("-1")){
                  linearLayout.setBackgroundResource(R.drawable.empty);
              }
          }else if(message.what==2){
              linearLayout.setBackgroundResource(R.drawable.empty);
              Toast.makeText(MyQuestionActivity.this,"请检查网络连接",Toast.LENGTH_SHORT).show();
          }else if(message.what==3){
              linearLayout.setBackgroundResource(R.drawable.empty);
          }
      }
    };
    List<GetMyQuestion> list;
    private ImageView imageView;
    private ListView lv;
    private List<HashMap<String,Object>> datalist;
    private HashMap<String,Object> map;
    private String sid="163680";
    private List<HashMap<String,Object>> getDatalist;
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_question);

        linearLayout=(LinearLayout)findViewById(R.id.activity_my_question_bg);

        lv=(ListView)findViewById(R.id.activity_my_question_list);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(MyQuestionActivity.this,Question_page_Activity.class);
                intent.putExtra("Qid",list.get(position).getQid());
                startActivity(intent);
            }
        });

        Intent intent=getIntent();
        sid=intent.getStringExtra("Sid");
        boolean is=intent.getBooleanExtra("isme",true);
        if(is==false){
            TextView tx=(TextView)findViewById(R.id.activity_my_question_tx);
            tx.setText("他的提问");
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    BaseNetwork1 baseNetwork1 = new BaseNetwork1();
                    list = baseNetwork1.getQuestionList(sid);
                    getDatalist = new ArrayList<HashMap<String, Object>>();
                    HashMap<String, Object> map;
                    for (int i = 0; i < list.size(); i++) {
                        map = new HashMap<String, Object>();
                        map.put("title", list.get(i).getQname());
                        map.put("time", list.get(i).getQtime());
                        getDatalist.add(map);
                        Message message = new Message();
                        message.what = 1;
                        handler.sendMessage(message);
                    }
                    if (list.size()==0) {
                        Message message = new Message();
                        message.what = 3;
                        handler.sendMessage(message);
                    }
                }catch (NullPointerException e){
                    Message message = new Message();
                    message.what = 2;
                    handler.sendMessage(message);
                }
            }
        }).start();

        imageView=(ImageView)findViewById(R.id.activity_my_question_img1);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
