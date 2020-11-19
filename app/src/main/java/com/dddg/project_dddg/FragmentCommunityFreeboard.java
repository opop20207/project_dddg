package com.dddg.project_dddg;

import android.content.Context;
import android.content.Intent;
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

import com.dddg.project_dddg.adapter.FreeboardRVAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class FragmentCommunityFreeboard extends Fragment {
    static FragmentCommunityFreeboard instance;
    public FragmentCommunityFreeboard() {
    }
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    FreeboardRVAdapter adapter;
    ArrayList<FreeboardData> freeboardDataArrayList = new ArrayList<>();
    DatabaseReference dataRef= FirebaseDatabase.getInstance().getReference("Freeboard");
    FloatingActionButton newcontent;
    ArrayList<String> contextKeys = new ArrayList<>();
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
        recyclerView = getView().findViewById(R.id.freeboard_recyclerview);
        linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setHasFixedSize(true);
        adapter = new FreeboardRVAdapter(freeboardDataArrayList);
        adapter.setOnItemClickListener(new FreeboardRVAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent openContent = new Intent(getContext(),FreeboardOpenContent.class);
                openContent.putExtra("key",contextKeys.get(position));
                startActivity(openContent);
                //클릭시 이벤트
            }
        });
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        dataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                freeboardDataArrayList.clear();
                contextKeys.clear();
                for(DataSnapshot snap : snapshot.getChildren()){
                    HashMap<String,Object> hashMap = (HashMap<String, Object>) snap.getValue();
                    FreeboardData freeboardData = new FreeboardData(hashMap.get("title").toString()
                            ,hashMap.get("context").toString()
                            ,hashMap.get("date").toString()
                            ,hashMap.get("email").toString()
                            ,(ArrayList<CommentData>) hashMap.get("comment")
                            ,Boolean.parseBoolean(hashMap.get("anonymouse").toString()));
                    freeboardDataArrayList.add(freeboardData);
                    contextKeys.add(snap.getKey());
                }
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        newcontent = getView().findViewById(R.id.freeboard_new_post);
        newcontent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newcontext = new Intent(getActivity(),FreeboardNewContent.class);
                startActivity(newcontext);
            }
        });
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }
}
