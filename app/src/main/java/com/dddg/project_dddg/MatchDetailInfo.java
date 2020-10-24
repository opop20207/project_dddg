package com.dddg.project_dddg;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.Arrays;

public class MatchDetailInfo extends AppCompatActivity {
    FragmentDetailOverview fragment_detail_overview;
    private FragmentDetailVPAdapter fragmentDetailVPAdapter;
    MatchData matchData;
    ImageButton teamRed;
    ImageButton teamBlue;
    ImageButton backward_btn;
    TextView teamRedName;
    TextView teamBlueName;
    // 몇개 더 필요
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.match_detail_frame);
        teamRed = findViewById(R.id.detail_teamRed_ImgBtn);
        teamBlue = findViewById(R.id.detail_teamBlue_ImgBtn);
        matchData = (MatchData) this.getIntent().getSerializableExtra("matchdata");
        Glide.with(this).load(TeamImgUrl.Url(matchData.team1_name)).fitCenter().into(teamRed);
        Glide.with(this).load(TeamImgUrl.Url(matchData.team2_name)).fitCenter().into(teamBlue);
        teamRedName = findViewById(R.id.detail_teamRed_name);
        teamBlueName = findViewById(R.id.detail_teamBlue_name);
        teamRedName.setText(matchData.team1_name);
        teamBlueName.setText(matchData.team2_name);
        ViewPager2 viewPager = findViewById(R.id.detail_viewpager);
        fragmentDetailVPAdapter = new FragmentDetailVPAdapter(this);
        viewPager.setAdapter(fragmentDetailVPAdapter);
        viewPager.setSaveEnabled(false);
        final ArrayList<String> tablayoutString = new ArrayList<String>(Arrays.asList("Overview","Comment"));
        TabLayout tabLayout = findViewById(R.id.detail_tablayout);
        new TabLayoutMediator(tabLayout,viewPager,
                (tab,position)-> tab.setText(tablayoutString.get(position))
        ).attach();
        backward_btn = findViewById(R.id.detail_match_backward);
        backward_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
