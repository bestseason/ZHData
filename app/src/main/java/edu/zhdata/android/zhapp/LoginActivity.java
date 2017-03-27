package edu.zhdata.android.zhapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import edu.zhdata.android.zhapp.tools.BaseNetwork1;

/**
 * Created by ck on 2017/2/28.
 */

public class LoginActivity extends Activity{
    Handler handler=new Handler(){
        public void handleMessage(Message msg){
            if(msg.what==1){
                preferences=getSharedPreferences("first_pref",MODE_PRIVATE);
                editor=preferences.edit();
                editor.putBoolean("isFirstIn",false);
                editor.putString("sid",et1.getText().toString());
                editor.commit();
                preferences1=getSharedPreferences("firstedit",MODE_PRIVATE);
                SharedPreferences.Editor editor=preferences1.edit();
                editor.putBoolean("isFirstEdit",true);
                editor.commit();
                Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                Intent i=new Intent(LoginActivity.this,MainActivity.class);
                startActivity(i);
                finish();
            }
            else if(msg.what==2){
                Toast.makeText(LoginActivity.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
            }
        }
    };

    private String student_number="123456789";
    private String password="123789";
    private String user=null,pwd=null;
    private EditText et1,et2;
    private TextView tx1;
    private SharedPreferences.Editor editor;
    private SharedPreferences preferences,preferences1;
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        ActivityCollector.addActivity(this);
        et1=(EditText)findViewById(R.id.login_edit2);
        et2=(EditText)findViewById(R.id.login_edit1);
        tx1=(TextView)findViewById(R.id.lost_pwd_tx);
        tx1.setVisibility(View.VISIBLE);
        tx1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1=new Intent(LoginActivity.this,OfferSid.class);
                startActivity(i1);
            }
        });
    }
    public void click(View v){
        switch (v.getId()){
            case R.id.login_btn2://点击登录按钮
                new Thread(new Runnable() {//子线程中请求数据
                    @Override
                    public void run() {
                        BaseNetwork1 baseNetwork1=new BaseNetwork1();
                        boolean is=baseNetwork1.Login(et1.getText().toString(),et2.getText().toString());
                        Message ms=new Message();
                        if(is==true){
                            ms.what=1;
                        }
                        else {
                            ms.what=2;
                        }
                        handler.sendMessage(ms);
                    }
                }).start();
                break;
            case R.id.login_btn1://进入注册界面
                Intent i=new Intent(LoginActivity.this,Register_Activity.class);
                startActivity(i);
                break;
            default:
                break;
        }
    }

}


