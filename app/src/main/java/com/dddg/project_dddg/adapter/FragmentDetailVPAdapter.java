package com.dddg.project_dddg.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.dddg.project_dddg.FragmentDetailComment;
import com.dddg.project_dddg.FragmentDetailOverview;

public class FragmentDetailVPAdapter extends FragmentStateAdapter {

    final int itemSize  = 2 ;
    public FragmentDetailVPAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 1: return FragmentDetailComment.getInstance();
            default: return FragmentDetailOverview.getInstance();
        }
        //Fragment를 교체 구현한 것
    }
    @Override
    public int getItemCount() {
        return itemSize;
    }

}
