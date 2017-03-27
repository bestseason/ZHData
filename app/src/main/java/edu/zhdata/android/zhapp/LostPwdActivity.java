package edu.zhdata.android.zhapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.zhdata.android.zhapp.entity.User;
import edu.zhdata.android.zhapp.tools.BaseNetwork1;

public class LostPwdActivity extends Activity {
    private Spinner spinner1,spinner2;
    private ArrayList<String> datalist1,datalist2;
    private Button button;
    private ArrayAdapter<String> arrayAdapter1,arrayAdapter2;
    private EditText editText,editText1,editText2,editText3;
    private List<User> list;
    private HashMap<String,Object> map;
    private int safequestion1,safequestion2,sex,major,college;
    private String safekey1,safekey2,Sid,image,phonenum,eadress,sname,d;
    private Date date;

    Handler handler=new Handler(){
      public void handleMessage(Message message){
          if(message.what==1){
              Toast.makeText(LostPwdActivity.this, "密码修改成功", Toast.LENGTH_SHORT).show();
              Intent i=new Intent(LostPwdActivity.this,LoginActivity.class);
              startActivity(i);
              finish();
          }
          else {
              Toast.makeText(LostPwdActivity.this, "密码修改失败", Toast.LENGTH_SHORT).show();
          }
      }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lost_pwd);
        spinner1=(Spinner)findViewById(R.id.spinner3);
        spinner2=(Spinner)findViewById(R.id.spinner4);
        editText=(EditText)findViewById(R.id.editText);
        editText1=(EditText)findViewById(R.id.editText5);
        editText2=(EditText)findViewById(R.id.editText7);
        editText3=(EditText)findViewById(R.id.editText2);
        button=(Button)findViewById(R.id.offersid_btn);

        final Intent intent=getIntent();
        safequestion1=intent.getIntExtra("safequestion1",0);
        safequestion2=intent.getIntExtra("safequestion2",0);
        safekey1=intent.getStringExtra("safekey1");
        safekey2=intent.getStringExtra("safekey2");
        Sid=intent.getStringExtra("Sid");
        image=intent.getStringExtra("image");
        phonenum=intent.getStringExtra("phonenum");
        eadress=intent.getStringExtra("eadress");
        sname=intent.getStringExtra("sname");
        d=intent.getStringExtra("birthday");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd");
        java.util.Date da = null;
        try {
            da = format.parse(d);
        } catch (Exception e) {
            e.printStackTrace();
        }
        date = new Date(da.getTime());

        major=intent.getIntExtra("major",0);
        college=intent.getIntExtra("college",0);
        sex=intent.getIntExtra("sex",0);


        datalist1=new ArrayList<String>();
        datalist1.add("你是谁");
        datalist1.add("你有多丑");
        datalist1.add("你有多帅");

        datalist2=new ArrayList<String>();
        datalist2.add("你要睡觉吗");
        datalist2.add("你睡觉了吗");
        datalist2.add("你睡醒了吗");

        arrayAdapter1=new ArrayAdapter<String>(LostPwdActivity.this,R.layout.support_simple_spinner_dropdown_item,datalist1);
        arrayAdapter1.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner1.setAdapter(arrayAdapter1);

        arrayAdapter2=new ArrayAdapter<String>(LostPwdActivity.this,R.layout.support_simple_spinner_dropdown_item,datalist2);
        arrayAdapter2.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner2.setAdapter(arrayAdapter2);

        spinner1.setSelection(safequestion1);
        spinner2.setSelection(safequestion2);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editText1.getText().toString().equals("")==false&&editText2.getText().toString().equals("")==false&&editText3.getText().toString().equals(editText.getText().toString())) {
                    if (editText1.getText().toString().equals(safekey1) && editText2.getText().toString().equals(safekey2)) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                BaseNetwork1 baseNetwork1 = new BaseNetwork1();
                                boolean is = baseNetwork1.modifyUserInformation(Sid, editText.getText().toString(), major, college, safequestion1, safequestion2, safekey1, safekey2, image, phonenum, eadress, sex, date, sname);
                                Message message = new Message();
                                if (is == true) {
                                    message.what = 1;
                                } else {
                                    message.what = 2;
                                }
                                handler.sendMessage(message);
                            }
                        }).start();
                    }
                    else {
                        Toast.makeText(LostPwdActivity.this, "密保回答错误", Toast.LENGTH_SHORT).show();
                    }
                }
                else if(editText1.getText().toString().equals("")==false&&editText2.getText().toString().equals("")==false&&editText3.getText().toString().equals(editText.getText().toString())==false){
                    Toast.makeText(LostPwdActivity.this, "密码不一致", Toast.LENGTH_SHORT).show();
                }
                else if(editText3.getText().toString().equals("")||editText.getText().toString().equals("")){
                    Toast.makeText(LostPwdActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
                }
                else if(editText1.getText().toString().equals("")||editText2.getText().toString().equals("")){
                    Toast.makeText(LostPwdActivity.this, "密保回答不能为空", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
