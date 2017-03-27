package edu.zhdata.android.zhapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import edu.zhdata.android.zhapp.tools.BaseNetwork1;

/**
 * Created by ck on 2017/3/1.
 */

public class Information_completeActivity extends Activity {
    private Button btn;
    private TextView[] textViews=new TextView[9];
    private TableRow[] tableRows=new TableRow[3];
    private int j=-1;
    private ArrayList<Integer> list=new ArrayList<>();
    private View.OnClickListener clickListener;
    private SharedPreferences addtopic,preferences;
    SharedPreferences.Editor editor;
    private String[] str=new String[30];
    private String Sid,Sname,password;

    private boolean[] txPressed=new boolean[9];
    private int Drawable[]={R.drawable.building,R.drawable.computer,R.drawable.food,R.drawable.travel,R.drawable.career,R.drawable.game,R.drawable.movie,R.drawable.economic,R.drawable.doctor};
    private String Tname[]={"建筑","计算机科学","美食","旅行","职业发展","游戏","电影","经济管理","医学"};
    private int tid[]={6796,6803,6825,6830,6829,6820,6834,6806,6814};

    Handler handler=new Handler(){
        public void handleMessage(Message msg){
            if(msg.what==1){
                Toast.makeText(Information_completeActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                preferences=getSharedPreferences("first_pref",MODE_PRIVATE);
                editor=preferences.edit();
                editor.putBoolean("isFirstIn",false);
                editor.putString("sid",Sid);
                editor.commit();
                Intent intent = new Intent(Information_completeActivity.this, MainActivity.class);//跳转到主界面并把点击获取的topic发送给主界面
                startActivity(intent);
            }
            else if(msg.what==2){
                Toast.makeText(Information_completeActivity.this, "请至少选择一个话题", Toast.LENGTH_SHORT).show();
            }else if (msg.what==3){
                Toast.makeText(Information_completeActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
            }
        }
    };

    public void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.information_complete);
        ActivityCollector.addActivity(this);

        Intent getintent=getIntent();
        Sid=getintent.getStringExtra("Sid");
        Sname=getintent.getStringExtra("Sname");
        password=getintent.getStringExtra("password");

        tableRows[0] =(TableRow) findViewById(R.id.tablerow1);
        tableRows[1]=(TableRow)findViewById(R.id.tablerow2);
        tableRows[2]=(TableRow)findViewById(R.id.tablerow3);

        clickListener=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v==textViews[0]){
                    setColor(0);
                }else if(v==textViews[1]){
                    setColor(1);
                }else if(v==textViews[2]){
                    setColor(2);
                }else if(v==textViews[3]){
                    setColor(3);
                }else if(v==textViews[4]){
                    setColor(4);
                }else if(v==textViews[5]){
                    setColor(5);
                }else if(v==textViews[6]){
                    setColor(6);
                }else if(v==textViews[7]){
                    setColor(7);
                }else if(v==textViews[8]){
                    setColor(8);
                }
            }
        };

        for(int i=0;i<9;i++){
            if((i)%3==0) {//tablerow一行最多为6了控件
                j++;
            }
            textViews[i]=new TextView(this);
            textViews[i].setText(Tname[i]);
            TableRow.LayoutParams lp=new TableRow.LayoutParams(TableRow.LayoutParams .MATCH_PARENT,TableRow.LayoutParams .MATCH_PARENT);//动态设置textview的外边距
                    //(TableRow.LayoutParams)(textViews[i].getLayoutParams());
            lp.setMargins(60,15,60,15);
            textViews[i].setLayoutParams(lp);
            tableRows[j].addView(textViews[i]);
            txPressed[i]=true;
//            Drawable drawable = ((Resources) getBaseContext().getResources()).getDrawable(Drawable[i]);
//            textViews[i].setBackgroundDrawable(drawable);
            setColor(i);
            textViews[i].setOnClickListener(clickListener);
        }

        addtopic=getSharedPreferences("Topic",MODE_PRIVATE);//将用户开始选择的话题
        SharedPreferences.Editor editor=addtopic.edit();
        Set set=new HashSet();
        for(int i=0;i<list.size();i++){
            set.add(list.get(i));
        }
        editor.putStringSet("topic", set);
        editor.commit();


        btn=(Button)findViewById(R.id.information_complete_btn1);
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        boolean is=false;
                        Message ms=new Message();
                        if(list.size()!=0){
                            BaseNetwork1 baseNetwork1=new BaseNetwork1();
                            for (int i=0;i<list.size();i++) {
                                is = baseNetwork1.focusTopic(list.get(i),Sid);//第一次登录时选择的话题提交到数据库
                                if (is==false){
                                    ms.what=3;
                                    break;
                                }
                            }
                            is=baseNetwork1.Register(Sname,Sid,password);
                            if (is==false){
                                ms.what=3;
                            }
                        }else {
                            ms.what=2;
                        }
                        if(is==true)
                            ms.what = 1;
                        handler.sendMessage(ms);
                    }
                }).start();

            }
        });
    }

    private void setColor(int i){
        if (txPressed[i]==false) {
            Drawable drawable = ((Resources) getBaseContext().getResources()).getDrawable(Drawable[i]);
            txPressed[i]=true;
            list.add(tid[i]);
            drawable.setColorFilter(0xff424242, PorterDuff.Mode.MULTIPLY);
            textViews[i].setBackgroundDrawable(drawable);
        }else {
            txPressed[i]=false;
            for(Integer j:list) {
                if(j==tid[i]) {
                    list.remove(j);
                    break;
                }
            }
            Drawable drawable1 = ((Resources) getBaseContext().getResources()).getDrawable(Drawable[i]);
            drawable1.setColorFilter(null);
            textViews[i].setBackgroundDrawable(drawable1);
        }
    }
}
