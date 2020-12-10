package com.dddg.project_dddg.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dddg.project_dddg.PlayerInfo;
import com.dddg.project_dddg.R;
import com.dddg.project_dddg.TeamData;
import com.dddg.project_dddg.TeamImgUrl;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.ArrayList;

public class MyteamRVAdapter extends RecyclerView.Adapter<MyteamRVAdapter.ViewHolder> {
    public ArrayList<TeamData> teamDataArrayList;
    Context context;
    OnItemClickListener mlistener;
    public interface OnItemClickListener{
        void onItemClick(View view,int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        mlistener = listener;
    }
    public MyteamRVAdapter(ArrayList<TeamData> teamDataArrayList, Context context) {
        this.teamDataArrayList = teamDataArrayList;
        this.context = context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.myteam_wrapper,parent,false);
        return new MyteamRVAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.team_name_l.setText(teamDataArrayList.get(position).teamname);
        holder.team_name_s.setText(teamDataArrayList.get(position).teamname);

        holder.supervisor.setText(teamDataArrayList.get(position).director);
        holder.coach.setText(teamDataArrayList.get(position).coach);
        holder.expand_btn_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.expand_layout.isExpanded()){
                    holder.expand_layout.collapse();
                }else holder.expand_layout.expand();
            }
        });
        ArrayList<PlayerInfo> list = new ArrayList<>();
        list.add(new PlayerInfo("이름","닉네임","포지션"));
        list.addAll(teamDataArrayList.get(position).matching());
        LinearLayoutManager manager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false);
        PlayerRVAdapter adapter = new PlayerRVAdapter(list);
        holder.recyclerView.setLayoutManager(manager);
        holder.recyclerView.setHasFixedSize(true);
        holder.recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        Glide.with(holder.itemView).load(TeamImgUrl.Url(teamDataArrayList.get(position).teamname)).into(holder.team_logo_l);
        Glide.with(holder.itemView).load(TeamImgUrl.Url(teamDataArrayList.get(position).teamname)).into(holder.team_logo_s);
        holder.expand_btn_layout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                AlertDialog.Builder builder= new AlertDialog.Builder(context).setTitle("마이팀 삭제").setMessage("삭제하시겠습니까?").setPositiveButton("네", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mlistener.onItemClick(holder.itemView,position);
                    }
                }).setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(context, "취소하셨습니다.", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
                return true;

            }
        });
    }

    @Override
    public int getItemCount() {
        return teamDataArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView team_logo_s = itemView.findViewById(R.id.myteam_team_logo_small);
        ImageView team_logo_l = itemView.findViewById(R.id.myteam_team_logo_large);
        TextView team_name_s = itemView.findViewById(R.id.myteam_team_name_small);
        TextView team_name_l = itemView.findViewById(R.id.myteam_team_name_large);
        TextView supervisor = itemView.findViewById(R.id.myteam_team_supervisor);
        TextView coach = itemView.findViewById(R.id.myteam_team_coach);
        ConstraintLayout expand_btn_layout = itemView.findViewById(R.id.myteam_team_layout_expand_btn);
        ExpandableLayout expand_layout = itemView.findViewById(R.id.myteam_expandable);
        RecyclerView recyclerView = itemView.findViewById(R.id.myteam_team_player_recylerview);

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
