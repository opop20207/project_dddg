package com.dddg.project_dddg;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dddg.project_dddg.adapter.FragmentDetailOverviewVPAdapter;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.MPPointF;


import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FragmentDetailOverview extends Fragment implements SeekBar.OnSeekBarChangeListener,
        OnChartValueSelectedListener {
    static FragmentDetailOverview instance;
    public FragmentDetailOverview() {
    }
    public static FragmentDetailOverview getInstance(){
        if(instance==null){
            instance = new FragmentDetailOverview();
        }
        return instance;
    }
    TextView winTeam;
    ImageView winTeamImg;
    PieChart vote_rate;
    BarChart kill_Red;
    BarChart kill_Blue;
    int red_vote,blue_vote;
    MatchData matchData;
    ExpandableLayout redExpand;
    ExpandableLayout blueExpand;
    Button expandBoth;
    ScrollView scrollView;
    ArrayList<BarDataSet> teamRedPlayerBarData;
    ArrayList<BarDataSet> teamBluePlayerBarData;
    ArrayList<BarEntry> tmpRed;
    ArrayList<BarEntry> tmpBlue;
    List<String> teamRedPlayerName;
    List<String> teamBluePlayerName;
    List<String > teamRedPlayerPick;
    List<String > teamRedPlayerBan;
    List<String > teamBluePlayerPick;
    List<String > teamBluePlayerBan;
    List<String> teamRedPlayScore;
    List<String> teamBluePlayScore;
    RecyclerView recyclerViewRed;
    RecyclerView recyclerViewBlue;
    LinearLayoutManager linearLayoutManagerRed;
    LinearLayoutManager linearLayoutManagerBlue;
    FragmentDetailOverviewVPAdapter adapterRed;
    FragmentDetailOverviewVPAdapter adapterBlue;
    @Nullable
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.match_detail_fragment_overview,container,false);
    }
