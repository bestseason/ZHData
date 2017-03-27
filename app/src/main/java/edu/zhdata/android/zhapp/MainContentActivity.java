package edu.zhdata.android.zhapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.zhdata.android.zhapp.entity.MainList;
import edu.zhdata.android.zhapp.tools.BaseNetwork;
import edu.zhdata.android.zhapp.tools.BaseNetwork1;

public class MainContentActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    boolean isLoading;
    private List<MainList> list=new ArrayList<MainList>();
    ArrayList<HashMap<String,Object>> datalist=new ArrayList<HashMap<String, Object>>();
    private String Sid="";
    private ArrayList<Integer> Tlist;
    private RecyclerViewAdapter adapter;
    private SharedPreferences preferences;

    private Handler handler = new Handler(){
        public void handleMessage(Message msg){
            if(msg.what==1){
                adapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
                adapter.notifyItemRemoved(adapter.getItemCount());
                isLoading = false;
            }else if(msg.what==2){
                isLoading=false;
                adapter.notifyItemRemoved(adapter.getItemCount());
            }else if(msg.what==3){
                swipeRefreshLayout.setRefreshing(false);
                isLoading=false;
                Toast.makeText(MainContentActivity.this,"请检查网络连接",Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_content);
        preferences=getSharedPreferences("first_pref",MODE_PRIVATE);
        Sid=preferences.getString("sid"," ");
        Intent intent=getIntent();
//        Sid=intent.getStringExtra("Sid");
        Tlist=intent.getIntegerArrayListExtra("Tlist");
        adapter = new RecyclerViewAdapter(this, datalist,Sid,Tlist);
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainContentActivity.this,SearchBar.class);
                startActivity(intent);
            }
        });
        recyclerView=(RecyclerView)findViewById(R.id.recyclerView);
        swipeRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.SwipeRefreshLayout);
        initView();
        initData();
    }

    public void initView() {
        setSupportActionBar(toolbar);

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
                        if (Sid.equals("-1") == true) {
                            BaseNetwork1 baseNetwork1 = new BaseNetwork1();
                            list = baseNetwork1.userDynamicTopicList(Tlist, Sid, startRow, 8);
                        } else {
                            list = base.mainpagedataBypost(Sid, startRow, 8);
                        }
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
                                map.put("Abad", list.get(i).getAbad());
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
                        Message ms = new Message();
                        ms.what = 3;
                        handler.sendMessage(ms);
                    }
                }
            }).start();

    }


}