package edu.zhdata.android.zhapp;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.ButterKnife;
import edu.zhdata.android.zhapp.entity.College_Major;
import edu.zhdata.android.zhapp.entity.PeopleFocusUser;
import edu.zhdata.android.zhapp.entity.User;
import edu.zhdata.android.zhapp.entity.UserFocusPeople;
import edu.zhdata.android.zhapp.tools.BaseNetwork;
import edu.zhdata.android.zhapp.tools.BaseNetwork1;

/**
 * Created by pink2 on 2017/3/3.
 */

public class MorePagerFragment extends Fragment{
    private static final String ARG_POSITION = "position";

    private ListView lv;
    private ArrayAdapter array;
    private List<String> datalist;
    private LinearLayout linearLayout;
    private Button button;
    private int count=0;

    TextView textView3,textView4;
    List<UserFocusPeople> userFocusPeopleList;
    List<PeopleFocusUser> peopleFocusUserList;

    private int position;
    private boolean isme;
    private List<HashMap<String,Object>> getdatalist;
    private HashMap<String,Object> map;
    private String sid,mySid;
    private List<College_Major> collegeMajorList;


    public static MorePagerFragment newInstance(int position,boolean isme) {
        MorePagerFragment f = new MorePagerFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        b.putBoolean("Isme",isme);
        f.setArguments(b);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        position = getArguments().getInt(ARG_POSITION);
        isme=getArguments().getBoolean("Isme");
        BaseNetwork baseNetwork=new BaseNetwork();
        collegeMajorList=baseNetwork.getCollege_Major(getContext());
    }


    Handler handler=new Handler(){
        public void handleMessage(Message message){
            if(message.what==1){
                SimpleAdapter simpleAdapter=new SimpleAdapter(getActivity(),getdatalist,R.layout.more_detail_information_listview,new String[]{"id","content"},new int[]{R.id.textView10,R.id.textView11});
                lv.setAdapter(simpleAdapter);
            }else if(message.what==2){
                textView3.setText(userFocusPeopleList.size()+"");
            }else if(message.what==3){
                textView4.setText(peopleFocusUserList.size()+"");
            }else if (message.what==4){
                Toast.makeText(getActivity(),"请检查网络连接",Toast.LENGTH_SHORT).show();
            }else if(message.what==11){
                Toast.makeText(getContext(), "关注成功", Toast.LENGTH_SHORT).show();
                button.setText("已关注");
                button.setTextColor(((Resources) getContext().getResources()).getColor(R.color.white));
                button.setBackgroundDrawable(((Resources) getContext().getResources()).getDrawable(R.drawable.focus_button_frame_p));
            }else if(message.what==12) {
                Toast.makeText(getContext(), "取消关注成功", Toast.LENGTH_SHORT).show();
                button.setText("关注");
                button.setTextColor(((Resources) getContext().getResources()).getColor(R.color.white));
                button.setBackgroundDrawable(((Resources) getContext().getResources()).getDrawable(R.drawable.focus_button_frame));
            }else if(message.what==13){
                button.setText("已关注");
                button.setTextColor(((Resources) getContext().getResources()).getColor(R.color.white));
                button.setBackgroundDrawable(((Resources) getContext().getResources()).getDrawable(R.drawable.focus_button_frame_p));
                ++count;
            }
        }
    };
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView=null;
        rootView= inflater.inflate(R.layout.more_fragment_card,container,false);
        ButterKnife.bind(this, rootView);
        ViewCompat.setElevation(rootView, 50);
        TextView textView1=(TextView)rootView.findViewById(R.id.more_frag_tx1);
        TextView textView2=(TextView)rootView.findViewById(R.id.more_frag_tx2);
        button=(Button)rootView.findViewById(R.id.focus_user_btn);



        LinearLayout linearLayout1=(LinearLayout)rootView.findViewById(R.id.more_fragment_linear1);
        LinearLayout linearLayout2=(LinearLayout)rootView.findViewById(R.id.more_fragment_linear2);

