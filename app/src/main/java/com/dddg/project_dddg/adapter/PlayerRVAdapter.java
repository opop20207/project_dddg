package com.dddg.project_dddg.adapter;

import android.graphics.Color;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.dddg.project_dddg.PlayerInfo;
import com.dddg.project_dddg.R;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.ArrayList;

public class PlayerRVAdapter extends RecyclerView.Adapter<PlayerRVAdapter.ViewHolder> {
    ArrayList<PlayerInfo> playerInfoArrayList;
    public PlayerRVAdapter(ArrayList<PlayerInfo> playerInfoArrayList) {
        this.playerInfoArrayList = playerInfoArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.playerinfo_wrapper, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.player_position.setText(playerInfoArrayList.get(position).position);
        holder.player_name.setText(playerInfoArrayList.get(position).name);
        holder.player_nickname.setText(playerInfoArrayList.get(position).nickname);
    }

    @Override
    public int getItemCount() {
        return playerInfoArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView player_position = itemView.findViewById(R.id.player_info_position);
        TextView player_name = itemView.findViewById(R.id.player_info_name);
        TextView player_nickname = itemView.findViewById(R.id.player_info_nickname);

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
