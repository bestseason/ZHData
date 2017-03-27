package edu.zhdata.android.zhapp;

import android.app.TabActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends TabActivity {
    private String[] tabMenu = {"主页", "发现", "提问", "回答","更多"};
    private Intent intent0, intent1, intent2, intent3, intent4;
    private Intent[] intents = {intent0, intent1, intent2, intent3,intent4};
    private TabHost.TabSpec tabSpec0, tabSpec1, tabSpec2, tabSpec3,tabSpec4;
    private TabHost.TabSpec[] tabSpecs = {tabSpec0, tabSpec1, tabSpec2,
            tabSpec3,tabSpec4};
    private int[] draw = {R.drawable.home, R.drawable.discover, R.drawable.question_press, R.drawable.answer,R.drawable.more};

    private TabHost tabHost = null;
    //private ArrayList<String> arrayList=new ArrayList<String>();

    private TextView textView;
    private String Sid;
    private SharedPreferences personalinfoamation;
    private ArrayList<Integer> Tlist=new ArrayList<Integer>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题栏
        setContentView(R.layout.activity_main);

        personalinfoamation=getSharedPreferences("first_pref",MODE_PRIVATE);
        Sid=personalinfoamation.getString("sid"," ");
        if(Sid.equals("-1")==true) {
            Intent intent = getIntent();
            Tlist = intent.getIntegerArrayListExtra("Tlist");
         }
        //arrayList=getlist();//获取的话题传递给Arraylist
        tabHost = getTabHost();


        for (int i = 0; i < tabMenu.length; i++) {
                intents[i] = new Intent();
            switch (i) {
                case 0:
                    intents[i].setClass(this, MainContentActivity.class);//进去maincontent页面并将从前面获取的话题信息发送给maincontent
                    intents[i].putExtra("Sid",Sid);
                    if(Sid.equals("-1")==true){
                        intents[i].putIntegerArrayListExtra("Tlist",Tlist);
                    }
                    break;
                case 1:
                    intents[i].setClass(this, DiscoverContent.class);
//                    intents[i].putExtra("Sid",Sid);
                    break;
                case 2:
                    intents[i].setClass(this,Put_questions_to1Activity.class);
//                    intents[i].putExtra("Sid",Sid);
                    break;
                case 3:
                    intents[i].setClass(this,Answer1Activity.class);
//                    intents[i].putExtra("Sid",Sid);
                    break;
                case 4:
                    intents[i].setClass(this,MoreActivity.class);
//                    intents[i].putExtra("Sid",Sid);
                    intents[i].putExtra("Isme",true);
                    break;
            }
                tabSpecs[i] = tabHost.newTabSpec(tabMenu[i]);
                //tabSpecs[i].setIndicator(tabMenu[i]);// 设置文字
                tabSpecs[i].setIndicator("", this.getResources().getDrawable(draw[i]));
                tabSpecs[i].setContent(intents[i]);// 设置该页的内容


                tabHost.addTab(tabSpecs[i]);// 将该页的内容添加到Tabhost
        }


        tabHost.setCurrentTabByTag(tabMenu[0]); // 设置第一次打开时默认显示的标签，

        updateTab(tabHost);//初始化Tab的颜色，和字体的颜色

        tabHost.setOnTabChangedListener(new OnTabChangedListener()); // 选择监听器


    }


    class OnTabChangedListener implements TabHost.OnTabChangeListener {


        @Override
        public void onTabChanged(String tabId) {
            tabHost.setCurrentTabByTag(tabId);
            System.out.println("tabid " + tabId);
            if (tabId.equals("提问")){
                if(Sid.equals("-1")==true) {
                    Toast.makeText(MainActivity.this,"请登录后再提问",Toast.LENGTH_SHORT).show();
                }
            }else if (tabId.equals("回答")){
                if(Sid.equals("-1")==true) {
                    Toast.makeText(MainActivity.this,"请登录后再回答",Toast.LENGTH_SHORT).show();
                }
            }
            System.out.println("curreny after: " + tabHost.getCurrentTabTag());
            updateTab(tabHost);
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            System.exit(0);
            return false;
        } else if (keyCode == KeyEvent.KEYCODE_MENU
                && event.getRepeatCount() == 0) {
            return true; // 返回true就不会弹出默认的setting菜单
        }


        return false;
    }

    /**
     * 更新Tab标签的颜色，和字体的颜色
     *
     * @param tabHost
     */
    private void updateTab(final TabHost tabHost) {
        for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++) {
            View view = tabHost.getTabWidget().getChildAt(i);
            view.setBackgroundResource(R.drawable.tab_style);
            //TextView tv = (TextView) tabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
            //tv.setTextSize(16);
            //tv.setTypeface(Typeface.SERIF, 2); // 设置字体和风格
            ImageView iv = (ImageView) tabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.icon);
            if (tabHost.getCurrentTab() == i) {//选中
                //view.setBackgroundColor(getResources().getColor(R.color.background));//选中后的背景
                //tv.setTextColor(this.getResources().getColorStateList(android.R.color.white));
                switch (i) {
                    case 0:
                        iv.setImageResource(R.drawable.home_press);
                        break;
                    case 1:
                        iv.setImageResource(R.drawable.discover_press);
                        break;
                    case 2:
                        iv.setImageResource(R.drawable.question_press);
                        break;
                    case 3:
                        iv.setImageResource(R.drawable.answer_press);
                        break;
                    case 4:
                        iv.setImageResource(R.drawable.more_press);
                        break;
                }
            } else {//不选中
                //view.setBackgroundDrawable(getResources().getDrawable(R.drawable.choosebak));//非选择的背景
                //tv.setTextColor(this.getResources().getColorStateList(R.color.tab_text));
                switch (i) {
                    case 0:
                        iv.setImageResource(R.drawable.home);
                        break;
                    case 1:
                        iv.setImageResource(R.drawable.discover);
                        break;
                    case 2:
                        iv.setImageResource(R.drawable.question);
                        break;
                    case 3:
                        iv.setImageResource(R.drawable.answer);
                        break;
                    case 4:
                        iv.setImageResource(R.drawable.more);
                        break;
                }
            }
        }
    }
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            ActivityCollector.finishAll();
        }
        return super.dispatchKeyEvent(event);
    }

//    public ArrayList<String> getlist(){//从选择话题页面后者信息完善页面获取话题
//        Intent intent=getIntent();
//        ArrayList<String> arrayList=new ArrayList<String>();
//        Bundle b=this.getIntent().getExtras();
//        arrayList=b.getStringArrayList("arraylist");
//        return  arrayList;
//    }

}
