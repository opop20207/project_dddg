package com.dddg.project_dddg;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragmentCommunityFreeboard extends Fragment {
    static FragmentCommunityFreeboard instance;
    private FragmentCommunityFreeboard() {
    }

    public static FragmentCommunityFreeboard getInstance(){
        if(instance==null){
            instance = new FragmentCommunityFreeboard();
        }
        return instance;
    }
    @Nullable
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.community_fragment_freeboard,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);



    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }
}