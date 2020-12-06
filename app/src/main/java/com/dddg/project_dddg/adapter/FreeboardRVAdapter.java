package com.dddg.project_dddg.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dddg.project_dddg.FreeboardData;
import com.dddg.project_dddg.R;

import java.util.ArrayList;

public class FreeboardRVAdapter extends RecyclerView.Adapter<FreeboardRVAdapter.ViewHolder> {
    ArrayList<FreeboardData> freeboardData;
    OnItemClickListener mlistener;
    public interface OnItemClickListener{
        public void onItemClick(View view,int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        mlistener =listener;
    }
    public FreeboardRVAdapter(ArrayList<FreeboardData> freeboardData) {
        this.freeboardData = freeboardData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view;
        view = layoutInflater.inflate(R.layout.community_freeboard_wrapper,parent,false);
        return new FreeboardRVAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.title.setText(freeboardData.get(position).denseTitleget());
        holder.context.setText(freeboardData.get(position).denseContextget());
        holder.date.setText(freeboardData.get(position).getDate());
        if(freeboardData.get(position).anonymouse) holder.email.setText("익명");
        else {
            holder.email.setText(freeboardData.get(position).getEmail());
        }

        if(freeboardData.get(position).getComment()!=null) holder.comment_num.setText("댓글 "+Integer.toString(freeboardData.get(position).getComment().size()));
        else  holder.comment_num.setText("댓글 0");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mlistener.onItemClick(v,position);
            }
        });
    }
    @Override
    public int getItemCount() {
        return freeboardData.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView title;
        public TextView context;
        public TextView date;
        public TextView email;
        public TextView comment_num;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.freeboard_wrapper_title);
            context = itemView.findViewById(R.id.freeboard_wrapper_context);
            date = itemView.findViewById(R.id.freeboard_wrapper_date);
            email = itemView.findViewById(R.id.freeboard_wrapper_email);
            comment_num = itemView.findViewById(R.id.freeboard_wrapper_comment_num);
        }
    }
}
