package com.dddg.project_dddg;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.ArrayRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dddg.project_dddg.adapter.FreeboardRVAdapter;
import com.dddg.project_dddg.auth.SignedinActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FragmentOption extends Fragment {
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    static FragmentOption instance;
    public FragmentOption() {
    }

    public static FragmentOption getInstance(){
        if(instance==null){
            instance = new FragmentOption();
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
        ViewGroup rootview = (ViewGroup)inflater.inflate(R.layout.fragment_option,container,false);
        Button setting = (Button)rootview.findViewById(R.id.setting);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),SignedinActivity.class);
                startActivity(intent);
            }
        });
        return rootview;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ImageView img = getView().findViewById(R.id.imgprofile);
        TextView tvname = getView().findViewById(R.id.tvname);
        TextView tvscore = getView().findViewById(R.id.tvscore);
        tvname.setText(user.getEmail());


        ArrayList<FreeboardData> freeboardData = new ArrayList<>();
        ArrayList<String> contextKeys = new ArrayList<>();
        FreeboardRVAdapter adapter = new FreeboardRVAdapter(freeboardData);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        RecyclerView recyclerView = getView().findViewById(R.id.profile_rv_activity);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new FreeboardRVAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent openContent = new Intent(getContext(),FreeboardOpenContent.class);
                openContent.putExtra("key",contextKeys.get(position));
                startActivity(openContent);
                //클릭시 이벤트
            }
        });
        ref.child("User").child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserData userData = snapshot.getValue(UserData.class);
                tvscore.setText(Integer.toString(userData.point)+" 점");
                if(userData.myTeamList.size()>0){
                    Glide.with(img).load(TeamImgUrl.Url(userData.myTeamList.get(0).teamname)).into(img);
                }
                freeboardData.clear();
                for(DataSnapshot snap : snapshot.child("freeBoardList").getChildren()){
                    contextKeys.add(snap.getValue(String.class));
                    ref.child("Freeboard").child(snap.getValue(String.class)).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            freeboardData.add(snapshot.getValue(FreeboardData.class));
                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }


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
