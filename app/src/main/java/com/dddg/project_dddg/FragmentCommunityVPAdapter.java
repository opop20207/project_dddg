package com.dddg.project_dddg;

import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.adapter.FragmentViewHolder;

public class FragmentCommunityVPAdapter extends FragmentStateAdapter {

    final int itemSize  =2 ;
    public FragmentCommunityVPAdapter(Fragment fragment) {
        super(fragment);
    }
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0: return FragmentCommunityFreeboard.getInstance();
            case 1: return FragmentCommunityPredict.getInstance();
            default: return null;
        }
        //Fragment를 교체 구현한 것
    }
    @Override
    public int getItemCount() {
        return itemSize;
    }

}
