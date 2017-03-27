package edu.zhdata.android.zhapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.text.Html;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class RecyclerViewAdapter extends Adapter<ViewHolder> {
    //设置字体
    Typeface face;

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;
    private Context context;
    private ArrayList<HashMap<String,Object>> data;
//    private String Sid;
    private ArrayList<Integer> Tlist;

    public RecyclerViewAdapter(Context context, ArrayList<HashMap<String,Object>> data) {
        this.context = context;
        this.data = data;
        face = Typeface.createFromAsset (context.getAssets() , "fonts/msyhl.ttc" );
    }

    public RecyclerViewAdapter(Context context, ArrayList<HashMap<String,Object>> data,String Sid,ArrayList<Integer> Tlist) {
        this.context = context;
        this.data = data;
//        this.Sid=Sid;
        this.Tlist=Tlist;
        face = Typeface.createFromAsset (context.getAssets() , "fonts/msyhl.ttc" );
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public int getItemCount() {
        return data.size() == 0 ? 0 : data.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View view = LayoutInflater.from(context).inflate(R.layout.activity_main_adapter, parent,
                    false);
            return new ItemViewHolder(view);
        } else if (viewType == TYPE_FOOTER) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_foot, parent,
                    false);
            return new FootViewHolder(view);
        }
        return null;
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder,final int position) {
        if (holder instanceof ItemViewHolder) {
            ((ItemViewHolder) holder).tv.setText(data.get(position).get("topic")+"");
            ((ItemViewHolder) holder).tv1.setText(data.get(position).get("title")+"");
            ((ItemViewHolder) holder).tv1.setTypeface (face);
            TextPaint tpaint = ((ItemViewHolder) holder).tv1.getPaint();
            tpaint.setFakeBoldText(true);
            char ch[]=data.get(position).get("content").toString().toCharArray();
            char ch1[]=new char[ch.length];
            int j=0;
            for(int i=0;i<ch.length;i++) {
                if (ch[i] == '<' && ch[i + 1] == 'i' && ch[i + 2] == 'm' && ch[i + 3] == 'g') {
                    ch1[j++]='[';
                    ch1[j++]='图';
                    ch1[j++]='片';
                    ch1[j++]=']';
                    while (ch[i] != '>') {
                        i++;
                    }
                }else {
                    ch1[j++] = ch[i];
                }
            }
            String chdata= new String(ch1);
            ((ItemViewHolder) holder).tv2.setText(Html.fromHtml(chdata.toString()));
            ((ItemViewHolder) holder).tv2.setTypeface (face);
            ((ItemViewHolder) holder).tv3.setText(data.get(position).get("up")+"");
            ((ItemViewHolder) holder).tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context,TopicActivity.class);
                    intent.putExtra("Tid",data.get(position).get("Tid")+"");
//                    intent.putExtra("Sid",data.get(position).get("Sid")+"");
                    intent.putExtra("Tname",data.get(position).get("Tname")+"");
                    context.startActivity(intent);
                }
            });
            ((ItemViewHolder) holder).tv1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, Question_page_Activity.class);
                    intent.putExtra("Qid",data.get(position).get("Qid")+"");
//                    intent.putExtra("A_Sid",data.get(position).get("Sid")+"");
                    intent.putExtra("Aid",data.get(position).get("Aid")+"");
                    intent.putExtra("Agood",data.get(position).get("Agood")+"");
                    intent.putExtra("AgoodPersonal",data.get(position).get("AgoodPersonal")+"");
                    intent.putExtra("AbadPersonal",data.get(position).get("AbadPersonal")+"");
                    intent.putExtra("FoucsQuestion",data.get(position).get("FoucsQuestion")+"");
                    context.startActivity(intent);
                }
            });
            ((ItemViewHolder) holder).tv2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context,Answer_pageActivity.class);
                    intent.putExtra("Qname",data.get(position).get("Qname")+"");
                    intent.putExtra("Sname",data.get(position).get("Sname")+"");
                    intent.putExtra("Adetail",data.get(position).get("Adetail")+"");
//                    intent.putExtra("Sid",Sid);
                    intent.putExtra("A_Sid",data.get(position).get("Sid")+"");
                    intent.putExtra("Aid",data.get(position).get("Aid")+"");
                    intent.putExtra("Agood",data.get(position).get("Agood")+"");
                    intent.putExtra("AgoodPersonal",(boolean) data.get(position).get("AgoodPersonal"));
                    intent.putExtra("AbadPersonal",(boolean) data.get(position).get("AbadPersonal"));
                    context.startActivity(intent);
                }
            });
        }
    }


    static class ItemViewHolder extends ViewHolder {

        TextView tv,tv1,tv2,tv3;

        public ItemViewHolder(View view) {
            super(view);
            tv = (TextView) view.findViewById(R.id.activitymain_adapter_tx1);
            tv1 = (TextView) view.findViewById(R.id.activitymain_adapter_tx2);
            tv2 = (TextView) view.findViewById(R.id.activitymain_adapter_tx3);
            tv3 = (TextView) view.findViewById(R.id.activitymain_adapter_tx4);
        }
    }

    static class FootViewHolder extends ViewHolder {

        public FootViewHolder(View view) {
            super(view);
        }
    }
}