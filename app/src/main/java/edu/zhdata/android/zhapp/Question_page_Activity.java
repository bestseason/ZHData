package edu.zhdata.android.zhapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.carbs.android.expandabletextview.library.ExpandableTextView;
import edu.zhdata.android.zhapp.entity.MyTopic;
import edu.zhdata.android.zhapp.entity.QAnswer;
import edu.zhdata.android.zhapp.entity.TQuestion;
import edu.zhdata.android.zhapp.tools.BaseNetwork1;

/**
 * Created by ck on 2017/2/28.
 */

public class Question_page_Activity extends Activity{
    private ListView lv;
    private ArrayList<HashMap<String,Object>> datalist;
    private HashMap<String,Object> map;
    private ImageView imageView;
    private TextView textView3,textView12,tx1;
    private ExpandableTextView tx2;
    private Button button;
    private BaseNetwork1 baseNetwork1=new BaseNetwork1();
    private int q_id;
    private boolean Agoodpersonal,Abadpersonal;
    private String s_id,a_id,agood;
    private int count=0;
    Typeface face;
    private TextView[] textViews=new TextView[18];
    private LinearLayout linearLayout;

    private List<MyTopic> list1;
    private List<TQuestion> list2;
    private List<QAnswer> list3;
    private boolean isFocusQuestion;

    private List<String> tlist,tidlist;
    private HashMap<String,Object> getmap;
    private List<HashMap<String,Object>> ulist;
    private SimpleAdapter sa;
    private SharedPreferences preferences;

    Handler handler=new Handler(){
        public void handleMessage(Message message){
            if (message.what==1){

                for(int i=0;i<tlist.size();i++){
                    textViews[i]=new TextView(Question_page_Activity.this);
                    textViews[i].setText(tlist.get(i));
                    textViews[i].setBackgroundDrawable(((Resources) getBaseContext().getResources()).getDrawable(R.drawable.topic_textview_frame));
                    textViews[i].setTextColor((ColorStateList) ((Resources) getBaseContext().getResources()).getColorStateList(R.color.mainColor));
                    textViews[i].setPadding(20,10,20,10);
                    linearLayout.addView(textViews[i]);
                    textViews[i].setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent=new Intent(Question_page_Activity.this,TopicActivity.class);
                            for(int j=0;j<tlist.size();j++) {
                                if(view==textViews[j]) {
                                    intent.putExtra("Tid", tidlist.get(j));
                                    intent.putExtra("Tname", tlist.get(j));
                                }
                            }
                            startActivity(intent);
                        }
                    });
                }

                tx1.setText((String)getmap.get("Qname"));
                tx2.setText((String)getmap.get("Qdetail"));
                tx1.setTypeface (face);
                tx2.setTypeface (face);
                TextPaint tpaint = tx1.getPaint();
                tpaint.setFakeBoldText(true);

//                sa=new SimpleAdapter(Question_page_Activity.this,ulist,R.layout.question_page_listview,new String[]{"Sname","Adetail","Agood","Atime"},new int[]{R.id.question_page_listview_tx1,R.id.question_page_listview_tx2,R.id.question_page_listview_tx3,R.id.question_page_listview_tx4});
                MyAdapter myAdapter=new MyAdapter(Question_page_Activity.this,ulist);
                lv.setAdapter(myAdapter);
            }
            else if(message.what==2){//点击关注
                Toast.makeText(Question_page_Activity.this, "关注成功", Toast.LENGTH_SHORT).show();
                button.setText("已关注");
                button.setBackgroundDrawable(((Resources) getBaseContext().getResources()).getDrawable(R.drawable.focus_button_frame_p));
            }
            else if(message.what==3){//取消关注
                Toast.makeText(Question_page_Activity.this, "取消关注成功", Toast.LENGTH_SHORT).show();
                button.setText("关注");
                button.setBackgroundDrawable(((Resources) getBaseContext().getResources()).getDrawable(R.drawable.focus_button_frame));
            }else if(message.what==4){
                button.setText("已关注");
                button.setBackgroundDrawable(((Resources) getBaseContext().getResources()).getDrawable(R.drawable.focus_button_frame_p));
                ++count;
            }else if (message.what==5){
                Toast.makeText(Question_page_Activity.this,"请检查网络连接",Toast.LENGTH_SHORT).show();
            }
        }
    };


    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        //设置字体
        face = Typeface.createFromAsset (getAssets() , "fonts/msyhl.ttc" );
        setContentView(R.layout.question_page);
        lv=(ListView)findViewById(R.id.question_page_list1);
        tx1=(TextView)findViewById(R.id.question_page_tx1);
        tx2=(ExpandableTextView) findViewById(R.id.question_page_tx2);
        linearLayout=(LinearLayout)findViewById(R.id.question_page_linear);
        button=(Button)findViewById(R.id.question_page_btn1);



        Intent intent=getIntent();
        String Qid=intent.getStringExtra("Qid");
        q_id=Integer.parseInt(Qid);
        preferences=getSharedPreferences("first_pref",MODE_PRIVATE);
        s_id=preferences.getString("sid"," ");
