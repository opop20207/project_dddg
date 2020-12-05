package com.dddg.project_dddg.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dddg.project_dddg.MatchPlanData;
import com.dddg.project_dddg.R;
import com.dddg.project_dddg.TeamImgUrl;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MatchPlanRVAdapter extends RecyclerView.Adapter<MatchPlanRVAdapter.ViewHolder> {
    DatabaseReference dataRef = FirebaseDatabase.getInstance().getReference("Schedule");
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    ArrayList<MatchPlanData> matchPlanDataArrayList;
    ArrayList<String> reference;
    OnItemClickListener mlistener;
    Context context;
    public interface  OnItemClickListener{
        public void onItemClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        mlistener = listener;
    }
    public MatchPlanRVAdapter(ArrayList<MatchPlanData> matchPlanDataArrayList,ArrayList<String> ref,Context context) {
        this.matchPlanDataArrayList = matchPlanDataArrayList;
        reference = ref;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view;
        view = layoutInflater.inflate(R.layout.match_plan_wrapper,parent,false);
        return new MatchPlanRVAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MatchPlanData data = matchPlanDataArrayList.get(position);
        holder.title.setText(data.title+"\n"+data.date+" "+data.matchtime);
        holder.team1_name.setText(data.team1);
        holder.team2_name.setText(data.team2);
        Glide.with(holder.itemView).load(TeamImgUrl.Url(data.team1)).into(holder.team1_img);
        Glide.with(holder.itemView).load(TeamImgUrl.Url(data.team2)).into(holder.team2_img);
        holder.vote1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean duple = false;
                for(int i =0;i<data.voteListName.size();i++) if(data.voteListName.get(i).equals(user.getEmail())) {
                    duple = true;
                    break;
                }
                if(!duple) {
                    dataRef.child(reference.get(position)).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            MatchPlanData matchPlanData = snapshot.getValue(MatchPlanData.class);
                            matchPlanData.setTeam1_vote(matchPlanData.getTeam1_vote() + 1);
                            matchPlanData.voteListName.add(user.getEmail());
                            matchPlanData.voteListNum.add(1);
                            dataRef.child(reference.get(position)).updateChildren(matchPlanData.toMap()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(context, "투표완료", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
                else{
                    Toast.makeText(context, "중복투표는 불가합니다.", Toast.LENGTH_LONG).show();
                }
            }
        });
        holder.vote2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean duple = false;
                for(int i =0;i<data.voteListName.size();i++) if(data.voteListName.get(i).equals(user.getEmail())) {
                    duple = true;
                    break;
                }
                if(!duple) {
                    dataRef.child(reference.get(position)).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            MatchPlanData matchPlanData = snapshot.getValue(MatchPlanData.class);
                            matchPlanData.setTeam2_vote(matchPlanData.getTeam2_vote() + 1);
                            matchPlanData.voteListName.add(user.getEmail());
                            matchPlanData.voteListNum.add(2);
                            dataRef.child(reference.get(position)).updateChildren(matchPlanData.toMap()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(context, "투표완료", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
                else{
                    Toast.makeText(context, "중복투표는 불가합니다.", Toast.LENGTH_LONG).show();
                }
            }
        });
        chartSetting(holder.barChart);
        setDatachart(holder.barChart,data.team1_vote,data.team2_vote);
    }
    void chartSetting(HorizontalBarChart chart){
        chart.setDrawBarShadow(false);
        chart.setDrawValueAboveBar(true);
        chart.getDescription().setEnabled(false);
        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        // scaling can now only be done on x- and y-axis separately
        chart.setPinchZoom(false);
        // draw shadows for each bar that show the maximum value
        // chart.setDrawBarShadow(true);
        chart.setDrawGridBackground(false);
        XAxis xl = chart.getXAxis();
        xl.setPosition(XAxis.XAxisPosition.BOTTOM);
        xl.setTypeface(Typeface.DEFAULT);
        xl.setDrawAxisLine(true);
        xl.setDrawGridLines(false);
        xl.setGranularity(10f);

        YAxis yl = chart.getAxisLeft();
        yl.setTypeface(Typeface.DEFAULT);
        yl.setDrawAxisLine(true);
        yl.setDrawGridLines(false);
        yl.setAxisMinimum(0f); // this replaces setStartAtZero(true)
//        yl.setInverted(true);
        YAxis yr = chart.getAxisRight();
        yr.setTypeface(Typeface.DEFAULT);
        yr.setDrawAxisLine(true);
        yr.setDrawGridLines(false);
        yr.setAxisMinimum(0f); // this replaces setStartAtZero(true)
//        yr.setInverted(true);

        chart.setFitBars(true);
        chart.animateY(500);

        Legend l = chart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setFormSize(8f);
        l.setXEntrySpace(4f);

    }
    void setDatachart(HorizontalBarChart chart, int red,int blue){ //비율로
        ArrayList<BarEntry> datas = new ArrayList<>();
        ArrayList<String> tmp = new ArrayList<>();
        tmp.add("Red");tmp.add("Blue");
        float redPercent = red/(float)(red+blue)*100.0f;
        float bluePercent = blue/(float)(red+blue)*100.0f;
        datas.add(new BarEntry(0,new float[]{redPercent, bluePercent}));
        ArrayList<Integer> color = new ArrayList<>();
        color.add(ContextCompat.getColor(context,R.color.subRed));
        color.add(ContextCompat.getColor(context,R.color.subDarkBlue));
        BarDataSet barDataSet = new BarDataSet(datas,"");
        barDataSet.setColors(color);
        BarData barData = new BarData(barDataSet);
        chart.setData(barData);
    }
    @Override
    public int getItemCount() {
        return matchPlanDataArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        TextView team1_name;
        TextView team2_name;
        ImageView team1_img;
        ImageView team2_img;
        HorizontalBarChart barChart;
        Button vote1;
        Button vote2;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.match_plan_title);
            team1_name = itemView.findViewById(R.id.match_plan_team1_name);
            team2_name = itemView.findViewById(R.id.match_plan_team2_name);
            team1_img = itemView.findViewById(R.id.match_plan_team1_img);
            team2_img= itemView.findViewById(R.id.match_plan_team2_img);
            barChart = itemView.findViewById(R.id.match_plan_hori_barchart);
            vote1 = itemView.findViewById(R.id.match_plan_vote_team1);
            vote2 = itemView.findViewById(R.id.match_plan_vote_team2);
        }
    }
}