        MoreActivity moreActivity=(MoreActivity)getActivity();
        sid=moreActivity.Sid;
        mySid=moreActivity.mySid;
        textView3=(TextView)rootView.findViewById(R.id.textView15);
        textView4=(TextView)rootView.findViewById(R.id.textView17);
        new Thread(new Runnable() {
            @Override
            public void run() {
                BaseNetwork1 baseNetwork1=new BaseNetwork1();
                userFocusPeopleList=baseNetwork1.getUserFocusPeople(sid);
                if (userFocusPeopleList!=null) {
                    Message message = new Message();
                    message.what = 2;
                    handler.sendMessage(message);
                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                BaseNetwork1 baseNetwork1=new BaseNetwork1();
                peopleFocusUserList=baseNetwork1.getPeopleFocusUser(sid);
                if (peopleFocusUserList!=null) {
                    Message message = new Message();
                    message.what = 3;
                    handler.sendMessage(message);
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                BaseNetwork1 baseNetwork1=new BaseNetwork1();
                boolean is=baseNetwork1.isUserFocus(mySid,sid);
                Message message=new Message();
                if(is==true){
                    message.what=13;
                }
                handler.sendMessage(message);
            }
        }).start();

        lv=(ListView) rootView.findViewById(R.id.more_list);
        datalist=new ArrayList<String>();
        if(isme||sid.equals(mySid)==true) {
            if(position==0) {
                button.setVisibility(View.INVISIBLE);
                    datalist.add("我的回答");
                    datalist.add("我的提问");
                    datalist.add("我的关注");
                linearLayout1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getActivity(), My_FocusActivity.class);
                        intent.putExtra("Sid",sid);
                        startActivityForResult(intent,1);
                    }
                });
                linearLayout2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getActivity(), Focus_MyActivity.class);
                        intent.putExtra("Sid",sid);
                        startActivityForResult(intent,1);
                    }
                });
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        switch (i){
                            case 0:
                                Intent intent1=new Intent(getActivity(),MyAnswerActivity.class);
                                intent1.putExtra("Sid",sid);
                                startActivity(intent1);
                                break;
                            case 1:
                                Intent intent2=new Intent(getActivity(),MyQuestionActivity.class);
                                intent2.putExtra("Sid",sid);
                                startActivity(intent2);
                                break;
                            case 2:
                                Intent intent3=new Intent(getActivity(),MyFocus.class);
                                intent3.putExtra("Sid",sid);
                                startActivity(intent3);
                                break;
                        }
                    }
                });
            }
            else {
                rootView= inflater.inflate(R.layout.more_detail_information,container,false);
                ButterKnife.bind(this, rootView);
                ViewCompat.setElevation(rootView, 50);

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            BaseNetwork1 baseNetwork1 = new BaseNetwork1();
                            List<User> list = baseNetwork1.getUserInformation(sid);
                            if (list.size() != 0) {
                                getdatalist = new ArrayList<HashMap<String, Object>>();
                                BaseNetwork baseNetwork=new BaseNetwork();
                                List<College_Major> college_majorList=baseNetwork.getCollege_Major(getContext());
                                int collegenum=0;
                                int majornum=0;
                                for (int j=0;j<college_majorList.size();j++){
                                   if(college_majorList.get(j).getCid()==list.get(0).getCollege()){
                                       collegenum=j;
                                       break;
                                   }
                                }
                                for (int k=0;k<college_majorList.get(collegenum).getMajorList().size();k++){
                                    if(college_majorList.get(collegenum).getMajorList().get(k).getMid()==list.get(0).getMajor()){
                                        majornum=k;
                                        break;
                                    }
                                }
                                map = new HashMap<String, Object>();
                                map.put("id", "学院");
                                map.put("content", collegeMajorList.get(collegenum).getCname());
                                getdatalist.add(map);
                                map = new HashMap<String, Object>();
                                map.put("id", "专业");
                                map.put("content", collegeMajorList.get(collegenum).getMajorList().get(majornum).getMname());
                                getdatalist.add(map);
                                map = new HashMap<String, Object>();
                                map.put("id", "邮箱地址");
                                map.put("content", list.get(0).getEaddress());
                                getdatalist.add(map);
                                map = new HashMap<String, Object>();
                                map.put("id", "生日");
                                map.put("content", list.get(0).getBirthday());
                                getdatalist.add(map);
                                map = new HashMap<String, Object>();
                                map.put("id", "电话");
                                map.put("content", list.get(0).getPhonenum());
                                getdatalist.add(map);
                                Message message = new Message();
                                message.what = 1;
                                handler.sendMessage(message);
                            }
                        }catch (NullPointerException e){
                            Message message = new Message();
                            message.what = 4;
                            handler.sendMessage(message);
                        }
                    }
                }).start();
                lv=(ListView)rootView.findViewById(R.id.more_detail_information_list);


            }
        }else {
            if (position==0) {
                button.setVisibility(View.VISIBLE);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ++count;
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                BaseNetwork1 baseNetwork1 = new BaseNetwork1();
                                Message message = new Message();
                                boolean is = false;
                                if (count % 2 == 1) {
                                    is = baseNetwork1.focusUser(mySid, sid);
                                    if (is == true) {
                                        message.what = 11;
                                    }
                                } else if (count % 2 == 0) {
                                    is = baseNetwork1.undoFocusUser(mySid, sid);
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
                    }
                });
                datalist.add("他的回答");
                datalist.add("他的提问");
                datalist.add("他的关注");
                textView1.setText("他关注的人");
                textView2.setText("关注他的人");
                linearLayout1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getActivity(), My_FocusActivity.class);
                        intent.putExtra("Sid",sid);
                        intent.putExtra("isme",false);
                        startActivity(intent);
                    }
                });
                linearLayout2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getActivity(), Focus_MyActivity.class);
                        intent.putExtra("Sid",sid);
                        intent.putExtra("isme",false);
                        startActivity(intent);
                    }
                });
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        switch (i){
                            case 0:
                                Intent intent1=new Intent(getActivity(),MyAnswerActivity.class);
                                intent1.putExtra("Sid",sid);
                                intent1.putExtra("isme",false);
                                startActivity(intent1);
                                break;
                            case 1:
                                Intent intent2=new Intent(getActivity(),MyQuestionActivity.class);
                                intent2.putExtra("Sid",sid);
                                intent2.putExtra("isme",false);
                                startActivity(intent2);
                                break;
                            case 2:
                                Intent intent3=new Intent(getActivity(),MyFocus.class);
                                intent3.putExtra("Sid",sid);
                                intent3.putExtra("isme",false);
                                startActivity(intent3);
                                break;
                        }
                    }
                });
            }
            else {
                rootView= inflater.inflate(R.layout.more_detail_information,container,false);
                ButterKnife.bind(this, rootView);
                ViewCompat.setElevation(rootView, 50);

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            BaseNetwork1 baseNetwork1 = new BaseNetwork1();
                            List<User> list = baseNetwork1.getUserInformation(sid);
                            getdatalist = new ArrayList<HashMap<String, Object>>();
                            BaseNetwork baseNetwork=new BaseNetwork();
                            List<College_Major> college_majorList=baseNetwork.getCollege_Major(getContext());
                            int collegenum=0;
                            int majornum=0;
                            for (int j=0;j<college_majorList.size();j++){
                                if(college_majorList.get(j).getCid()==list.get(0).getCollege()){
                                    collegenum=j;
                                    break;
                                }
                            }
                            for (int k=0;k<college_majorList.get(collegenum).getMajorList().size();k++){
                                if(college_majorList.get(collegenum).getMajorList().get(k).getMid()==list.get(0).getMajor()){
                                    majornum=k;
                                    break;
                                }
                            }
                            map = new HashMap<String, Object>();
                            map.put("id", "学院");
                            map.put("content", collegeMajorList.get(collegenum).getCname());
                            getdatalist.add(map);
                            map = new HashMap<String, Object>();
                            map.put("id", "专业");
                            map.put("content", collegeMajorList.get(collegenum).getMajorList().get(majornum).getMname());
                            getdatalist.add(map);
                            map = new HashMap<String, Object>();
                            map.put("id", "邮箱地址");
                            map.put("content", list.get(0).getEaddress());
                            getdatalist.add(map);
                            map = new HashMap<String, Object>();
                            map.put("id", "生日");
                            map.put("content", list.get(0).getBirthday());
                            getdatalist.add(map);
                            map = new HashMap<String, Object>();
                            map.put("id", "电话");
                            map.put("content", list.get(0).getPhonenum());
                            getdatalist.add(map);
                            Message message = new Message();
                            message.what = 1;
                            handler.sendMessage(message);
                        } catch (NullPointerException e) {
                            Message message = new Message();
                            message.what = 4;
                            handler.sendMessage(message);
                        }
                    }
                }).start();
                lv=(ListView)rootView.findViewById(R.id.more_detail_information_list);