//        s_id=intent.getStringExtra("Sid");
        //a_id=intent.getStringExtra("Aid");

        new Thread(new Runnable() {
            @Override
            public void run() {
                BaseNetwork1 baseNetwork1=new BaseNetwork1();
                boolean is=baseNetwork1.isFocusQuestion(q_id,s_id);
                Message message=new Message();
                if(is==true){
                    message.what=4;
                }
                handler.sendMessage(message);
            }
        }).start();

          button.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  if (s_id.equals("-1") == false) {
                      ++count;
                      new Thread(new Runnable() {
                          @Override
                          public void run() {
                              BaseNetwork1 baseNetwork1 = new BaseNetwork1();
                              Message message = new Message();
                              boolean is=false;
                              if (count % 2 == 1) {
                                  is = baseNetwork1.focusQuestion(q_id, s_id);
                                  if (is == true) {
                                      message.what = 2;
                                  }
                              } else if (count % 2 == 0) {
                                  is = baseNetwork1.undofocusQuestion(q_id, s_id);
                                  if (is == true) {
                                      message.what = 3;
                                  }
                              }
                              if (!is){
                                  message.what=5;
                              }
                              handler.sendMessage(message);
                          }
                      }).start();
                  } else {
                      Toast.makeText(Question_page_Activity.this, "匿名用户不能关注", Toast.LENGTH_SHORT).show();
                  }

              }
          });



//        Intent intent=getIntent();
//        final String qid=intent.getStringExtra("Qid");
//        int Qid=Integer.parseInt(qid);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    list1 = new ArrayList<MyTopic>();
                    list1 = baseNetwork1.selectTidTnameByQid(q_id);
                    System.out.println(list1);
                    tlist = new ArrayList<String>();
                    tidlist = new ArrayList<String>();
                    for (int i = 0; i < list1.size(); i++) {
                        tlist.add(list1.get(i).getTname());
                        tidlist.add(list1.get(i).getTid() + "");
                    }
                    list2 = new ArrayList<TQuestion>();
                    list2 = baseNetwork1.selectQuestionByQid(q_id);
                    getmap = new HashMap<String, Object>();
                    getmap.put("Qname", list2.get(0).getQname());
                    getmap.put("Qdetail", list2.get(0).getQdetail());
                    getmap.put("Qid", list2.get(0).getQid());

