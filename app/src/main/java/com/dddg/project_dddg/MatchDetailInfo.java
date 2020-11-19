package com.dddg.project_dddg;

import android.os.Build;
import android.os.Bundle;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.dddg.project_dddg.adapter.FragmentDetailVPAdapter;
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
    TextView title;
    TextView score;
    ConstraintLayout constraintLayout;
    // 몇개 더 필요
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.match_detail_frame);
        title = findViewById(R.id.detail_match_title);
        score = findViewById(R.id.detail_teamScore);
        teamRed = findViewById(R.id.detail_teamRed_ImgBtn);
        teamBlue = findViewById(R.id.detail_teamBlue_ImgBtn);
        constraintLayout = findViewById(R.id.detail_comment_layout);
        constraintLayout.setVisibility(View.GONE);
        matchData = (MatchData) this.getIntent().getSerializableExtra("matchdata");
        Glide.with(this).load(TeamImgUrl.Url(matchData.team1_name)).fitCenter().into(teamRed);
        Glide.with(this).load(TeamImgUrl.Url(matchData.team2_name)).fitCenter().into(teamBlue);
        teamRedName = findViewById(R.id.detail_teamRed_name);
        teamBlueName = findViewById(R.id.detail_teamBlue_name);
        title.setText(matchData.title+" "+matchData.stage);
        score.setText(matchData.gamescore.split("/")[0]+" - "+matchData.gamescore.split("/")[1]);
        teamRedName.setText(matchData.team1_name);
        teamBlueName.setText(matchData.team2_name);
        ViewPager2 viewPager = findViewById(R.id.detail_viewpager);
        fragmentDetailVPAdapter = new FragmentDetailVPAdapter(this);
        viewPager.setAdapter(fragmentDetailVPAdapter);
        viewPager.setSaveEnabled(false);
        Transition slide = new Slide(Gravity.BOTTOM);
        slide.setDuration(600);
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }
            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        slide.addTarget(constraintLayout);
                        TransitionManager.beginDelayedTransition(findViewById(R.id.detail_comment_layout),slide);
                        constraintLayout.setVisibility(View.GONE);
                        slide.removeTarget(constraintLayout);
                        break;
                    case 1:
                        slide.addTarget(constraintLayout);
                        TransitionManager.beginDelayedTransition(findViewById(R.id.detail_comment_layout),slide);
                        constraintLayout.setVisibility(View.VISIBLE);
                        slide.removeTarget(constraintLayout);
                        break;
                    default:
                        constraintLayout.setVisibility(View.GONE);
                        break;
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });
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
