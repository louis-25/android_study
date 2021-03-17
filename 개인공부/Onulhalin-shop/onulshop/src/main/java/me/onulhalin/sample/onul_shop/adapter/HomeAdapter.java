package me.onulhalin.sample.onul_shop.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import me.onulhalin.sample.R;
import me.onulhalin.sample.onul_shop.entity.Article;
import me.onulhalin.sample.onul_shop.listener.OnItemClickListener;

/**
 *
 *
 */
public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {
    private List<Article> mItems = new ArrayList<>();
    private LayoutInflater mInflater;

    private OnItemClickListener mClickListener;
    SimpleDateFormat sdf,sdf1;
	//Date date;
    SimpleDateFormat dayTime;
    long time;
    SimpleDateFormat dt;
    Date date;
    public HomeAdapter(Context context) {
        this.mInflater = LayoutInflater.from(context);
    }

    public void setDatas(List<Article> items) {
        mItems.clear();
        mItems.addAll(items);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_home, parent, false);
        final MyViewHolder holder = new MyViewHolder(view);
		 date = new Date();
         sdf = new SimpleDateFormat("MM-dd");
        sdf1 = new SimpleDateFormat("HH:mm");
         dt = new SimpleDateFormat("yyyyy-mm-dd hh:mm:ss");
        //Date date = dt.parse(date_s);
        //  time = System.currentTimeMillis();

     //   SimpleDateFormat dayTime = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");

       // Log.d("kimtest","@@@"+date );

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                if (mClickListener != null) {
                    mClickListener.onItemClick(position, v, holder);
                }
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Article item = mItems.get(position);

        try {
             date = dt.parse(item.getDatetime());
             Log.d("kimtest","@@====="+item.getDatetime());
        } catch (ParseException e) {
            Log.d("kimtest","@@===="+e.getMessage());
            e.printStackTrace();
        }

       // date = dt.parse(item.getDatetime());
        //String getTime = sdf.format(item.getDate());


            if (item.getStatus().equals("CANCELED")){
                holder.tv_status.setText("취소됨");
                holder.tv_status.setTextColor(Color.RED);
                holder.tv_status.setVisibility(View.VISIBLE);
            }
            else if(item.getStatus().equals("CONFIRMED")){
                holder.tv_status.setText("완료됨");
                holder.tv_status.setTextColor(Color.BLACK);
                holder.tv_status.setVisibility(View.VISIBLE);
            }
            else {
                holder.tv_status.setText("");

                holder.tv_status.setVisibility(View.GONE);
            }
        holder.tvTitle.setText(item.getTitle());
        holder.tvContent.setText("매장픽업 "+item.getContent());
       holder.tv_date.setText( sdf.format(new Date(item.getDatetime())));
       holder.tv_time.setText(sdf1.format(new Date(item.getDatetime())));
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public Article getItem(int position) {
        return mItems.get(position);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle, tvContent,tv_date,tv_time,noorder,tv_status;
        private CardView cardview;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            tvContent = (TextView) itemView.findViewById(R.id.tv_content);
            tv_date = (TextView)itemView.findViewById(R.id.tv_date);
            tv_time = (TextView)itemView.findViewById(R.id.tv_time);
            noorder = (TextView)itemView.findViewById(R.id.noorder);
            cardview = (CardView)itemView.findViewById(R.id.cardview);
            tv_status = (TextView)itemView.findViewById(R.id.tv_status);
        }
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }
}
