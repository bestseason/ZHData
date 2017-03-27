package edu.zhdata.android.zhapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by ck on 2017/3/1.
 */

public class Put_questions_to1Activity extends Activity {
    private TextView textView;
    private EditText et1,et2;
    private String Sid;
    private SharedPreferences preferences;
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.put_questions_to1);
        et1=(EditText)findViewById(R.id.put_questions_to_edit1);
        et2=(EditText)findViewById(R.id.put_questions_to_edit2);

        preferences=getSharedPreferences("first_pref",MODE_PRIVATE);
        Sid=preferences.getString("sid","");
        Intent intent=getIntent();
//        Sid=intent.getStringExtra("Sid");
        textView=(TextView)findViewById(R.id.put_questions_to_tx3);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Sid.equals("-1")==false) {
                    if (et1.getText().toString().equals("") == false && et2.getText().toString().equals("") == false) {
                        Intent intent = new Intent(Put_questions_to1Activity.this, Put_questions_to2Activity.class);
                        intent.putExtra("title", et1.getText().toString());
                        intent.putExtra("content", et2.getText().toString());
//                intent.putExtra("Sid",Sid);
                        startActivity(intent);
                    } else {
                        Toast.makeText(Put_questions_to1Activity.this, "标题和内容可能为空，请输入内容", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(Put_questions_to1Activity.this, "匿名用户不能提问", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