//                list3=new ArrayList<QAnswer>();
                    list3 = baseNetwork1.selectAnswerByQid(q_id);
                    ulist = new ArrayList<HashMap<String, Object>>();
                    HashMap<String, Object> map1;
                    for (int i = 0; i < list3.size(); i++) {
                        map1 = new HashMap<String, Object>();
                        map1.put("Sname", list3.get(i).getSname());
                        map1.put("Adetail", list3.get(i).getAdetail());
                        map1.put("Agood", list3.get(i).getAgood());
                        map1.put("Atime", list3.get(i).getAtime());
                        map1.put("Sid", list3.get(i).getSid());
                        ulist.add(map1);
                    }

                    Message ms = new Message();
                    ms.what = 1;
                    handler.sendMessage(ms);
                }catch (NullPointerException e){
                    Message ms = new Message();
                    ms.what = 5;
                    handler.sendMessage(ms);
                }
            }
        }).start();

        imageView=(ImageView)findViewById(R.id.question_page_img1);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        textView3=(TextView)findViewById(R.id.question_page_tx3);
        textView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(s_id.equals("-1")) {
                    Toast.makeText(Question_page_Activity.this,"请登录后再回答",Toast.LENGTH_SHORT).show();
                }else {
                    Intent intent = new Intent(Question_page_Activity.this, Answer2Activity.class);
                    intent.putExtra("Qid", q_id);
//                intent.putExtra("Sid",s_id);
                    startActivity(intent);
                    finish();
                }
            }
        });


    }


    public class MyAdapter extends BaseAdapter {
        private Context context;                        //运行上下文
        private List<HashMap<String,Object>> dataList;    //商品信息集合
        private LayoutInflater listContainer;           //视图容器
        public MyAdapter(Context context, List<HashMap<String,Object>> dataList){
            this.context = context;
            listContainer = LayoutInflater.from(context);
            //创建视图容器并设置上下文

            this.dataList=dataList;
        }
        @Override
        public int getCount() {
            return dataList.size();
        }

        @Override
        public HashMap<String, Object> getItem(int position) {
            return dataList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getItemViewType(int position) {
            return 1;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            Question_page_Activity.MyAdapter.ViewHolder holder = null;
            View v = convertView;

                if (convertView == null) {// 如果是第一次显示该页面(要记得保存到viewholder中供下次直接从缓存中调用)
                    holder = new Question_page_Activity.MyAdapter.ViewHolder();

                    convertView = listContainer.inflate(R.layout.question_page_listview,parent,false);
                    // 以下为保存这一屏的内容，供下次回到这一屏的时候直接refresh，而不用重读布局文件
                    holder.textView1 = (TextView) convertView.findViewById(R.id.question_page_listview_tx1);
                    holder.textView2 = (TextView) convertView.findViewById(R.id.question_page_listview_tx2);
                    holder.textView3 = (TextView) convertView.findViewById(R.id.question_page_listview_tx3);
                    holder.textView4 = (TextView) convertView.findViewById(R.id.question_page_listview_tx4);

                    convertView.setTag(holder);

                    //}
                } else {// 如果之前已经显示过该页面，则用viewholder中的缓存直接刷屏

                    holder = (Question_page_Activity.MyAdapter.ViewHolder) convertView.getTag();
                }

                final HashMap<String, Object> map=dataList.get(position);
                holder.textView1.setText(map.get("Sname")+"");
               char ch[]=map.get("Adetail").toString().toCharArray();
               char ch1[]=new char[ch.length];
               int j=0;
                for(int i=0;i<ch.length;i++) {
                    if (ch[i] == '<' && ch[i + 1] == 'i' && ch[i + 2] == 'm' && ch[i + 3] == 'g') {
                        ch1[j++]='[';
                        ch1[j++]='图';
                        ch1[j++]='片';
                        ch1[j++]=']';
                        while (ch[i] != '>') {
                            i++;
                        }
                    }else {
                        ch1[j++] = ch[i];
                    }
                }
                String chdata= new String(ch1);
                holder.textView2.setText(Html.fromHtml(chdata.toString()));
                holder.textView3.setText(map.get("Agood")+" 赞同");
                holder.textView4.setText(map.get("Atime")+"");

            holder.textView1.setTypeface (face);
            holder.textView2.setTypeface (face);
            holder.textView3.setTypeface (face);
            holder.textView4.setTypeface (face);

                final String title=holder.textView2.getText().toString();
                final String detail=holder.textView3.getText().toString();

                holder.textView1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(Question_page_Activity.this,MoreActivity.class);
                        intent.putExtra("Isme",false);
                        intent.putExtra("user_id",map.get("Sid")+"");
                        startActivity(intent);
                    }
                });
                holder.textView2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(Question_page_Activity.this,Answer_pageActivity.class);
                        intent.putExtra("Qname",getmap.get("Qname")+"");
                        intent.putExtra("Sname",map.get("Sname")+"");
                        intent.putExtra("Adetail",map.get("Adetail")+"");
                        intent.putExtra("Aid",list3.get(position).getAid()+"");
                        intent.putExtra("A_Sid",map.get("Sid")+"");
                        startActivity(intent);
                    }
                });
                return convertView;
            }



        private final class ViewHolder{
            TextView textView1,textView2,textView3,textView4;
        }
    }

}

