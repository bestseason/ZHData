package edu.zhdata.android.zhapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class FlashActivity extends Activity {

    private Boolean isFirstIn = false;
    private int FLSH_DISPLAY_LENGTH=2000;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash);

        SharedPreferences preferences=getSharedPreferences("first_pref",MODE_PRIVATE);//创建一个first_pref文件
        editor=preferences.edit();

        isFirstIn=preferences.getBoolean("isFirstIn",true);

        final ArrayList<String> arrayList=new ArrayList<String>();

        SharedPreferences gettopic=getSharedPreferences("Topic",MODE_PRIVATE);//打开Topic文件
        Set set=new HashSet();
        set=gettopic.getStringSet("topic",set);
        Iterator it=set.iterator();
        while(it.hasNext()) {//在hashset中逐个获取数据放到arraylist里
            arrayList.add((String) it.next());
        }




        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(isFirstIn){//进入话题选择界面
                    Intent intent=new Intent(FlashActivity.this,ChooseTopicActivity.class);
                    startActivity(intent);
                    FlashActivity.this.finish();
                }
                else {//进入主界面并传送数据
                    Intent intent=new Intent(FlashActivity.this,MainActivity.class);
                    Bundle b=new Bundle();
                    b.putStringArrayList("arraylist",arrayList);
                    intent.putExtras(b);
                    startActivity(intent);
                    FlashActivity.this.finish();
                }
            }
        },FLSH_DISPLAY_LENGTH);
        editor.commit();
    }
}
