package edu.zhdata.android.zhapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import edu.zhdata.android.zhapp.entity.User;
import edu.zhdata.android.zhapp.tools.BaseNetwork1;

/**
 * Created by ck on 2017/2/28.
 */

public class Register_Activity extends Activity {
    Handler handler=new Handler(){
        public void handleMessage(Message msg){
            if(msg.what==1){
//                Toast.makeText(Register_Activity.this, "注册成功", Toast.LENGTH_SHORT).show();
//                preferences =getSharedPreferences("first_pref",MODE_PRIVATE);
//                SharedPreferences personalinfoamation=getSharedPreferences("first_pref",MODE_PRIVATE);
//                SharedPreferences.Editor editor=personalinfoamation.edit();
//                editor.putString("sid",et2.getText().toString());
//                editor.commit();
                Intent i1=new Intent(Register_Activity.this,Information_completeActivity.class);
                i1.putExtra("Sid",et2.getText().toString());
                i1.putExtra("Sname",et1.getText().toString());
                i1.putExtra("password",et3.getText().toString());
                startActivity(i1);
            }
            else if(msg.what==2){
                Toast.makeText(Register_Activity.this, "注册失败,账号已注册", Toast.LENGTH_SHORT).show();
            }
        }
    };

    private EditText et1,et2,et3,et4;
    private String pwd="",againpwd="",user="",sid="";
    private Button btn1,btn2;
    private SharedPreferences preferences;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        ActivityCollector.addActivity(this);
        et1=(EditText)findViewById(R.id.register_edit1);
        et2=(EditText)findViewById(R.id.register_edit2);
        et3=(EditText)findViewById(R.id.register_edit3);
        et4=(EditText)findViewById(R.id.register_edit4);

    }

    public void click(View v){
        switch (v.getId()){
            case R.id.register_btn1://进入登录界面
                Intent i=new Intent(this,LoginActivity.class);
                startActivity(i);
                break;
            case R.id.register_btn2://进入子线程进行数据请求
                user=et1.getText().toString();
                sid=et2.getText().toString();
                pwd=et3.getText().toString();
                againpwd=et4.getText().toString();
                if(user.equals("")||sid.equals("")||pwd.equals("")){
                    Toast.makeText(this, "注册信息不能为空", Toast.LENGTH_SHORT).show();
                }
                else if(pwd.equals(againpwd)){//当数据不为空且两次输入的密码相同进入子线程
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            BaseNetwork1 baseNetwork=new BaseNetwork1();
                            boolean is=false;
                            List<User> userList= baseNetwork.getUserInformation(et2.getText().toString());
                            if (userList.size()==0){
                                is=true;
                            }else {
                                is=false;
                            }
                            Message ms=new Message();
                            if(is==true){
                                ms.what=1;
                            }
                            else{
                                ms.what=2;
                            }
                            handler.sendMessage(ms);
                        }
                    }).start();
                }
                else {
                    Toast.makeText(this, "密码不一致，请重新输入", Toast.LENGTH_SHORT).show();
                }
        }
    }


}
