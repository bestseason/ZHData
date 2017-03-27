package edu.zhdata.android.zhapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.zhdata.android.zhapp.entity.MainList;
import edu.zhdata.android.zhapp.tools.BaseNetwork;

public class MyAnswerActivity extends Activity {
    private ImageView imageView;
    private ListView lv;
    ArrayList<HashMap<String,Object>> datalist=new ArrayList<HashMap<String, Object>>();
    private String sid="163680";
    private RecyclerViewAdapter adapter;
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    boolean isLoading;
    private List<MainList> list=new ArrayList<MainList>();
    private LinearLayout linearLayout;

    private Handler handler = new Handler(){
        public void handleMessage(Message msg){
            if(msg.what==1){
                adapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
                adapter.notifyItemRemoved(adapter.getItemCount());
                isLoading = false;

            }else if(msg.what==2){
                isLoading=false;
                swipeRefreshLayout.setRefreshing(false);
                recyclerView.setBackgroundResource(R.drawable.empty);
                adapter.notifyItemRemoved(adapter.getItemCount());
            }else if(msg.what==3){
                isLoading=false;
                swipeRefreshLayout.setRefreshing(false);
                recyclerView.setBackgroundResource(R.drawable.empty);
                Toast.makeText(MyAnswerActivity.this,"请检查网络连接",Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_answer);

        linearLayout=(LinearLayout)findViewById(R.id.activity_my_answer_bg);

        Intent intent=getIntent();
        sid=intent.getStringExtra("Sid");
        boolean is=intent.getBooleanExtra("isme",true);
        if(is==false){
            TextView tx=(TextView)findViewById(R.id.activity_my_answer_tx);
            tx.setText("他的回答");
        }





        imageView=(ImageView)findViewById(R.id.activity_my_answer_img1);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        adapter = new RecyclerViewAdapter(this, datalist);
        recyclerView=(RecyclerView)findViewById(R.id.activity_my_answer_list);
        swipeRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.A_SwipeRefreshLayout);
        initView();
        initData();
    }

    public void initView() {
        swipeRefreshLayout.setColorSchemeResources(R.color.mainColor);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                datalist.clear();
                getData(0);
            }
        });

        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
//                Log.d("test", "StateChanged = " + newState);


            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
//                Log.d("test", "onScrolled");

                int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
                if (lastVisibleItemPosition + 1 == adapter.getItemCount()) {
//                    Log.d("test", "loading executed");

                    boolean isRefreshing = swipeRefreshLayout.isRefreshing();
                    if (isRefreshing) {
                        //adapter.notifyItemRemoved(adapter.getItemCount());
                        return;
                    }
                    if (!isLoading) {
                        isLoading = true;
                        getData(adapter.getItemCount()-1);
//                        Log.d("test", "load more completed");
                    }
                }
            }
        });
    }


    public void initData() {
        swipeRefreshLayout.setRefreshing(true);
        getData(0);
    }

    /**
     * 获取数据
     */
    private void getData(final int startRow) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    BaseNetwork base = new BaseNetwork();//调用方法从数据库能获取所需的数据
                    list = base.myAnswerListLimited(sid, startRow, 8);
                    if (list.size() != 0) {
                        for (int i = 0; i < list.size(); i++) {//从放回的数据中提取自己所需要的数据，并放到datalist
                            HashMap<String, Object> map = new HashMap<String, Object>();
                            map.put("topic", "来自话题：" + list.get(i).getTlist().get(0).getTname());
                            map.put("title", list.get(i).getQname());
                            map.put("content", list.get(i).getAdetail());
                            map.put("up", list.get(i).getAgood() + " 赞同");
                            map.put("Qname", list.get(i).getQname());
                            map.put("Sname", list.get(i).getA_Sname());
                            map.put("Adetail", list.get(i).getAdetail());
                            map.put("Qid", list.get(i).getQid());
                            map.put("Sid", list.get(i).getSid());
                            map.put("Tid", list.get(i).getTlist().get(0).getTid());
                            map.put("Tname", list.get(i).getTlist().get(0).getTname());
                            map.put("Aid", list.get(i).getAid());
                            map.put("Agood", list.get(i).getAgood());
                            map.put("AgoodPersonal", list.get(i).isAgoodPersonal());
                            map.put("AbadPersonal", list.get(i).isAbadPersonal());
                            map.put("FocusQuestion", list.get(i).isFocusQuestion());
                            datalist.add(map);
                        }
                    }
                    if (list.size() == 0) {
                        Message ms = new Message();
                        ms.what = 2;
                        handler.sendMessage(ms);

                    } else {
                        Message ms = new Message();
                        ms.what = 1;
                        handler.sendMessage(ms);
                    }
                }catch (NullPointerException e){
                    Message ms=new Message();
                    ms.what=3;
                    handler.sendMessage(ms);
                }
            }
        }).start();

    }
}
