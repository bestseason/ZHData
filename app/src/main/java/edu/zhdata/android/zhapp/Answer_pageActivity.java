package edu.zhdata.android.zhapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.method.ScrollingMovementMethod;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zzhoujay.richtext.RichText;

import java.io.IOException;

import edu.zhdata.android.zhapp.circleimageview.CircleImageView;
import edu.zhdata.android.zhapp.entity.GetVote;
import edu.zhdata.android.zhapp.tools.BaseNetwork;
import edu.zhdata.android.zhapp.tools.BaseNetwork1;
import edu.zhdata.android.zhapp.tools.HtmlUtil;

/**
 * Created by ck on 2017/3/1.
 */

public class Answer_pageActivity extends Activity {
    private  TextView textView;
    private ImageView iv;
    private RelativeLayout relativeLayout;
    private TextView tVote,tUpvote,tDownvote;
    private RelativeLayout.LayoutParams relativeParam,relativeParam2,relativeParam3;
    private int voteCount=0;
    private String A_Sid,Sid;
    private String Aid;
    private int Agood,Abad;
    private boolean Agoodpersonal,Abadpersonal;
    private int count=0;
    private SharedPreferences preferences;
    private TextView tx1,tx2,tx3,tx4;
    private CircleImageView imageView;
    private Bitmap bitmap;

