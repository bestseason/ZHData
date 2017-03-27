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

import edu.zhdata.android.zhapp.entity.PeopleFocusUser;
import edu.zhdata.android.zhapp.tools.BaseNetwork1;

/**
 * Created by ck on 2017/3/8.
 */

public class Focus_MyActivity extends Activity {
    List<PeopleFocusUser> list;

    Handler handler=new Handler(){
        public void handleMessage(Message message){
            if(message.what==1){
                ArrayAdapter<String> array = new ArrayAdapter<String>(Focus_MyActivity.this, R.layout.support_simple_spinner_dropdown_item, getDatalist);
                listView.setAdapter(array);
                if (sid.equals("-1")) {
                    linearLayout.setBackgroundResource(R.drawable.empty);
                }
            }else if(message.what==2){
                linearLayout.setBackgroundResource(R.drawable.empty);
                Toast.makeText(Focus_MyActivity.this,"请检查网络连接",Toast.LENGTH_SHORT).show();
            }
        }
    };

    private ListView listView;
    private List<String> getDatalist;
    private ImageView iv;
    private String sid;
    private LinearLayout linearLayout;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.focus_my);

        linearLayout=(LinearLayout)findViewById(R.id.focus_my_bg);

        listView=(ListView)findViewById(R.id.focus_my_list);

        iv=(ImageView)findViewById(R.id.focus_my_back);

        Intent intent=getIntent();
        sid=intent.getStringExtra("Sid");
        boolean is=intent.getBooleanExtra("isme",true);
        if(is==false){
            TextView tx=(TextView)findViewById(R.id.FocusmytextView);
            tx.setText("关注他的人");
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
                    list = baseNetwork1.getPeopleFocusUser(sid);
                    getDatalist = new ArrayList<String>();
                    for (int i = 0; i < list.size(); i++) {
                        getDatalist.add(list.get(i).getSname());
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
                Intent intent=new Intent(Focus_MyActivity.this,MoreActivity.class);
                intent.putExtra("Isme",false);
                intent.putExtra("user_id",list.get(i).getSid());
                startActivity(intent);
            }
        });
    }
}
