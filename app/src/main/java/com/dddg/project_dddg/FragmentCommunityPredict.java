package com.dddg.project_dddg;

import android.content.Context;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dddg.project_dddg.adapter.MatchPlanRVAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FragmentCommunityPredict extends Fragment {
    static FragmentCommunityPredict instance;
    DatabaseReference dataRef = FirebaseDatabase.getInstance().getReference("Schedule");
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    MatchPlanRVAdapter adapter;
    ArrayList<MatchPlanData> matchPlanDataArrayList= new ArrayList<>();
    ArrayList<String> ref= new ArrayList<>();
    public FragmentCommunityPredict() {
    }

    public static FragmentCommunityPredict getInstance(){
        if(instance==null){
            instance = new FragmentCommunityPredict();
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
        return inflater.inflate(R.layout.community_fragment_predict,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView = getView().findViewById(R.id.community_predict_recyclerview);
        linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setHasFixedSize(true);
        adapter = new MatchPlanRVAdapter(matchPlanDataArrayList,ref,getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        dataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                matchPlanDataArrayList.clear();
                ref.clear();
                for(DataSnapshot snap:snapshot.getChildren()){
                    MatchPlanData newone = snap.getValue(MatchPlanData.class);
                    newone.team1_vote = newone.team1_vote+1;
                    newone.team2_vote = newone.team2_vote+1;
                    matchPlanDataArrayList.add(newone);
                    ref.add(snap.getKey());
                }
                adapter.notifyDataSetChanged();
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
}
