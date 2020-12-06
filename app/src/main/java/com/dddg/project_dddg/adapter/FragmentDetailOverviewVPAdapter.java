package com.dddg.project_dddg.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dddg.project_dddg.ChampImgUrl;
import com.dddg.project_dddg.FragmentDetailOverview;
import com.dddg.project_dddg.R;

import java.util.ArrayList;

public class FragmentDetailOverviewVPAdapter extends RecyclerView.Adapter<FragmentDetailOverviewVPAdapter.viewHolder> {
    ArrayList<FragmentDetailOverview.PlayerChampData> playerChampData;
    public onItemClickListener mlistener;
    public FragmentDetailOverviewVPAdapter(ArrayList<FragmentDetailOverview.PlayerChampData> data){
        playerChampData = data;
    }
    public interface onItemClickListener{
        public void onItemClick();
    }
    public void setOnItemClickListener(onItemClickListener listener){
        mlistener = listener;
    }
    public static class viewHolder extends RecyclerView.ViewHolder {
        TextView player_name;
        TextView pick_champ_name;
        TextView ban_champ_name;
        ImageView  pick_champ_img;
        ImageView ban_champ_img;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            player_name = itemView.findViewById(R.id.detail_player_name);
            pick_champ_name = itemView.findViewById(R.id.detail_pick_champ_name);
            ban_champ_name = itemView.findViewById(R.id.detail_ban_champ_name);
            pick_champ_img = itemView.findViewById(R.id.detail_pick_champ_img);
            ban_champ_img = itemView.findViewById(R.id.detail_ban_champ_img);
        }
    }
    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.match_detail_player_wrapper,parent,false);
        return new viewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
       holder.player_name.setText(playerChampData.get(position).name);
       holder.pick_champ_name.setText(playerChampData.get(position).pick);
       holder.ban_champ_name.setText(playerChampData.get(position).ban);
       Glide.with(holder.itemView).load(ChampImgUrl.getUrl(playerChampData.get(position).pick)).into(holder.pick_champ_img);
       Glide.with(holder.itemView).load(ChampImgUrl.getUrl(playerChampData.get(position).ban)).into(holder.ban_champ_img);
       holder.itemView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if(mlistener !=null){
                   mlistener.onItemClick();
               }
           }
       });
    }

    @Override
    public int getItemCount() {
        return playerChampData.size();
    }


}
