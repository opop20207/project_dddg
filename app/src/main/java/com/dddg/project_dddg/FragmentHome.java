package com.dddg.project_dddg;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.load.resource.bitmap.CenterInside;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;

public class FragmentHome extends Fragment {
    DatabaseReference dataRef = FirebaseDatabase.getInstance().getReference("match");
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    MatchRVAdapter adapter;
   HorizontalScrollView scrollView;
    ArrayList<MatchData> matchData = new ArrayList<MatchData>(Arrays.asList(new MatchData()));
    ArrayList<MatchData> matchDataOrdered = new ArrayList<MatchData>(Arrays.asList(new MatchData()));
    ArrayList<String> dateList = new ArrayList<String>();
    String checked_date = new String();
    static FragmentHome instance;

    private FragmentHome() {
    }

    public static FragmentHome getInstance() {
        if (instance == null) {
            instance = new FragmentHome();
        }
        return instance;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        {
            RadioGroup radioGroup = (RadioGroup) getView().findViewById(R.id.RadioGroup);
            SimpleDateFormat simpleDate = new SimpleDateFormat("E\ndd");
            SimpleDateFormat simpleDateToday= new SimpleDateFormat("오늘\ndd");
            SimpleDateFormat simpleDateforSort = new SimpleDateFormat("MMdd");
            Date today = new Date();
            int range_date = 30;
            //radiobutton 날짜 범위 30 -> 30일 전부터 30일 후까지
            for(int i=(-1*range_date); i<=range_date;i++) {
                RadioButton rdbtn = new RadioButton(getContext());
                Calendar cal = new GregorianCalendar(Locale.KOREA);
                cal.setTime(today);
                cal.add(Calendar.DATE, i);
                String dDate = simpleDate.format(cal.getTime());
                rdbtn.setId(i+range_date);
                dateList.add(simpleDateforSort.format(cal.getTime()).toString());
                if(i == 0 ){
                    rdbtn.setChecked(true);
                    rdbtn.setText(simpleDateToday.format(cal.getTime()));
                    rdbtn.setTextColor(getResources().getColor(R.color.subDarkBlue));
                    checked_date = simpleDateforSort.format(cal.getTime());
                }
                else{
                    rdbtn.setText(dDate);
                    rdbtn.setTextColor(Color.WHITE);
                }
                rdbtn.setLayoutParams(new LinearLayout.LayoutParams(75, ViewGroup.LayoutParams.MATCH_PARENT,1));
                rdbtn.setTextSize(15);
                rdbtn.setGravity(Gravity.CENTER);
                rdbtn.setBackground(getResources().getDrawable(R.drawable.radiobutton));
                rdbtn.setButtonDrawable(getResources().getDrawable(R.color.fui_transparent));
                rdbtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked) {
                            buttonView.setTextColor(getResources().getColor(R.color.subDarkBlue));
                            buttonView.setChecked(true);
                            checked_date = dateList.get(buttonView.getId());
                            matchDataOrdered.clear();
                            matchDataOrdered.addAll(setDate(checked_date,matchData));
                            adapter.notifyDataSetChanged();
                            Log.d("로그","선택 date: "+checked_date);
                        }
                        else {
                            buttonView.setTextColor(Color.WHITE);
                            buttonView.setChecked(false);
                        }
                    }
                });
                radioGroup.addView(rdbtn);
            }
            RadioButton focus = getView().findViewById(radioGroup.getCheckedRadioButtonId());
            scrollView = getView().findViewById(R.id.h_scrollView);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    int endPos = (int)focus.getX();
                    int halfWidth = (int)focus.getWidth()/2;
                    scrollView.smoothScrollTo(endPos + halfWidth - scrollView.getWidth()/2,0);
                    //가운데로 스크롤 이동
                }
            },500);
        }
        recyclerView = getView().findViewById(R.id.home_recyclerview);
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new MatchRVAdapter(matchDataOrdered);
        adapter.setOnItemClickListener(new MatchRVAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent detailintent = new Intent(getActivity(), MatchDetailInfo.class);
                detailintent.putExtra("matchdata", matchData.get(position));
                startActivity(detailintent);
                // 클릭되었을때
            }
        });
        recyclerView.setAdapter(adapter);
        ProgressDialog Dialog = new ProgressDialog(getActivity());
        Dialog.setMessage("경기정보 수신중");
        Dialog.show();
        dataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                matchData.clear();
                for (DataSnapshot snap : snapshot.getChildren()) {
                    HashMap<String, String> matchDataHash = (HashMap<String, String>) snap.getValue();
                    MatchData match = new MatchData(
                            matchDataHash.get("date").toString(),
                            matchDataHash.get("title").toString(),
                            matchDataHash.get("stage").toString(),
                            matchDataHash.get("winteam").toString(),
                            matchDataHash.get("gamescore").toString(),
                            matchDataHash.get("gametime").toString(),
                            matchDataHash.get("team1_name").toString(),
                            matchDataHash.get("team2_name").toString(),
                            matchDataHash.get("team1_teamscore").toString(),
                            matchDataHash.get("team2_teamscore").toString(),
                            matchDataHash.get("team1_playername").toString(),
                            matchDataHash.get("team2_playername").toString(),
                            matchDataHash.get("team1_playerscore").toString(),
                            matchDataHash.get("team2_playerscore").toString(),
                            matchDataHash.get("team1_champ").toString(),
                            matchDataHash.get("team2_champ").toString(),
                            matchDataHash.get("team1_ban").toString(),
                            matchDataHash.get("team2_ban").toString()
                    );
                    matchData.add(match);
                }
                matchDataOrdered.clear();
                matchDataOrdered.addAll(setDate(checked_date,matchData));
                adapter.notifyDataSetChanged();
                Dialog.hide();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    public ArrayList<MatchData> setDate(String date,ArrayList<MatchData> matchData){
        ArrayList<MatchData> setDate = new ArrayList();
        for(MatchData match:matchData){
            if(match.date.substring(4).contains(date))
                setDate.add(match);
        }
        return setDate;
    }
}
