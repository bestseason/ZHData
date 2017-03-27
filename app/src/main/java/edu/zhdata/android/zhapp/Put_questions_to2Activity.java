package edu.zhdata.android.zhapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.zhdata.android.zhapp.entity.STopic;
import edu.zhdata.android.zhapp.tools.BaseNetwork;
import edu.zhdata.android.zhapp.tools.BaseNetwork1;

/**
 * Created by ck on 2017/3/1.
 */

public class Put_questions_to2Activity extends Activity {
    ArrayAdapter<String> arrayAdapter;
    private ListView list;
    private ImageView iv;
    private AutoCompleteTextView autoCompleteTextView;
    private ArrayList<String> datalist=new ArrayList<String>();
    private String Sid;
    private String title,content;
    private ArrayAdapter<String> arrayAdapter1;
    private List<STopic> arr;
    private ArrayList<String> arrayList;
    private HashMap<String,Object> getTid;
    private List<String> Tid;
    private Date date;
    private SharedPreferences preferences;
    private int qid;

    Handler handler=new Handler(){
      public void handleMessage(Message msg){
          if(msg.what==0){
              arrayAdapter = new ArrayAdapter<String>(Put_questions_to2Activity.this,R.layout.autocompletetextview,arrayList);
              autoCompleteTextView.setAdapter(arrayAdapter);
          }
          else if(msg.what==1){
              Toast.makeText(Put_questions_to2Activity.this, "搜索话题失败", Toast.LENGTH_SHORT).show();
          }
          if(msg.what==2){
              Toast.makeText(Put_questions_to2Activity.this, "提交成功", Toast.LENGTH_SHORT).show();
              Intent intent=new Intent(Put_questions_to2Activity.this,Question_page_Activity.class);
              intent.putExtra("Qid",qid+"");
              startActivity(intent);
              finish();
          }
          else if(msg.what==3){
              Toast.makeText(Put_questions_to2Activity.this, "提交失败", Toast.LENGTH_SHORT).show();
          }else if(msg.what==4){
              Toast.makeText(Put_questions_to2Activity.this, "请检查网络连接", Toast.LENGTH_SHORT).show();
          }
      }
    };


    Thread thread=new Thread(new Runnable() {
        @Override
        public void run() {//调用方法提交数据
            try {
                BaseNetwork baseNetwork = new BaseNetwork();
                Message ms = new Message();
                arr = baseNetwork.searchTopic("%", Sid);
                if (arr.size() != 0) {
                    arrayList = new ArrayList<String>();
                    getTid = new HashMap<String, Object>();
                    for (int i = 0; i < arr.size(); i++) {
                        arrayList.add(arr.get(i).getTname());
                        getTid.put(arr.get(i).getTname(), arr.get(i).getTid());
                    }
                    ms.what = 0;
                } else
                    ms.what = 1;

                handler.sendMessage(ms);
            }catch (NullPointerException e){
                Message ms=new Message();
                ms.what=4;
                handler.sendMessage(ms);
            }
        }
    });

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.put_questions_to2);

        Intent intent=getIntent();//获取提问1界面传送的数据
        title=intent.getStringExtra("title");
        content=intent.getStringExtra("content");
        preferences=getSharedPreferences("first_pref",MODE_PRIVATE);
        Sid=preferences.getString("sid"," ");
        date = new Date(System.currentTimeMillis());


//        SharedPreferences preferences=getSharedPreferences("personal",MODE_PRIVATE);
//        sid=Integer.parseInt(preferences.getString("sid",""));//获取登录注册时保存的用户名id

        list=(ListView)findViewById(R.id.put_questions_to2_list);

        autoCompleteTextView=(AutoCompleteTextView)findViewById(R.id.put_questions_to2_edit);

//        autoCompleteTextView.setD(((Resources)getBaseContext().getResources()).getColor(R.color.white));
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {//获取话题名放入datalist
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String str=autoCompleteTextView.getText().toString();
                int a=Compare(datalist,str);
                if(a==-1){
                    Toast.makeText(Put_questions_to2Activity.this, "该话题已添加", Toast.LENGTH_SHORT).show();
                }
                else {
                    datalist.add(str);
                }
                arrayAdapter1=new ArrayAdapter<String>(Put_questions_to2Activity.this,R.layout.support_simple_spinner_dropdown_item,datalist);
                list.setAdapter(arrayAdapter1);
                autoCompleteTextView.setText("");
            }
        });

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                deleteItem();
            }
        });

        thread.start();

    }
    public int Compare(ArrayList<String> list,String str){
        for(int a=0;a<list.size();a++){
            if(str.equals(list.get(a))){
                return -1;
            }
        }
        return 1;
    }

    private void deleteItem()
    {
        int size = datalist.size();
        if( size > 0 )
        {
            datalist.remove(datalist.size() - 1);
            arrayAdapter1.notifyDataSetChanged();
        }
    }

    public void click(View view){
        switch (view.getId()){
            case R.id.put_questions_to2_img1://返回到提问1界面
                finish();
                break;
            case R.id.put_questions_to2_img2://提交问题
                Tid=new ArrayList<String>();
                for(int i=0;i<datalist.size();i++){
                    Tid.add(getTid.get(datalist.get(i))+"");
                }
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            BaseNetwork1 baseNetwork1 = new BaseNetwork1();
                            qid = baseNetwork1.addQuestion(title, content, date, Tid, Sid);
                            Message ms = new Message();
                            if (qid != -1) {
                                ms.what = 2;
                            } else
                                ms.what = 3;
                            handler.sendMessage(ms);
                        }catch (NullPointerException e){
                            Message ms=new Message();
                            ms.what=4;
                            handler.sendMessage(ms);
                        }
                    }
                }).start();
                break;
            default:
                break;
        }
    }
}
