package edu.zhdata.android.zhapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
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

import java.io.IOException;

import edu.zhdata.android.zhapp.circleimageview.CircleImageView;
import edu.zhdata.android.zhapp.entity.User;
import edu.zhdata.android.zhapp.tools.BaseNetwork;

/**
 * Created by ck on 2017/3/1.
 */

public class MoreActivity extends FragmentActivity {
    ImageView imageView;
    CircleImageView imageView2;
    private boolean Isme;
    private User user;
    private TextView textView1,textView2,textView3;
    public String Sid,mySid;
    private SharedPreferences preferences,preferences1;
    private Intent intent1;
    private Bitmap bitmap;
    private int REQUEST_OK = 1;

    Handler handler=new Handler(){
        public void handleMessage(Message msg){
            if(msg.what==1){
                textView1.setText("个人主页");
                textView2.setText(user.getSname());
                imageView2.setImageBitmap(bitmap);
                imageView2.setVisibility(View.VISIBLE);
            }
            else if(msg.what==2){
                textView1.setText("匿名用户");
                textView2.setText("登录/注册");
                textView2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(MoreActivity.this,LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
            }else if (msg.what==3){
                Toast.makeText(MoreActivity.this,"请检查网络连接",Toast.LENGTH_SHORT).show();
            }else if (msg.what==4){
                Toast.makeText(MoreActivity.this, "上传成功", Toast.LENGTH_SHORT).show();
            }
        }
    };
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.more);

        imageView2=(CircleImageView)findViewById(R.id.more_img2);
        imageView2.setVisibility(View.INVISIBLE);
        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Isme&&!Sid.equals("-1")) {
                    Intent intent = new Intent();
                    //intent = new Intent(Intent.ACTION_GET_CONTENT);
                /* 开启Pictures画面Type设定为image */
                    intent.setType("image/*");
                /* 使用Intent.ACTION_GET_CONTENT这个Action */
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                /* 取得相片后返回本画面 */
                    startActivityForResult(intent, 1);
                }
            }
        });

        textView3=(TextView)findViewById(R.id.more_tx1);

        Intent intent =getIntent();

        preferences=getSharedPreferences("first_pref",MODE_PRIVATE);
        Isme=intent.getBooleanExtra("Isme",true);
        if (Isme==true) {
            Sid = preferences.getString("sid", " ");
        }else {
            Sid = intent.getStringExtra("user_id");
            if (Sid.equals(preferences.getString("sid", " "))){
                Isme=true;
            }
        }
        mySid=preferences.getString("sid", " ");
        if(Sid.equals("-1")==false){
            //子线程获取用户名,图片
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        BaseNetwork base = new BaseNetwork();//调用方法从数据库能获取所需的数据
                        Message ms = new Message();
                        user = base.getUserInformation(Sid).get(0);
                        bitmap=base.getImage(user.getImage());
                        ms.what = 1;
                        handler.sendMessage(ms);
                    }catch (NullPointerException e){
                        Message ms=new Message();
                        ms.what = 3;
                        handler.sendMessage(ms);
                    }catch (IOException e){
                        Message ms=new Message();
                        ms.what = 3;
                        handler.sendMessage(ms);
                    }
                }
            }).start();
        }
        else if(Sid.equals("-1")==true){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Message ms=new Message();
                    ms.what = 2;
                    handler.sendMessage(ms);
                }
            }).start();
        }


        textView1=(TextView)findViewById(R.id.more_tx1);
        textView2=(TextView)findViewById(R.id.more_tx2);


        // Initialize the ViewPager and set an adapter
        ViewPager pager = (ViewPager) findViewById(R.id.person_info_pager);
        pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));

        // Bind the tabs to the ViewPager
        PagerSlidingTabStrip tabs = (PagerSlidingTabStrip) findViewById(R.id.person_info_tabs);
        tabs.setViewPager(pager);

        imageView=(ImageView)findViewById(R.id.more_img1);
        imageView.setOnClickListener(new View.OnClickListener() {//点击设置按钮会提示进入编辑个人页面和退出页面
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog=new AlertDialog.Builder(MoreActivity.this).setTitle("设置").setItems(new String[]{"进入个人编辑页面", "退出登录"}, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i) {

                            case 0://进入个人编辑页面
                                Intent intent=new Intent(MoreActivity.this,Edit_personal_informationActivity.class);
                                intent.putExtra("Bitmap",bitmap);
                                startActivity(intent);
                                break;

                            case 1://进入退出页面
                                // 创建退出对话框
                                AlertDialog isExit = new AlertDialog.Builder(MoreActivity.this).create();
                                // 设置对话框标题
                                isExit.setTitle("系统提示");
                                // 设置对话框消息
                                isExit.setMessage("确定要退出吗");
                                // 添加选择按钮并注册监听
                                isExit.setButton("确定", listener);
                                isExit.setButton2("取消", listener);
                                // 显示对话框
                                isExit.show();
                                break;

                            default:

                                break;
                        }
                    }
                }).show();
            }

        });
        if(Isme==false||Sid.equals("-1")){
            imageView.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        if (requestCode == REQUEST_OK) {
            Uri selectedImage=null;
            try {
                selectedImage = data.getData();
            }
            catch (Exception e) {
                    Toast.makeText(this, "请选择图片", Toast.LENGTH_SHORT).show();
                }
            try {
                    final Bitmap bitmap= BitmapFactory.decodeStream(this.getContentResolver().openInputStream(Uri.parse(selectedImage.toString())));
                final String fileName=selectedImage.toString();
                    imageView2.setImageBitmap(bitmap);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            BaseNetwork baseNetwork=new BaseNetwork();
                            String address =baseNetwork.uploadFile("Aupload",MoreActivity.this,bitmap,fileName,mySid);
                            if (baseNetwork.modifyImage(mySid,address)) {
                                Message ms = new Message();
                                ms.what = 4;
                                handler.sendMessage(ms);
                            }
                        }
                    }).start();
            }
            catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**监听对话框里面的button点击事件*/
        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case AlertDialog.BUTTON_POSITIVE:// "确认"按钮退出程序
                        preferences1 = getSharedPreferences("first_pref", MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences1.edit();
                        editor.clear();
                        editor.commit();
                        ActivityCollector.finishAll();
                        Intent intent = new Intent(MoreActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    case AlertDialog.BUTTON_NEGATIVE:// "取消"第二个按钮取消对话框
                        break;
                    default:
                        break;
                }
            }
        };

    public class MyPagerAdapter extends FragmentPagerAdapter {

        private final String[] TITLES = {"个人主页","详细信息"};

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
            return MorePagerFragment.newInstance(position,Isme);
        }
    }
}
