package edu.zhdata.android.zhapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import edu.zhdata.android.zhapp.entity.UserFocusPeople;
import edu.zhdata.android.zhapp.tools.BaseNetwork1;

/**
 * Created by ck on 2017/3/8.
 */

public class My_FocusActivity extends Activity {
    List<UserFocusPeople> list;
    Handler handler=new Handler(){
        public void handleMessage(Message message){
            if(message.what==1) {
                ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(My_FocusActivity.this,R.layout.support_simple_spinner_dropdown_item,datalist);
                listView.setAdapter(arrayAdapter);
                if (sid.equals("-1")){
                    linearLayout.setBackgroundResource(R.drawable.empty);
                }
            }else if(message.what==2){
                linearLayout.setBackgroundResource(R.drawable.empty);
                Toast.makeText(My_FocusActivity.this,"请检查网络连接",Toast.LENGTH_SHORT).show();
            }
        }
    };

    private ListView listView;
    private List<String> datalist;
    private ImageView iv;
    private String sid;
    private LinearLayout linearLayout;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_focus);

        linearLayout=(LinearLayout)findViewById(R.id.my_focus_bg);

        listView=(ListView)findViewById(R.id.my_focus_list);
        iv=(ImageView)findViewById(R.id.my_focus_back_img);

        Intent intent=getIntent();
        sid=intent.getStringExtra("Sid");
        boolean is=intent.getBooleanExtra("isme",true);
        if(is==false){
            TextView tx=(TextView)findViewById(R.id.myFocustextView);
            tx.setText("他关注的人");
        }

        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();;
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    BaseNetwork1 baseNetwork1 = new BaseNetwork1();
                    list = baseNetwork1.getUserFocusPeople(sid);
                    datalist = new ArrayList<String>();
                    for (int i = 0; i < list.size(); i++) {
                        datalist.add(list.get(i).getSname());
                    }
                    Message message = new Message();
                    message.what = 1;
                    handler.sendMessage(message);
                }catch (NullPointerException e){
                    Message message = new Message();
                    message.what = 2;
                    handler.sendMessage(message);
                }
            }
        }).start();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(My_FocusActivity.this,MoreActivity.class);
                intent.putExtra("Isme",false);
                intent.putExtra("user_id",list.get(i).getSid());
                startActivityForResult(intent,1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    BaseNetwork1 baseNetwork1 = new BaseNetwork1();
                    list = baseNetwork1.getUserFocusPeople(sid);
                    datalist = new ArrayList<String>();
                    for (int i = 0; i < list.size(); i++) {
                        datalist.add(list.get(i).getSname());
                    }
                    Message message = new Message();
                    message.what = 1;
                    handler.sendMessage(message);
                }catch (NullPointerException e){
                    Message message = new Message();
                    message.what = 2;
                    handler.sendMessage(message);
                }
            }
        }).start();
    }
}
