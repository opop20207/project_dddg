package com.dddg.project_dddg;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;

import java.util.ArrayList;
import java.util.Arrays;

public class FragmentDetailOverview extends Fragment implements SeekBar.OnSeekBarChangeListener,
        OnChartValueSelectedListener {
    static FragmentDetailOverview instance;
    private FragmentDetailOverview() {
    }
    public static FragmentDetailOverview getInstance(){
        if(instance==null){
            instance = new FragmentDetailOverview();
        }
        return instance;
    }
    PieChart vote_rate;
    SeekBar seekBarX,seekBarY;
    int red_vote,blue_vote;
    MatchData matchData;
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

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        matchData = (MatchData) getActivity().getIntent().getSerializableExtra("matchdata");
        {
            seekBarX = getView().findViewById(R.id.seekBar1);
            seekBarY = getView().findViewById(R.id.seekBar2);
            seekBarX.setOnSeekBarChangeListener(this);
            seekBarY.setOnSeekBarChangeListener(this);
            vote_rate = getView().findViewById(R.id.detail_overview_pie);
            vote_rate.setUsePercentValues(true);
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
        } //파이 차트 관련
        red_vote = Integer.parseInt(matchData.gamescore.split("/")[0]);
        blue_vote = Integer.parseInt(matchData.gamescore.split("/")[1]);//임시데이터
        setData();

    }
    private void setData() {
        ArrayList entries = new ArrayList<>();
        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        entries.add(new PieEntry(red_vote,matchData.team1_name +"\n\n"+ Integer.toString(red_vote)+"킬"));
        entries.add(new PieEntry(blue_vote,matchData.team2_name +"\n\n"+ Integer.toString(blue_vote)+"킬"));
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
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
        data.setValueTypeface(Typeface.DEFAULT);
        vote_rate.setData(data);

        // undo all highlights
        vote_rate.highlightValues(null);
        vote_rate.invalidate();
    }//대충 pie 데이터 넣는 곳
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

    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }
}
