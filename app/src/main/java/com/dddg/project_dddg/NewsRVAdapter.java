package com.dddg.project_dddg;

import android.util.Log;
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
    String newsContextString;
    int mode;
    public NewsRVAdapter(ArrayList<NewsData> NewsList,int mode) { // 0이면 리스트,1 이면 그리드
        this.NewsList = NewsList;
        this.mode = mode;
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
        View view;
        Log.d("recycer mode ","recycler mode:" + Integer.toString(mode));
        if(mode == 0) view = layoutInflater.inflate(R.layout.news_wrapper,parent,false);
        else view = layoutInflater.inflate(R.layout.news_wrapper_grid,parent,false);
        return new ViewHolder(view,mode);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        newsContextString = NewsList.get(position).context;
        if(newsContextString.length()>100) newsContextString = newsContextString.substring(0,100)+"...";
        holder.newsTitle.setText(NewsList.get(position).title.toString());
        holder.newsContext.setText(newsContextString);
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
        public TextView newsTitle;
        public TextView newsContext;
        public TextView newsInfo;
        public ImageView newsImg;
        int mode;
        public ViewHolder(@NonNull View itemView, int mode) {
            super(itemView);
            this.mode = mode;
            if(mode == 0) { // list형
                this.newsTitle = itemView.findViewById(R.id.news_item_title);
                this.newsContext = itemView.findViewById(R.id.news_item_context);
                this.newsInfo = itemView.findViewById(R.id.news_item_info);
                this.newsImg = itemView.findViewById(R.id.news_item_img);
            }
            else{
                this.newsTitle = itemView.findViewById(R.id.news_item_title_grid);
                this.newsContext = itemView.findViewById(R.id.news_item_context_grid);
                this.newsInfo = itemView.findViewById(R.id.news_item_info_grid);
                this.newsImg = itemView.findViewById(R.id.news_item_img_grid);
            }

    }
    }
}
