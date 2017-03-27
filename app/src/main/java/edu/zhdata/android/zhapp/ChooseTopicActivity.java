package edu.zhdata.android.zhapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by ck on 2017/3/1.
 */

public class ChooseTopicActivity extends Activity {

    //private TextView[] textViews=new TextView[18];
    //private TableRow[] tableRows=new TableRow[3];
    //private int j=-1,i;
    private String Tname[]={"自然","网络","阅读","美食","金融","体育","汽车","健康","音乐"};
    //private int Drawable[]={R.drawable.gradient_frame,R.drawable.gradient_frame1,R.drawable.gradient_frame2,R.drawable.gradient_frame3,R.drawable.gradient_frame4,R.drawable.gradient_frame5,R.drawable.gradient_frame6,R.drawable.gradient_frame7,R.drawable.gradient_frame8};
    private int Drawable[]={R.drawable.topic_choose_frame,R.drawable.topic_choose_frame1,R.drawable.topic_choose_frame2,R.drawable.topic_choose_frame3,R.drawable.topic_choose_frame4};
    private int PDrawable[]={R.drawable.topic_chose_frame,R.drawable.topic_chose_frame1,R.drawable.topic_chose_frame2,R.drawable.topic_chose_frame3,R.drawable.topic_chose_frame4};
    private TextView[] textViews=new TextView[9];
    private int txId[]={R.id.textView31,R.id.textView32,R.id.textView33,R.id.textView34,R.id.textView35,R.id.textView36,R.id.textView37,R.id.textView38,R.id.textView39};
    private boolean[] txPressed=new boolean[9];
    private ArrayList<String> list=new ArrayList<String>();
    private View.OnClickListener clickListener;
    private SharedPreferences.Editor editor;
    private SharedPreferences preferences;
    private int tid[]={6796,6803,6825,6830,6829,6820,6834,6806,6814};
    private ArrayList<Integer> Tidlist=new ArrayList<>();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.topic);

        preferences=getSharedPreferences("first_pref",MODE_PRIVATE);
        editor=preferences.edit();
        editor.putString("sid","-1");
        editor.commit();
        ActivityCollector.addActivity(this);
        clickListener=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v==textViews[0]){
                    if (txPressed[0]){
                        for(Integer i:Tidlist) {
                            if(i==tid[0]) {
                                Tidlist.remove(i);
                                break;
                            }
                        }
                        textViews[0].setBackgroundDrawable(((Resources) getBaseContext().getResources()).getDrawable(Drawable[0]));
                        textViews[0].setTextColor(0xff3b77bf);
                    }else {
                        Tidlist.add(tid[0]);
                        textViews[0].setBackgroundDrawable(((Resources) getBaseContext().getResources()).getDrawable(PDrawable[0]));
                        textViews[0].setTextColor(0xffffffff);
                    }
                    txPressed[0]=!txPressed[0];
                }else if(v==textViews[1]){
                    if (txPressed[1]){
                        for(Integer i:Tidlist) {
                            if(i==tid[1]) {
                                Tidlist.remove(i);
                                break;
                            }
                        }
                        textViews[1].setBackgroundDrawable(((Resources) getBaseContext().getResources()).getDrawable(Drawable[1]));
                        textViews[1].setTextColor(0xff7c6ad6);
                    }else {
                        Tidlist.add(tid[1]);
                        textViews[1].setBackgroundDrawable(((Resources) getBaseContext().getResources()).getDrawable(PDrawable[1]));
                        textViews[1].setTextColor(0xffffffff);
                    }
                    txPressed[1]=!txPressed[1];
                }else if(v==textViews[2]){
                    if (txPressed[2]){
                        for(Integer i:Tidlist) {
                            if(i==tid[2]) {
                                Tidlist.remove(i);
                                break;
                            }
                        }
                        textViews[2].setBackgroundDrawable(((Resources) getBaseContext().getResources()).getDrawable(Drawable[2]));
                        textViews[2].setTextColor(0xFFFD6B9C);
                    }else {
                        Tidlist.add(tid[2]);
                        textViews[2].setBackgroundDrawable(((Resources) getBaseContext().getResources()).getDrawable(PDrawable[2]));
                        textViews[2].setTextColor(0xffffffff);
                    }
                    txPressed[2]=!txPressed[2];
                }else if(v==textViews[3]){
                    if (txPressed[3]){
                        for(Integer i:Tidlist) {
                            if(i==tid[3]) {
                                Tidlist.remove(i);
                                break;
                            }
                        }
                        textViews[3].setBackgroundDrawable(((Resources) getBaseContext().getResources()).getDrawable(Drawable[1]));
                        textViews[3].setTextColor(0xFF7c6ad6);
                    }else {
                        Tidlist.add(tid[3]);
                        textViews[3].setBackgroundDrawable(((Resources) getBaseContext().getResources()).getDrawable(PDrawable[1]));
                        textViews[3].setTextColor(0xffffffff);
                    }
                    txPressed[3]=!txPressed[3];
                }else if(v==textViews[4]){
                    if (txPressed[4]){
                        for(Integer i:Tidlist) {
                            if(i==tid[4]) {
                                Tidlist.remove(i);
                                break;
                            }
                        }
                        textViews[4].setBackgroundDrawable(((Resources) getBaseContext().getResources()).getDrawable(Drawable[2]));
                        textViews[4].setTextColor(0xfffd6b9c);
                    }else {
                        Tidlist.add(tid[4]);
                        textViews[4].setBackgroundDrawable(((Resources) getBaseContext().getResources()).getDrawable(PDrawable[2]));
                        textViews[4].setTextColor(0xffffffff);
                    }
                    txPressed[4]=!txPressed[4];
                }else if(v==textViews[5]){
                    if (txPressed[5]){
                        for(Integer i:Tidlist) {
                            if(i==tid[5]) {
                                Tidlist.remove(i);
                                break;
                            }
                        }
                        textViews[5].setBackgroundDrawable(((Resources) getBaseContext().getResources()).getDrawable(Drawable[3]));
                        textViews[5].setTextColor(0xFFff7467);
                    }else {
                        Tidlist.add(tid[5]);
                        textViews[5].setBackgroundDrawable(((Resources) getBaseContext().getResources()).getDrawable(PDrawable[3]));
                        textViews[5].setTextColor(0xffffffff);
                    }
                    txPressed[5]=!txPressed[5];
                }else if(v==textViews[6]){
                    if (txPressed[6]){
                        for(Integer i:Tidlist) {
                            if(i==tid[6]) {
                                Tidlist.remove(i);
                                break;
                            }
                        }
                        textViews[6].setBackgroundDrawable(((Resources) getBaseContext().getResources()).getDrawable(Drawable[2]));
                        textViews[6].setTextColor(0xFFfd6b9c);
                    }else {
                        Tidlist.add(tid[6]);
                        textViews[6].setBackgroundDrawable(((Resources) getBaseContext().getResources()).getDrawable(PDrawable[2]));
                        textViews[6].setTextColor(0xffffffff);
                    }
                    txPressed[6]=!txPressed[6];
                }else if(v==textViews[7]){
                    if (txPressed[7]){
                        for(Integer i:Tidlist) {
                            if(i==tid[7]) {
                                Tidlist.remove(i);
                                break;
                            }
                        }
                        textViews[7].setBackgroundDrawable(((Resources) getBaseContext().getResources()).getDrawable(Drawable[3]));
                        textViews[7].setTextColor(0xFFff7467);
                    }else {
                        Tidlist.add(tid[7]);
                        textViews[7].setBackgroundDrawable(((Resources) getBaseContext().getResources()).getDrawable(PDrawable[3]));
                        textViews[7].setTextColor(0xffffffff);
                    }
                    txPressed[7]=!txPressed[7];
                }else if(v==textViews[8]){
                    if (txPressed[8]){
                        textViews[8].setBackgroundDrawable(((Resources) getBaseContext().getResources()).getDrawable(Drawable[4]));
                        textViews[8].setTextColor(0xFFF9AA11);
                    }else {
                        textViews[8].setBackgroundDrawable(((Resources) getBaseContext().getResources()).getDrawable(PDrawable[4]));
                        textViews[8].setTextColor(0xffffffff);
                    }
                    txPressed[8]=!txPressed[8];
                }
            }
        };
        for (int i=0;i<9;i++){
            txPressed[i]=false;
            textViews[i]= (TextView) findViewById(txId[i]);
            textViews[i].setOnClickListener(clickListener);
        }
        /*tableRows[0] =(TableRow) findViewById(R.id.tablerow1);
        tableRows[1]=(TableRow)findViewById(R.id.tablerow2);
        tableRows[2]=(TableRow)findViewById(R.id.tablerow3);

        for(i=0;i<9;i++){
            if((i)%3==0) {//tablerow一行最多为6了控件
                j++;
            }
            textViews[i]=new TextView(this);//动态设置textview
            textViews[i].setText(Tname[i]);
            textViews[i].setBackgroundDrawable(((Resources) getBaseContext().getResources()).getDrawable(Drawable[i]));
            textViews[i].setPadding(20,20,20,20);
            textViews[i].setGravity(View.TEXT_ALIGNMENT_CENTER);
            TableRow.LayoutParams lp=new TableRow.LayoutParams(TableRow.LayoutParams .MATCH_PARENT,TableRow.LayoutParams .MATCH_PARENT);//动态设置textview的外边距
            //(TableRow.LayoutParams)(textViews[i].getLayoutParams());
            lp.setMargins(120,30,60,30);
            textViews[i].setLayoutParams(lp);
            tableRows[j].addView(textViews[i]);
            textViews[i].setOnClickListener(new View.OnClickListener() {//监听textview点击事件
                @Override
                public void onClick(View view) {
                    list.add(textViews[i].getText().toString());
                }
            });
        }*/
    }
    public void click(View view){
        switch (view.getId()){
            case R.id.topicbtn1://跳转到主界面
                Intent i1=new Intent(this,MainActivity.class);
                Bundle b=new Bundle();
                if(Tidlist.size()==0) {
                    Toast.makeText(this, "没有选择话题", Toast.LENGTH_SHORT).show();
                }
                else {
                    b.putIntegerArrayList("Tlist", Tidlist);
                    i1.putExtras(b);
                    startActivity(i1);
                    finish();
                }
                break;
            case R.id.topic_register://跳转到注册界面
                Intent i2=new Intent(this,Register_Activity.class);
                startActivity(i2);
                break;
            case R.id.topic_login://跳转到登录界面
                Intent i3=new Intent(this,LoginActivity.class);
                startActivity(i3);
                break;
            default:
                break;
        }
    }
}
