package edu.zhdata.android.zhapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.zhdata.android.zhapp.entity.College_Major;
import edu.zhdata.android.zhapp.entity.User;
import edu.zhdata.android.zhapp.tools.BaseNetwork;
import edu.zhdata.android.zhapp.tools.BaseNetwork1;

/**
 * Created by ck on 2017/3/1.
 */

public class Edit_personal_informationActivity extends Activity{

    private Spinner spinner1,spinner2,spinner3,spinner4;
    private List<String> datalist,datalist2,datalist3,datalist4;
    private ArrayAdapter<String> arrayAdapter,arrayAdapter2,arrayAdapter3,arrayAdapter4;
    private EditText et1,et2,et3,et4,et5,et6,et7,et8,et9;
    private RadioButton ra1,ra2;
    private List<User> userList;
    private HashMap<String,Object> map;
    private int sex;
    private ImageView imageView;
    private Date date;
    private DatePicker datePicker;
    private SharedPreferences preferences,preferences1;
    private boolean aBoolean=false;
    private Bitmap bitmap;


    Handler handler=new Handler(){
        public void handleMessage(Message message){
            if(message.what==1){
                try {
                    et1.setText(userList.get(0).getSname());
                    Date birth=userList.get(0).getBirthday();
                    int yy=birth.getYear();
                    int mm=birth.getMonth();
                    int dd=birth.getDate();

                    datePicker.updateDate(yy+1900,mm,dd);

                    et6.setText(userList.get(0).getPhonenum());
                    et7.setText(userList.get(0).getEaddress());
                    et8.setText(userList.get(0).getSafekey1());
                    et9.setText(userList.get(0).getSafekey2());
                    if(userList.get(0).getSex()==1)
                        ra2.setChecked(true);
                    else
                        ra1.setChecked(true);
                    spinner1.setSelection(userList.get(0).getSafequestion1());
                    spinner2.setSelection(userList.get(0).getSafequestion2());
                    BaseNetwork baseNetwork=new BaseNetwork();
                    List<College_Major> college_majorList= baseNetwork.getCollege_Major(Edit_personal_informationActivity.this);
                    for (int i=0;i<college_majorList.size();i++){
                        if (college_majorList.get(i).getCid()==userList.get(0).getCollege()){
                            spinner3.setSelection(i);
                            for (int j=0;j<college_majorList.get(i).getMajorList().size();j++){
                                if (college_majorList.get(i).getMajorList().get(j).getMid()==userList.get(0).getMajor()){
                                    spinner4.setSelection(j);
                                    break;
                                }
                            }
                            break;
                        }
                    }
                }
                catch (NullPointerException e){
                    Toast.makeText(Edit_personal_informationActivity.this, "请检查网络连接", Toast.LENGTH_SHORT).show();
                }
            }
            else if(message.what==2){
                Toast.makeText(Edit_personal_informationActivity.this, "提交成功", Toast.LENGTH_SHORT).show();
                finish();
            }
            else if(message.what==3){
                Toast.makeText(Edit_personal_informationActivity.this, "提交失败", Toast.LENGTH_SHORT).show();
            }
            else if(message.what==4){
                Toast.makeText(Edit_personal_informationActivity.this, "请检查网络连接", Toast.LENGTH_SHORT).show();
            }
        }
    };

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_personal_information);
        Intent intent=getIntent();
        bitmap=intent.getParcelableExtra("Bitmap");
        et1=(EditText)findViewById(R.id.edit_personal_information_et1);//用户名
        et1.setFocusable(false);
        et1.setFocusableInTouchMode(false);

        et6=(EditText)findViewById(R.id.edit_personal_information_et6);//手机号
        et7=(EditText)findViewById(R.id.edit_personal_information_et7);//邮箱
        et8=(EditText)findViewById(R.id.edit_personal_information_et8);//密保一回答
        et9=(EditText)findViewById(R.id.edit_personal_information_et9);//密保二回答

        ra1=(RadioButton)findViewById(R.id.radioButton5);
        ra2=(RadioButton)findViewById(R.id.radioButton6);

        imageView=(ImageView)findViewById(R.id.edit_personal_information_img3);
        imageView.setImageBitmap(bitmap);
        datePicker=(DatePicker)findViewById(R.id.datePicker);

        preferences1=getSharedPreferences("firstedit",MODE_PRIVATE);


        spinner1=(Spinner)findViewById(R.id.edit_personal_information_spinner1);//密保一
        spinner2=(Spinner)findViewById(R.id.edit_personal_information_spinner2);//密保2
        spinner3=(Spinner)findViewById(R.id.edit_personal_information_spinner3);//学院
        spinner4=(Spinner)findViewById(R.id.edit_personal_information_spinner4);//专业
        datalist=new ArrayList<String>();
        datalist.add("你的小学的名称是什么？");
        datalist.add("你最喜欢的电影是什么？");
        datalist.add("你最喜欢的人是谁？");

        datalist2=new ArrayList<String>();
        datalist2.add("你高中的名字是什么？");
        datalist2.add("你大学的名字是什么？");
        datalist2.add("你最喜欢的演员是谁？");

