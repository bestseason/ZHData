package edu.zhdata.android.zhapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import edu.zhdata.android.zhapp.entity.MyFocusQuestion;
import edu.zhdata.android.zhapp.entity.MyFocusTopic;
import edu.zhdata.android.zhapp.tools.BaseNetwork1;

/**
 * Created by ck on 2017/3/9.
 */

public class MyFocusFragment extends Fragment {
    private static final String ARG_POSITION = "position";
    List<MyFocusQuestion> list1;
    List<MyFocusTopic> list2;
    private ListView lv;
    private ArrayAdapter array;
    private List<String> datalist;
    private CardView cardView;

    private String Sid;
    private List<String> getDatalist;

    private int position;

    public static MyFocusFragment newInstance(int position) {
        MyFocusFragment f = new MyFocusFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        position = getArguments().getInt(ARG_POSITION);

    }


    Handler handler=new Handler(){
        public void handleMessage(Message message){
            if(message.what==1){
                array = new ArrayAdapter<String>(getActivity(), R.layout.support_simple_spinner_dropdown_item, getDatalist);
                lv.setAdapter(array);
                if (Sid.equals("-1")){
                    cardView.setBackgroundResource(R.drawable.empty);
                }
            }else if(message.what==2){
                cardView.setBackgroundResource(R.drawable.empty);
                Toast.makeText(getActivity(),"请检查网络连接",Toast.LENGTH_SHORT).show();
            }
        }
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_my_focus_fragment,container,false);
        ButterKnife.bind(this, rootView);
        ViewCompat.setElevation(rootView, 50);

        MyFocus myFocus=(MyFocus)getActivity();
        Sid=myFocus.sid;
        cardView=(CardView)rootView.findViewById(R.id.activity_my_focus_cardview);
        lv = (ListView) rootView.findViewById(R.id.activity_my_focus_fragment_list);
        final BaseNetwork1 baseNetwork1=new BaseNetwork1();
        if(position==0) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        list1 = baseNetwork1.getMyFocusQuestion(Sid);
                        getDatalist = new ArrayList<String>();
                        for (int i = 0; i < list1.size(); i++) {
                            getDatalist.add(list1.get(i).getQname());
                        }
                        Message ms = new Message();
                        ms.what = 1;
                        handler.sendMessage(ms);
                    }catch (NullPointerException e){
                        Message message = new Message();
                        message.what = 2;
                        handler.sendMessage(message);
                    }
                }
            }).start();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getActivity(),Question_page_Activity.class);
                intent.putExtra("Qid",list1.get(position).getQid());
                startActivity(intent);
            }
        });
        }
        else{
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        list2 = baseNetwork1.getMyFocusTopic(Sid);
                        getDatalist = new ArrayList<String>();
                        for (int i = 0; i < list2.size(); i++) {
                            getDatalist.add(list2.get(i).getTname());
                        }
                        Message ms = new Message();
                        ms.what = 1;
                        handler.sendMessage(ms);
                    }catch (NullPointerException e){
                        Message message = new Message();
                        message.what = 2;
                        handler.sendMessage(message);
                    }
                }
            }).start();
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent=new Intent(getActivity(),TopicActivity.class);
                    intent.putExtra("Tid",list2.get(position).getTid());
                    intent.putExtra("Tname",list2.get(position).getTname());
                    startActivity(intent);
                }
            });
        }

//        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                switch (i){
//                    case 0:
//                        Intent intent1=new Intent(getActivity(),MyAnswerActivity.class);
//                        startActivity(intent1);
//                        break;
//                    case 1:
//                        Intent intent2=new Intent(getActivity(),MyQuestionActivity.class);
//                        startActivity(intent2);
//                        break;
//                    case 2:
//                        Intent intent3=new Intent(getActivity(),MyFocus.class);
//                        startActivity(intent3);
//                        break;
//                }
//            }
//        });

        return rootView;
    }
}
