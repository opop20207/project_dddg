package com.dddg.project_dddg.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.AnimatorRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dddg.project_dddg.MatchData;
import com.dddg.project_dddg.R;
import com.dddg.project_dddg.TeamImgUrl;

import java.util.ArrayList;

public class MatchRVAdapter extends RecyclerView.Adapter<MatchRVAdapter.ViewHolder> {
    ArrayList<MatchData> MatchList;
    Context context;
    public MatchRVAdapter(ArrayList<MatchData> MatchList, Context context) {
        this.MatchList = MatchList;
        this.context = context;
    }
    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }
    public OnItemClickListener mlistener;
    public void setOnItemClickListener(OnItemClickListener listener){
        mlistener = listener;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.match_wrapper,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        holder.home_name.setText(MatchList.get(position).team1_name);
        holder.away_name.setText(MatchList.get(position).team2_name);
        holder.match_info.setText(MatchList.get(position).date+" "+MatchList.get(position).title+" "+MatchList.get(position).stage);

        if(MatchList.get(position).gamescore !=null) {
            holder.home_score.setText(MatchList.get(position).gamescore.split("/")[0]);
            holder.away_score.setText(MatchList.get(position).gamescore.split("/")[1]);
        }
        Glide.with(holder.itemView).load(TeamImgUrl.Url(MatchList.get(position).team1_name)).into(holder.home_img);
        Glide.with(holder.itemView).load(TeamImgUrl.Url(MatchList.get(position).team2_name)).into(holder.away_img);

        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {//클릭시 mlistener에 Bind에서 가지고 있던 itemView와 position을 전송
                if(mlistener!=null)
                    mlistener.onItemClick(holder.itemView,position);
            }
        });
        setAnimation(holder.itemView,position);
        // 내용물 설정
    }

    @Override
    public int getItemCount() {
        return MatchList.size();
        // 데이터 사이즈 뉴스 리스트
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        public TextView home_name = itemView.findViewById(R.id.match_home_team_name);
        public TextView away_name = itemView.findViewById(R.id.match_away_team_name);
        public TextView home_score = itemView.findViewById(R.id.match_home_team_score);
        public TextView away_score = itemView.findViewById(R.id.match_away_team_score);
        public TextView match_info = itemView.findViewById(R.id.match_info);
        public ImageView home_img = itemView.findViewById(R.id.match_home_team_img);
        public ImageView away_img = itemView.findViewById(R.id.match_away_team_img);
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
    }
    }
    private void setAnimation(View viewtoAnimate, int position){
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            animation.setDuration(500);
            viewtoAnimate.startAnimation(animation);
    }
}
