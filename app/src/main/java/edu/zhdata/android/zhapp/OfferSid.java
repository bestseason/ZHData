package edu.zhdata.android.zhapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import edu.zhdata.android.zhapp.entity.User;
import edu.zhdata.android.zhapp.tools.BaseNetwork;

/**
 * Created by ck on 2017/3/20.
 */

public class OfferSid extends Activity {
    Handler handler=new Handler(){
        public void handleMessage(Message message){
            if(message.what==1){
                Intent intent=new Intent(OfferSid.this,LostPwdActivity.class);
                intent.putExtra("safequestion1",list.get(0).getSafequestion1());
                intent.putExtra("safequestion2",list.get(0).getSafequestion2());
                intent.putExtra("safekey1",list.get(0).getSafekey1());
                intent.putExtra("safekey2",list.get(0).getSafekey2());
                intent.putExtra("Sid",editText.getText().toString());
                intent.putExtra("major",list.get(0).getMajor());
                intent.putExtra("college",list.get(0).getCollege());
                intent.putExtra("image",list.get(0).getImage());
                intent.putExtra("phonenum",list.get(0).getPhonenum());
                intent.putExtra("eadress",list.get(0).getEaddress());
                intent.putExtra("sex",list.get(0).getSex());
                intent.putExtra("birthday",list.get(0).getBirthday().toString());
                intent.putExtra("sname",list.get(0).getSname());
                startActivity(intent);
            }
            else if(message.what==2) {
                Toast.makeText(OfferSid.this, "请检查网络连接", Toast.LENGTH_SHORT).show();
            }
        }
    };

    private EditText editText;
    private Button button;
    private List<User> list;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.offersid);
        editText=(EditText)findViewById(R.id.offersid_edit);
        button=(Button)findViewById(R.id.offersid_btn);
        button.setOnClickListener(new View.OnClickListener() {
         @Override
           public void onClick(View view) {
             new Thread(new Runnable() {
                 @Override
                 public void run() {
                     BaseNetwork baseNetwork=new BaseNetwork();
                     list=new ArrayList<User>();
                     list=baseNetwork.getUserInformation(editText.getText().toString());
                     Message message = new Message();
                     if(list.size()!=0) {
                         message.what = 1;
                     }
                     else {
                         message.what=2;
                     }
                     handler.sendMessage(message);
                 }
             }).start();
           }
         });

    }
}
