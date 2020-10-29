package com.dddg.project_dddg;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CommentRVAdapter extends RecyclerView.Adapter<CommentRVAdapter.ViewHolder> {
    ArrayList<CommentData> commenList;
    public CommentRVAdapter(ArrayList<CommentData> commenList) {
        this.commenList = commenList;
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
        View view = layoutInflater.inflate(R.layout.detail_comment_wrapper,parent,false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.commentID.setText(commenList.get(position).id+":  ");
        holder.commentContext.setText(commenList.get(position).context);
        holder.commentUploadTime.setText(commenList.get(position).time);
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
        return commenList.size();
        // 데이터 사이즈 뉴스 리스트
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        public TextView commentID = itemView.findViewById(R.id.detail_comment_name);
        public TextView commentContext = itemView.findViewById(R.id.detail_comment_context);
        public TextView commentUploadTime = itemView.findViewById(R.id.detail_comment_uploadTime);
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
    }
    }
}