public class PlayerChampData{
        public String name;
        public String pick;
        public String ban;
        public PlayerChampData(String name,String pick, String ban){
            this.name = name;
            this.pick = pick;
            this.ban = ban;
        }

}
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        matchData = (MatchData) getActivity().getIntent().getSerializableExtra("matchdata");
        winTeam = getView().findViewById(R.id.detail_win_team_name);
        winTeamImg = getView().findViewById(R.id.detail_win_team_img);
        String winTeamString;
        if(Integer.parseInt(matchData.winteam)>1) winTeamString = matchData.team2_name;
        else winTeamString = matchData.team1_name;
        winTeam.setText(winTeamString);
        Glide.with(getView()).load(TeamImgUrl.Url(winTeamString)).into(winTeamImg);
        kill_Red = getView().findViewById(R.id.detail_teamRed_kill_chart);
        kill_Blue = getView().findViewById(R.id.detail_teamBlue_kill_chart);
        kill_Red.setVisibility(View.GONE);
        kill_Blue.setVisibility(View.GONE);
        expandBoth = getView().findViewById(R.id.detail_teamBoth_expand);
        redExpand = getView().findViewById(R.id.detail_teamRed_expand);
        blueExpand = getView().findViewById(R.id.detail_teamBlue_expand);
        scrollView = getView().findViewById(R.id.detail_scrollview);
        expandBoth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!redExpand.isExpanded()&&!blueExpand.isExpanded()){
                    redExpand.expand();
                    blueExpand.expand();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            scrollView.post(new Runnable(){
                                public void run(){
                                    scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                                }
                            });
                        }
                    },500);
                }
                else{
                    redExpand.collapse();
                    blueExpand.collapse();
                }
            }
        });
        {
            vote_rate = getView().findViewById(R.id.detail_overview_pie);
            vote_rate.setUsePercentValues(false);
            vote_rate.getDescription().setEnabled(false);
            vote_rate.setExtraOffsets(5, 10, 5, 5);
            vote_rate.setDragDecelerationFrictionCoef(0.95f);
            vote_rate.setCenterTextTypeface(Typeface.DEFAULT);
            vote_rate.setCenterText("킬 비율");
            vote_rate.setDrawHoleEnabled(true);
            vote_rate.setHoleColor(Color.WHITE);
            vote_rate.setHoleRadius(30f);
            vote_rate.setTransparentCircleRadius(35f);
            vote_rate.setDrawCenterText(true);
            vote_rate.setRotationAngle(0);
            vote_rate.setRotationEnabled(true);
            vote_rate.setHighlightPerTapEnabled(true);
            vote_rate.setOnChartValueSelectedListener(this);
            vote_rate.animateY(1400, Easing.EaseInOutQuad);
            vote_rate.setTouchEnabled(true);
        } //파이 차트 관련
        {
            kill_Red.setOnChartValueSelectedListener(this);
            kill_Blue.setOnChartValueSelectedListener(this);
            kill_Red.getDescription().setEnabled(false);
            kill_Blue.getDescription().setEnabled(false);
            kill_Red.setPinchZoom(false);
            kill_Blue.setPinchZoom(false);
            kill_Red.setDrawBarShadow(false);
            kill_Blue.setDrawBarShadow(false);
            kill_Red.setDrawGridBackground(false);
            kill_Blue.setDrawGridBackground(false);
            kill_Red.getAxisLeft().setDrawGridLines(false);
            kill_Red.getXAxis().setDrawGridLines(false);
            kill_Blue.getAxisLeft().setDrawGridLines(false);
            kill_Blue.getXAxis().setDrawGridLines(false);
            kill_Red.setDoubleTapToZoomEnabled(false);
            kill_Blue.setDoubleTapToZoomEnabled(false);
            kill_Red.animateY(3000, Easing.EaseInOutQuad);
            kill_Blue.animateY(3000, Easing.EaseInOutQuad);
            Legend R = kill_Red.getLegend();
            R.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
            R.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
            R.setOrientation(Legend.LegendOrientation.HORIZONTAL);
            R.setWordWrapEnabled(true);
            R.setTextColor(Color.BLACK);
            R.setForm(Legend.LegendForm.CIRCLE);
            Legend B = kill_Blue.getLegend();
            B.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
            B.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
            B.setOrientation(Legend.LegendOrientation.HORIZONTAL);
            B.setWordWrapEnabled(true);
            B.setTextColor(Color.BLACK);
            B.setForm(Legend.LegendForm.CIRCLE);
        }// 바 차트 관련
        red_vote = Integer.parseInt(matchData.gamescore.split("/")[0]);
        blue_vote = Integer.parseInt(matchData.gamescore.split("/")[1]);
        setDataPie();
        setDataBar();
        setExpandAdapter();
    }
    private void setExpandAdapter(){
        ArrayList<FragmentDetailOverview.PlayerChampData> redPlayer =new ArrayList();
        ArrayList<FragmentDetailOverview.PlayerChampData> bluePlayer=new ArrayList();
        teamRedPlayerPick = Arrays.asList(matchData.team1_champ.split("/"));
        teamBluePlayerPick = Arrays.asList(matchData.team2_champ.split("/"));
        teamRedPlayerBan = Arrays.asList(matchData.team1_ban.split("/"));
        teamBluePlayerBan = Arrays.asList(matchData.team2_ban.split("/"));
        String banPick;
        Log.d("로그",Integer.toString(teamRedPlayerBan.size()));
        for(int i = 0;i<teamRedPlayerName.size();i++){
            if(i>=teamRedPlayerBan.size()) banPick = "";
            else banPick = teamRedPlayerBan.get(i);
          redPlayer.add(new PlayerChampData(teamRedPlayerName.get(i),teamRedPlayerPick.get(i),banPick));

        }
        for(int i = 0;i<teamBluePlayerName.size();i++){
            if(i>=teamBluePlayerBan.size()) banPick = "";
            else banPick = teamBluePlayerBan.get(i);
            bluePlayer.add(new PlayerChampData(teamBluePlayerName.get(i),teamBluePlayerPick.get(i),banPick));
        }
        recyclerViewRed = getView().findViewById(R.id.detail_teamred_player_recyclerview);
        recyclerViewBlue = getView().findViewById(R.id.detail_teamblue_player_recyclerview);
        linearLayoutManagerRed = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        linearLayoutManagerBlue = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        recyclerViewRed.setLayoutManager(linearLayoutManagerRed);
        recyclerViewBlue.setLayoutManager(linearLayoutManagerBlue);
        recyclerViewRed.setHasFixedSize(true);
        recyclerViewBlue.setHasFixedSize(true);
        adapterRed = new FragmentDetailOverviewVPAdapter(redPlayer);
        adapterBlue = new FragmentDetailOverviewVPAdapter(bluePlayer);
        adapterRed.setOnItemClickListener(new FragmentDetailOverviewVPAdapter.onItemClickListener() {
            @Override
            public void onItemClick() {
                if(redExpand.isExpanded()){
                    redExpand.collapse();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            scrollView.post(new Runnable(){
                                public void run(){
                                    scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                                }
                            });
                        }
                    },500);
                }
            }
        });
        adapterBlue.setOnItemClickListener(new FragmentDetailOverviewVPAdapter.onItemClickListener() {
            @Override
            public void onItemClick() {
                if(blueExpand.isExpanded()){
                    blueExpand.collapse();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            scrollView.post(new Runnable(){
                                public void run(){
                                    scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                                }
                            });
                        }
                    },500);
                }
            }
        });
        recyclerViewRed.setAdapter(adapterRed);
        recyclerViewBlue.setAdapter(adapterBlue);

    } //recyclerview
    private void setDataPie() {
        ArrayList entries = new ArrayList<>();
        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        entries.add(new PieEntry(red_vote,matchData.team1_name));
        entries.add(new PieEntry(blue_vote,matchData.team2_name));
        PieDataSet dataSet = new PieDataSet(entries, " Red Vs Blue");
        dataSet.setDrawIcons(false);
        dataSet.setSliceSpace(3f);
        dataSet.setIconsOffset(new MPPointF(0, 40));
        dataSet.setSelectionShift(5f);
        // add a lot of colors

        ArrayList<Integer> colors = new ArrayList<>();

        colors.add(getResources().getColor(R.color.subRed));
        colors.add(getResources().getColor(R.color.subDarkBlue));

        dataSet.setColors(colors);
        //dataSet.setSelectionShift(0f);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new LargeValueFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
        data.setValueTypeface(Typeface.DEFAULT);
        vote_rate.setData(data);

        // undo all highlights
        vote_rate.highlightValues(null);
        vote_rate.invalidate();
    }//대충 pie 데이터 넣는 곳
    public  void setDataBar(){
        teamRedPlayerBarData = new ArrayList<BarDataSet>();
        teamBluePlayerBarData = new ArrayList<BarDataSet>();
        tmpRed = new ArrayList<BarEntry>();
        tmpBlue = new ArrayList<BarEntry>();
        teamRedPlayerName =Arrays.asList(matchData.team1_playername.split("/"));
        teamBluePlayerName = Arrays.asList(matchData.team2_playername.split("/"));
        teamRedPlayScore = Arrays.asList(matchData.team1_playerscore.split("#"));
        teamBluePlayScore = Arrays.asList(matchData.team2_playerscore.split("#"));

        BarData dataRed = new BarData();
        BarData dataBlue = new BarData();
        for(int i = 0;i<5;i++){//선수
            for(int j = 0;j<3;j++){//킬뎃어시
                tmpRed.add(new BarEntry(i*4+j, Integer.parseInt(teamRedPlayScore.get(i).split("/")[j])));
                tmpBlue.add(new BarEntry(i*4+j, Integer.parseInt(teamBluePlayScore.get(i).split("/")[j])));
            }
            teamRedPlayerBarData.add(new BarDataSet(tmpRed,teamRedPlayerName.get(i)));
            teamBluePlayerBarData.add(new BarDataSet(tmpBlue,teamBluePlayerName.get(i)));
            teamRedPlayerBarData.get(i).setColors(new int[] {getColor(0),getColor(1),getColor(2)});
            teamBluePlayerBarData.get(i).setColors(new int[] {getColor(0),getColor(1),getColor(2)});
            dataRed.addDataSet(teamRedPlayerBarData.get(i));
            dataBlue.addDataSet(teamBluePlayerBarData.get(i));
        }
        dataRed.setValueTextSize(15f);
        dataBlue.setValueTextSize(15f);
        dataRed.setValueFormatter(new LargeValueFormatter());
        dataBlue.setValueFormatter(new LargeValueFormatter());
        dataRed.setValueTypeface(Typeface.DEFAULT);
        dataBlue.setValueTypeface(Typeface.DEFAULT);

        kill_Red.setData(dataRed);
        kill_Red.invalidate();
        kill_Blue.setData(dataBlue);
        kill_Blue.invalidate();
    }//대충 bar 데이터 넣는곳
    public int getColor(int i){
        switch (i){
            case 0: return getResources().getColor(R.color.killRed);
            case 1: return getResources().getColor(R.color.deathBrown);
            case 2: return getResources().getColor(R.color.asistBlue);
            default: return Color.rgb(255,255,255);
        }
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }



    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onValueSelected(Entry e, Highlight h) {
        if(e instanceof PieEntry) {
            Transition slide = new Slide(Gravity.BOTTOM);
            Transition fade = new Fade();
            slide.setDuration(600);
            fade.setDuration(600);
            PieEntry pe = (PieEntry) e;
            String label = pe.getLabel();
            String red = matchData.team1_name;
            String blue = matchData.team2_name;
            if (label.equals(red)) {
                if (kill_Red.getVisibility() == View.VISIBLE) {
                    fade.addTarget(kill_Red);
                    TransitionManager.beginDelayedTransition(getView().findViewById(R.id.detail_teamRed_kill_chart),fade);
                    kill_Red.setVisibility(View.GONE);
                    fade.removeTarget(kill_Red);
                }
                else {
                    slide.addTarget(kill_Red);
                    TransitionManager.beginDelayedTransition(getView().findViewById(R.id.detail_teamRed_kill_chart),slide);
                    kill_Red.setVisibility(View.VISIBLE);
                    kill_Red.requestFocus();
                    kill_Red.animateY(1500, Easing.EaseInOutQuad);
                    slide.removeTarget(kill_Red);
                }
            }
            else if (label.equals(blue)) {
                if (kill_Blue.getVisibility() == View.VISIBLE) {
                    fade.addTarget(kill_Blue);
                    TransitionManager.beginDelayedTransition(getView().findViewById(R.id.detail_teamBlue_kill_chart),fade);
                    kill_Blue.setVisibility(View.GONE);
                    fade.removeTarget(kill_Blue);
                }
                else {
                    slide.addTarget(kill_Blue);
                    TransitionManager.beginDelayedTransition(getView().findViewById(R.id.detail_teamBlue_kill_chart),slide);
                    kill_Blue.setVisibility(View.VISIBLE);
                    kill_Blue.requestFocus();
                    kill_Blue.animateY(1500, Easing.EaseInOutQuad);
                    slide.removeTarget(kill_Blue);
                }
            }
        }
    }

    @Override
    public void onNothingSelected() {

    }
}
