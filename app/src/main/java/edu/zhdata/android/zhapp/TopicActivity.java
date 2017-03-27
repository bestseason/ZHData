package edu.zhdata.android.zhapp;


import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;

import java.util.ArrayList;

import edu.zhdata.android.zhapp.tools.BaseNetwork1;

public class TopicActivity extends FragmentActivity {
    private ImageView imageView;
    public  String s_id,t_id;
    private ArrayList<String> list=new ArrayList<String>();
    private TextView tx,tx1;
    private int count=0;

    Handler handler=new Handler(){
        public void handleMessage(Message message){
            if(message.what==1){
                Toast.makeText(TopicActivity.this, "关注成功", Toast.LENGTH_SHORT).show();
                tx1.setText("已关注");
                tx1.setBackgroundDrawable(((Resources) getBaseContext().getResources()).getDrawable(R.drawable.focus_topic_frame_p));
            }
            else if(message.what==2){
                Toast.makeText(TopicActivity.this, "取消关注成功", Toast.LENGTH_SHORT).show();
                tx1.setText("关注话题");
                tx1.setBackgroundDrawable(((Resources) getBaseContext().getResources()).getDrawable(R.drawable.focus_topic_frame));
            }
            else if(message.what==3){
                tx1.setText("已关注");
                tx1.setBackgroundDrawable(((Resources) getBaseContext().getResources()).getDrawable(R.drawable.focus_topic_frame_p));
                ++count;
            }
        }
    };
    private SharedPreferences preferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic);

        ViewPager pager = (ViewPager) findViewById(R.id.topic_pager);
        pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));

        // Bind the tabs to the ViewPager
        PagerSlidingTabStrip tabs = (PagerSlidingTabStrip) findViewById(R.id.topic_tabs);
        tabs.setViewPager(pager);

        tx1=(TextView)findViewById(R.id.activity_topic_tx2);


        imageView=(ImageView)findViewById(R.id.activity_topic_img1);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tx = (TextView)findViewById(R.id.activity_topic_tx1);
        Intent intent=getIntent();
        t_id=intent.getStringExtra("Tid");
        preferences=getSharedPreferences("first_pref",MODE_PRIVATE);
        s_id=preferences.getString("sid"," ");
//        if(s_id.equals("-1")==false) {
//            s_id = intent.getStringExtra("Sid");
//        }
        tx.setText(intent.getStringExtra("Tname"));

        new Thread(new Runnable() {
            @Override
            public void run() {
                BaseNetwork1 baseNetwork1=new BaseNetwork1();
                boolean is=baseNetwork1.isTopicFocus(Integer.parseInt(t_id),s_id);
                Message message=new Message();
                if(is==true){
                    message.what=3;
                }
                handler.sendMessage(message);
            }
        }).start();

        tx1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(s_id.equals("-1")==false) {
                    ++count;
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            BaseNetwork1 baseNetwork1 = new BaseNetwork1();
                            Message message = new Message();
                            boolean is;
                            if (count % 2 == 1) {
                                is = baseNetwork1.focusTopic(Integer.parseInt(t_id), s_id);
                                if (is == true) {
                                    message.what = 1;
                                }
                            } else if (count % 2 == 0) {
                                is = baseNetwork1.undoFocusTopic(Integer.parseInt(t_id), s_id);
                                if (is == true) {
                                    message.what = 2;
                                }
                            }
                            handler.sendMessage(message);
                        }
                    }).start();
                }
                else {
                    Toast.makeText(TopicActivity.this, "匿名用户不能关注", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    public class MyPagerAdapter extends FragmentPagerAdapter {



        private final String[] TITLES = {"所有问题","热门问题","等待回答"};


        MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return TITLES[position];
        }

        @Override
        public int getCount() {
            return TITLES.length;
        }

        @Override
        public Fragment getItem(int position) {
            return TopicFragmentActivity.newInstance(position);
        }
    }

}
