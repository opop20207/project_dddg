package com.dddg.project_dddg;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class NewsRVAdapter extends RecyclerView.Adapter<NewsRVAdapter.ViewHolder> {
    ArrayList<NewsData> NewsList;
    public NewsRVAdapter(ArrayList<NewsData> NewsList) {
        this.NewsList = NewsList;
    }
    interface OnItemClickListener{
        void onItemClick(View view, int position);
    }
    OnItemClickListener mlistener;
    public void setOnItemClickListener(OnItemClickListener listener){
        mlistener = listener;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.news_wrapper,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.newsTitle.setText(NewsList.get(position).title.toString());
        holder.newsContext.setText(NewsList.get(position).context.toString());
        holder.newsInfo.setText(NewsList.get(position).info.toString());
        Glide.with(holder.itemView).load(NewsList.get(position).img_url.toString()).into(holder.newsImg);
        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {//클릭시 mlistener에 Bind에서 가지고 있던 itemView와 position을 전송
                if(mlistener!=null)
                    mlistener.onItemClick(holder.itemView,position);
            }
        });
        // 내용물 설정
    }

    @Override
    public int getItemCount() {
        return NewsList.size();
        // 데이터 사이즈 뉴스 리스트
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        public TextView newsTitle = itemView.findViewById(R.id.news_item_title);
        public TextView newsContext = itemView.findViewById(R.id.news_item_context);
        public TextView newsInfo = itemView.findViewById(R.id.news_item_info);
        public ImageView newsImg = itemView.findViewById(R.id.news_item_img);
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
    }
    }
}
