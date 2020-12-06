package com.dddg.project_dddg;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.dddg.project_dddg.adapter.FragmentCommunityVPAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.Arrays;

public class FragmentCommunity extends Fragment {

    static FragmentCommunity instance;
    public FragmentCommunity() {
    }

   private FragmentCommunityVPAdapter fragmentCommunityVPAdapter;

    public static FragmentCommunity getInstance(){
        if(instance==null){
            instance = new FragmentCommunity();
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
       return inflater.inflate(R.layout.fragment_community,container,false);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ViewPager2 viewPager = getView().findViewById(R.id.community_viewpager);
        fragmentCommunityVPAdapter = new FragmentCommunityVPAdapter(this);
        viewPager.setAdapter(fragmentCommunityVPAdapter);
        viewPager.setSaveEnabled(false);
        final ArrayList<String> tablayoutString = new ArrayList<String>(Arrays.asList("자유게시판","경기예측"));
        TabLayout tabLayout = getView().findViewById(R.id.community_tablayout);
        tabLayout.setTabIndicatorFullWidth(true);
        new TabLayoutMediator(tabLayout,viewPager,
                (tab,position)-> tab.setText(tablayoutString.get(position))
        ).attach();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }
}
