package edu.zhdata.android.zhapp;

import android.content.Intent;
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
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import edu.zhdata.android.zhapp.entity.MainList;
import edu.zhdata.android.zhapp.entity.TopicQlist;
import edu.zhdata.android.zhapp.entity.TopicWaitQlist;
import edu.zhdata.android.zhapp.tools.BaseNetwork;
import edu.zhdata.android.zhapp.tools.BaseNetwork1;

/**
 * Created by ck on 2017/3/11.
 */

public class TopicFragmentActivity extends Fragment{
    private static final String ARG_POSITION = "position";

    private List<String> getDatalist1,getDatalist2,getDatalist3;
    private List<String> datalist1=new ArrayList<String>();
    private List<String> datalist2=new ArrayList<String>();
    private List<String> datalist3=new ArrayList<String>();
    private ArrayAdapter array;
    private ListView lv;
    private ArrayList<String> arraylist;
    private int tid;
    private String sid;

    private int position;

    public static TopicFragmentActivity newInstance(int position) {
        TopicFragmentActivity f = new TopicFragmentActivity();
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
                array = new ArrayAdapter<String>(getActivity(), R.layout.support_simple_spinner_dropdown_item, getDatalist1);
                lv.setAdapter(array);
            }
            else if(message.what==2){
                array = new ArrayAdapter<String>(getActivity(), R.layout.support_simple_spinner_dropdown_item, getDatalist2);
                lv.setAdapter(array);
            }
            else if(message.what==3){
                array = new ArrayAdapter<String>(getActivity(), R.layout.support_simple_spinner_dropdown_item, getDatalist3);
                lv.setAdapter(array);
            }else if(message.what==4){
                Toast.makeText(getActivity(),"请检查网络连接",Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_my_focus_fragment,container,false);
        ButterKnife.bind(this, rootView);
        ViewCompat.setElevation(rootView, 50);

        TopicActivity topic=(TopicActivity)getActivity();

        sid=topic.s_id;
        tid=Integer.parseInt(topic.t_id);

        lv = (ListView) rootView.findViewById(R.id.activity_my_focus_fragment_list);
        final BaseNetwork baseNetwork=new BaseNetwork();
        final BaseNetwork1 baseNetwork1=new BaseNetwork1();
        if(position==0) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        List<TopicQlist> list = baseNetwork1.getTopicQlist(tid);
                        getDatalist1 = new ArrayList<String>();
                        for (int i = 0; i < list.size(); i++) {
                            getDatalist1.add(list.get(i).getQname());
                            datalist1.add(list.get(i).getQid());
                        }
                        Message ms = new Message();
                        ms.what = 1;
                        handler.sendMessage(ms);
                    }catch (NullPointerException e){
                        Message ms = new Message();
                        ms.what = 4;
                        handler.sendMessage(ms);
                    }
                }
            }).start();

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent=new Intent(getActivity(),Question_page_Activity.class);
                    intent.putExtra("Qid",datalist1.get(i));
                    startActivity(intent);

                }
            });
        }
        else if(position==1){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        List<MainList> list = baseNetwork.dynamicHotQuestion(sid, tid);
                        getDatalist2 = new ArrayList<String>();
                        for (int i = 0; i < list.size(); i++) {
                            getDatalist2.add(list.get(i).getQname());
                            datalist2.add(list.get(i).getQid() + "");
                        }
                        Message ms = new Message();
                        ms.what = 2;
                        handler.sendMessage(ms);
                    }catch (NullPointerException e){
                        Message ms = new Message();
                        ms.what = 4;
                        handler.sendMessage(ms);
                    }
                }
            }).start();

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent=new Intent(getActivity(),Question_page_Activity.class);
                    intent.putExtra("Qid",datalist2.get(i));
                    startActivity(intent);

                }
            });
        }
        else if(position==2){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        List<TopicWaitQlist> list = baseNetwork1.getTopicWaitQlist(tid);
                        getDatalist3 = new ArrayList<String>();
                        for (int i = 0; i < list.size(); i++) {
                            getDatalist3.add(list.get(i).getQname());
                            datalist3.add(list.get(i).getQid());
                        }
                        Message ms = new Message();
                        ms.what = 3;
                        handler.sendMessage(ms);
                    }catch (NullPointerException e){
                        Message ms = new Message();
                        ms.what = 4;
                        handler.sendMessage(ms);
                    }
                }
            }).start();

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent=new Intent(getActivity(),Question_page_Activity.class);
                    intent.putExtra("Qid",datalist3.get(i));
                    startActivity(intent);

                }
            });
        }
        return rootView;
    }

}