    Handler handler=new Handler(){
      public void handleMessage(Message message){
          if(message.what==10){
              //System.out.println("子线程运行完成");
              voteCount=Agood;
              tVote.setText(voteCount+" 赞同");
              imageView.setImageBitmap(bitmap);
              imageView.setVisibility(View.VISIBLE);
          }
          if(message.what==1||message.what==2)
              Toast.makeText(Answer_pageActivity.this, "点赞成功", Toast.LENGTH_SHORT).show();
          if(message.what==4||message.what==5)
              Toast.makeText(Answer_pageActivity.this, "反对成功", Toast.LENGTH_SHORT).show();
          if(message.what==3||message.what==6)
              Toast.makeText(Answer_pageActivity.this, "有错误！", Toast.LENGTH_SHORT).show();
               if(message.what==1){
                   ++voteCount;
                   addbtn();
                   removevote();
               }
               else if(message.what==2){
                   ++voteCount;
                   addbtn();
                   removevote();
               }
               else if(message.what==4){
                   addbtn();
                   removevote();
               }
               else if(message.what==5){
                   --voteCount;
                   addbtn();
                   removevote();
               }

               if(message.what==11){
                   Toast.makeText(Answer_pageActivity.this, "关注成功", Toast.LENGTH_SHORT).show();
                   tx4.setText("已关注");
                   tx4.setTextColor(((Resources) getBaseContext().getResources()).getColor(R.color.white));
                   tx4.setBackgroundDrawable(((Resources) getBaseContext().getResources()).getDrawable(R.drawable.focus_button_frame_p));
               }
               else if(message.what==12) {
                   Toast.makeText(Answer_pageActivity.this, "取消关注成功", Toast.LENGTH_SHORT).show();
                   tx4.setText("+关注");
                   tx4.setTextColor(((Resources) getBaseContext().getResources()).getColor(R.color.mainColor));
                   tx4.setBackgroundDrawable(((Resources) getBaseContext().getResources()).getDrawable(R.drawable.wide_button_frame));
               }
               if(message.what==13){
                   tx4.setText("已关注");
                   tx4.setTextColor(((Resources) getBaseContext().getResources()).getColor(R.color.white));
                   tx4.setBackgroundDrawable(((Resources) getBaseContext().getResources()).getDrawable(R.drawable.focus_button_frame_p));
                   ++count;
               }
               if (message.what==14){
                   Toast.makeText(Answer_pageActivity.this,"请检查网络连接",Toast.LENGTH_SHORT).show();
               }
      }
    };




    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.answer_page);

        imageView=(CircleImageView)findViewById(R.id.head);
        imageView.setVisibility(View.INVISIBLE);

        tx1=(TextView) findViewById(R.id.answer_page_tx1);
        tx2=(TextView)findViewById(R.id.answer_page_tx2);
        tx3=(TextView) findViewById(R.id.answer_page_tx3);
        tx4=(TextView)findViewById(R.id.answer_page_focus);

        preferences=getSharedPreferences("first_pref",MODE_PRIVATE);
        Sid=preferences.getString("sid","");

        Intent intent=getIntent();
        tx1.setText(intent.getStringExtra("Qname"));
        tx2.setText(intent.getStringExtra("Sname"));
        RichText.fromHtml(HtmlUtil.replaceHtmlTag(intent.getStringExtra("Adetail"),"img", "src", "src=\""+BaseNetwork.IP_ADDRESS.substring(0,BaseNetwork.IP_ADDRESS.length()-1), "\"")).into(tx3);
        A_Sid=intent.getStringExtra("A_Sid");
        Aid=intent.getStringExtra("Aid");
        tx3.setMovementMethod(new ScrollingMovementMethod());
        final Typeface face = Typeface.createFromAsset (getAssets() , "fonts/msyhl.ttc" );
        tx3.setTypeface (face);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    BaseNetwork1 baseNetwork1 = new BaseNetwork1();
                    BaseNetwork baseNetwork=new BaseNetwork();
                    GetVote map = baseNetwork1.getVoteState(Integer.parseInt(Aid), Sid);
                    bitmap=baseNetwork.getImage("/images/user/"+A_Sid+"-user.jpg");
                    Agoodpersonal = map.isAgoodPersonal();
                    Abadpersonal = map.isAbadPersonal();
                    Agood = map.getAgood();
                    Abad = map.getAbad();
                    Message message = new Message();
                    message.what = 10;
                    handler.sendMessage(message);
                }catch (NullPointerException e){
                    Message message = new Message();
                    message.what = 14;
                    handler.sendMessage(message);
                } catch (IOException e) {
                    Message message = new Message();
                    message.what = 14;
                    handler.sendMessage(message);
                }
            }
        }
        ).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                BaseNetwork1 baseNetwork1=new BaseNetwork1();
                boolean is=baseNetwork1.isUserFocus(Sid,A_Sid);
                Message message=new Message();
                if(is==true){
                    message.what=13;
                }
                handler.sendMessage(message);
            }
        }).start();

        tx4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Sid.equals("-1")==false) {
                    if (!Sid.equals(A_Sid)) {
                        ++count;
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                BaseNetwork1 baseNetwork1 = new BaseNetwork1();
                                Message message = new Message();
                                boolean is = false;
                                if (count % 2 == 1) {
                                    is = baseNetwork1.focusUser(Sid, A_Sid);
                                    if (is == true) {
                                        message.what = 11;
                                    }
                                } else if (count % 2 == 0) {
                                    is = baseNetwork1.undoFocusUser(Sid, A_Sid);
                                    if (is == true) {
                                        message.what = 12;
                                    }
                                }
                                if (!is) {
                                    message.what = 14;
                                }
                                handler.sendMessage(message);
                            }
                        }).start();
                    }else {
                        Toast.makeText(Answer_pageActivity.this, "不能关注自己", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(Answer_pageActivity.this, "匿名用户不能关注", Toast.LENGTH_SHORT).show();
                }
            }
        });

        tx2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Answer_pageActivity.this,MoreActivity.class);
                intent.putExtra("Isme",false);
                intent.putExtra("user_id",A_Sid);
                startActivity(intent);
            }
        });

        iv=(ImageView)findViewById(R.id.answer_page_img1);
        iv.setOnClickListener(new View.OnClickListener() {//返回问题界面
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        
        relativeLayout= (RelativeLayout) findViewById(R.id.vote_relativeLayout);
        relativeLayout.setGravity(Gravity.CENTER_VERTICAL);
        tVote=new TextView(this);
        relativeParam = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        //tVote.setText(" 赞同");
        tVote.setTextColor((ColorStateList) ((Resources) getBaseContext().getResources()).getColorStateList(R.color.mainColor));
        tVote.setTextSize(15);
        tVote.setPadding(20,10,20,10);
        tVote.setGravity(Gravity.CENTER_VERTICAL);
        tVote.setBackgroundDrawable(getBaseContext().getResources().getDrawable(R.drawable.textview_frame));
        tVote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                relativeLayout.removeView(tVote);
                addvote();
            }
        });

        relativeParam2= new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        relativeParam2.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        tUpvote=new TextView(this);
        tUpvote.setId(R.id.upVote);
        tUpvote.setText("赞同");
        tUpvote.setTextColor((ColorStateList) ((Resources) getBaseContext().getResources()).getColorStateList(R.color.mainColor));
        tUpvote.setTextSize(15);
        tUpvote.setGravity(Gravity.CENTER);
        tUpvote.setBackgroundDrawable(getBaseContext().getResources().getDrawable(R.drawable.textview_frame));
        tUpvote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Sid.equals("-1")==false) {
                    if (Agoodpersonal == false) {//用户对该问题没有点赞
                        if (Abadpersonal == false) {//用户对问题既没点赞也没反对

                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    BaseNetwork1 baseNetwork1 = new BaseNetwork1();//可以直接点赞
                                    boolean is = baseNetwork1.addVote(Integer.parseInt(Aid), Sid, 1);
                                    Message message = new Message();
                                    if (is == true) {
                                        Agoodpersonal=true;
                                        message.what = 1;
                                    }else {
                                        message.what=14;
                                    }
                                    handler.sendMessage(message);
                                }
                            }).start();
                        } else if (Abadpersonal == true) {//用户对该问题没有点赞却点了反对
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    BaseNetwork1 baseNetwork1 = new BaseNetwork1();
                                    boolean is = baseNetwork1.deleteVote(Integer.parseInt(Aid), Sid);//先删除反对再点赞
                                    Message message = new Message();
                                    if (is == true) {
                                        Agoodpersonal=true;
                                        Abadpersonal=false;
                                        boolean is1 = baseNetwork1.addVote(Integer.parseInt(Aid), Sid, 1);
                                        if (is1 == true) {
                                            message.what = 2;
                                        } else {
                                            message.what = 3;
                                        }
                                    }else {
                                        message.what=14;
                                    }

                                    handler.sendMessage(message);
                                }
                            }).start();
                        }
                    } else {
                        Toast.makeText(Answer_pageActivity.this, "已点过赞", Toast.LENGTH_SHORT).show();
                        removevote();
                        addbtn();
                    }
                }
                else {
                    Toast.makeText(Answer_pageActivity.this, "匿名用户不能点赞", Toast.LENGTH_SHORT).show();
                    removevote();
                    addbtn();
                }

            }
        });

        relativeParam3= new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        relativeParam3.addRule(RelativeLayout.ALIGN_LEFT);
        relativeParam3.addRule(RelativeLayout.RIGHT_OF,R.id.upVote);
        tDownvote=new TextView(this);
        tDownvote.setText("反对");
        tDownvote.setTextColor((ColorStateList) ((Resources) getBaseContext().getResources()).getColorStateList(R.color.mainColor));
        tDownvote.setTextSize(15);
        tDownvote.setGravity(Gravity.CENTER);
        tDownvote.setBackgroundDrawable(getBaseContext().getResources().getDrawable(R.drawable.textview_frame));
        tDownvote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Sid.equals("-1")==false) {
                    if (Abadpersonal == false) {//用户对该问题没有反对
                        if (Agoodpersonal == false) {//用户对问题既没点赞也没反对
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    BaseNetwork1 baseNetwork1 = new BaseNetwork1();//可以直接反对
                                    boolean is = baseNetwork1.addVote(Integer.parseInt(Aid), Sid, -1);
                                    Message message = new Message();
                                    if (is == true) {
                                        Abadpersonal=true;
                                        message.what = 4;
                                    }else {
                                        message.what=14;
                                    }
                                    handler.sendMessage(message);
                                }
                            }).start();
                        } else if (Agoodpersonal == true) {//用户对该问题点了赞没有反对
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    BaseNetwork1 baseNetwork1 = new BaseNetwork1();
                                    boolean is = baseNetwork1.deleteVote(Integer.parseInt(Aid), Sid);//先删除赞再反对
                                    Message message = new Message();
                                    if (is == true) {
                                        Abadpersonal=true;
                                        Agoodpersonal=false;
                                        boolean is1 = baseNetwork1.addVote(Integer.parseInt(Aid), Sid, -1);
                                        if (is1 == true) {
                                            message.what = 5;
                                        } else {
                                            message.what = 6;
                                        }
                                    }else {
                                        message.what=14;
                                    }

                                    handler.sendMessage(message);
                                }
                            }).start();
                        }
                    } else {
                        Toast.makeText(Answer_pageActivity.this, "已点过反对", Toast.LENGTH_SHORT).show();
                        removevote();
                        addbtn();
                    }
                }
                else {
                    Toast.makeText(Answer_pageActivity.this, "匿名用户不能反对", Toast.LENGTH_SHORT).show();
                    removevote();
                    addbtn();
                }

            }
        });

        addbtn();
    }

    private void addbtn(){
        relativeLayout.addView(tVote,relativeParam);
        tVote.setText(voteCount+" 赞同");
    }
    private void addvote(){

        relativeParam2.width=relativeLayout.getWidth()/2-10;
        relativeParam3.width=relativeLayout.getWidth()/2-10;
        relativeLayout.addView(tUpvote,relativeParam2);
        relativeLayout.addView(tDownvote,relativeParam3);
    }
    private void removevote(){
        relativeLayout.removeView(tUpvote);
        relativeLayout.removeView(tDownvote);
    }
}