//                handler=new Handler(){
//                    public void handleMessage(Message message){
//                        if(message.what==1){
//                            SimpleAdapter simpleAdapter=new SimpleAdapter(getActivity(),getdatalist,R.layout.more_detail_information_listview,new String[]{"id","content"},new int[]{R.id.textView10,R.id.textView11});
//                            lv.setAdapter(simpleAdapter);
//                        }
//                    }
//                };
            }
        }

        array=new ArrayAdapter<String>(getActivity(),R.layout.more_arrayadapter,datalist);
        lv.setAdapter(array);



        return rootView;
    }
    public class MyAdapter extends BaseAdapter{
        Context context;
        HashMap<String,Object> map;

        public MyAdapter(Context context,HashMap<String,Object> map){
            this.context=context;
            this.map=map;
        }

        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder holder;
            LayoutInflater inflater=(LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view=inflater.inflate(R.layout.more_detail_information_listview,null);
            holder=new ViewHolder();
            holder.textView1=(TextView)view.findViewById(R.id.textView10);
            holder.textView2=(TextView)view.findViewById(R.id.textView11);
            view.setTag(holder);
            return view;
        }
        class ViewHolder{
            TextView textView1,textView2;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        new Thread(new Runnable() {
            @Override
            public void run() {
                BaseNetwork1 baseNetwork1=new BaseNetwork1();
                userFocusPeopleList=baseNetwork1.getUserFocusPeople(sid);
                if (userFocusPeopleList!=null) {
                    Message message = new Message();
                    message.what = 2;
                    handler.sendMessage(message);
                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                BaseNetwork1 baseNetwork1=new BaseNetwork1();
                peopleFocusUserList=baseNetwork1.getPeopleFocusUser(sid);
                if (peopleFocusUserList!=null) {
                    Message message = new Message();
                    message.what = 3;
                    handler.sendMessage(message);
                }
            }
        }).start();
    }
}