//        datalist3=new ArrayList<String>();
//        datalist3.add("计算机学院");
//        datalist3.add("土木工程");
//        datalist3.add("建筑学院");
//        datalist3.add("商学院");
//        datalist3.add("化工学院");
//        datalist3.add("英语学院");
//
//        datalist4=new ArrayList<String>();
//        datalist4.add("计算机科学与技术");
//        datalist4.add("软件工程");
//        datalist4.add("网络工程");

        setDatalist3();
        datalist4=new ArrayList<String>();
        setDatalist4(0);

        arrayAdapter=new ArrayAdapter<String>(Edit_personal_informationActivity.this,R.layout.support_simple_spinner_dropdown_item,datalist);
        arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner1.setAdapter(arrayAdapter);

        arrayAdapter2=new ArrayAdapter<String>(Edit_personal_informationActivity.this,R.layout.support_simple_spinner_dropdown_item,datalist2);
        arrayAdapter2.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner2.setAdapter(arrayAdapter2);

        arrayAdapter3=new ArrayAdapter<String>(Edit_personal_informationActivity.this,R.layout.support_simple_spinner_dropdown_item,datalist3);
        arrayAdapter3.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner3.setAdapter(arrayAdapter3);
        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setDatalist4(position);
                arrayAdapter4.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        arrayAdapter4=new ArrayAdapter<String>(Edit_personal_informationActivity.this,R.layout.support_simple_spinner_dropdown_item,datalist4);
        arrayAdapter4.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner4.setAdapter(arrayAdapter4);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    BaseNetwork baseNetwork=new BaseNetwork();
                    preferences=getSharedPreferences("first_pref",MODE_PRIVATE);
                    userList=baseNetwork.getUserInformation(preferences.getString("sid", " "));
                    Message message=new Message();
                    message.what=1;
                    handler.sendMessage(message);
                }
                catch (NullPointerException e){
                    Message message=new Message();
                    message.what=4;
                    handler.sendMessage(message);
                }
            }
        }).start();

    }

    public void click(View view){
        switch (view.getId()){
            case R.id.edit_personal_information_img1://返回到更多界面
                finish();
                break;
            case R.id.edit_personal_information_img2://提交数据
                int year;
                int month;
                int day;
                year=datePicker.getYear();
                month=datePicker.getMonth();
                day=datePicker.getDayOfMonth();

                date = new Date(year-1900,month,day);
                if((et8.getText().toString().equals("")==false&&et9.getText().toString().equals("")==false)||preferences1.getBoolean("isFirstEdit",false)==false) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            BaseNetwork1 baseNetwork1 = new BaseNetwork1();
                            if (ra1.isChecked())
                                sex = 0;
                            else
                                sex = 1;

                            BaseNetwork baseNetwork=new BaseNetwork();
                            List<College_Major> college_majorList= baseNetwork.getCollege_Major(Edit_personal_informationActivity.this);
                            boolean is = baseNetwork1.modifyUserInformation(preferences.getString("sid", " "), userList.get(0).getPassword(), college_majorList.get(spinner3.getSelectedItemPosition()).getMajorList().get(spinner4.getSelectedItemPosition()).getMid(), college_majorList.get(spinner3.getSelectedItemPosition()).getCid(), spinner1.getSelectedItemPosition(), spinner2.getSelectedItemPosition(), et8.getText().toString(), et9.getText().toString(), userList.get(0).getImage(), et6.getText().toString(), et7.getText().toString(), sex, date, et1.getText().toString());
                            Message message = new Message();
                            if (is == true) {
                                message.what = 2;
                            } else
                                message.what = 3;
                            handler.sendMessage(message);
                        }
                    }).start();
                    SharedPreferences.Editor e=preferences1.edit();
                    e.putBoolean("isFirstEdit",false);
                    e.commit();
                }
                else if((et8.getText().toString().equals("")||et9.getText().toString().equals(""))&&preferences1.getBoolean("isFirstEdit",false)==true){
                    Toast.makeText(this, "第一次修改请设置密保问题", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    private void setDatalist3(){
        BaseNetwork baseNetwork=new BaseNetwork();
        List<College_Major> college_majorList= baseNetwork.getCollege_Major(this);
        datalist3=new ArrayList<String>();
        for (int i=0;i<college_majorList.size();i++){
            datalist3.add(college_majorList.get(i).getCname());
        }
    }
    private void setDatalist4(int position){
        BaseNetwork baseNetwork=new BaseNetwork();
        List<College_Major> college_majorList= baseNetwork.getCollege_Major(this);
        datalist4.clear();
        for (int i=0;i<college_majorList.get(position).getMajorList().size();i++){
            datalist4.add(college_majorList.get(position).getMajorList().get(i).getMname());
        }
    }
}
