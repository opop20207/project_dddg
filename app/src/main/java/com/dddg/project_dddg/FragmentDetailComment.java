package com.dddg.project_dddg;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Comment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class FragmentDetailComment extends Fragment {
    static FragmentDetailComment instance;
    private FragmentDetailComment() {
    }
    public static FragmentDetailComment getInstance(){
        if(instance==null){
            instance = new FragmentDetailComment();
        }
        return instance;
    }
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    CommentRVAdapter commentRVAdapter;
    MatchData matchData;
    ArrayList<CommentData> commentList = new ArrayList();
    String key;
    EditText commentText;
    Button commentBtn;
    DatabaseReference dateRef = FirebaseDatabase.getInstance().getReference("Comment");
    FirebaseUser auth;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM월 dd일 HH:mm:ss");
    Date now;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.match_detail_fragment_comment,container,false);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        auth = FirebaseAuth.getInstance().getCurrentUser();
        matchData = (MatchData) getActivity().getIntent().getSerializableExtra("matchdata");
        recyclerView = getView().findViewById(R.id.detail_comment_recyclerview);
        commentText = getActivity().findViewById(R.id.detail_comment_upload_edittext);
        commentBtn = getActivity().findViewById(R.id.detail_comment_upload_btn);
        linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setHasFixedSize(false);
        commentRVAdapter = new CommentRVAdapter(commentList);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(commentRVAdapter);
        key = matchData.getKey();
        dateRef.child(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                commentList.clear();
                if(snapshot !=null) {
                    for (DataSnapshot snap : snapshot.getChildren()) {
                        HashMap<String, Object> Hash = (HashMap<String, Object>) snap.getValue();
                        CommentData commentData = new CommentData(Hash.get("id").toString(), Hash.get("context").toString(),Hash.get("time").toString());
                        commentList.add(commentData);
                    }
                }
                commentRVAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        commentBtn.setOnClickListener(v -> {
            if(commentText.getText().toString()!=""){
                now = new Date();
                CommentData upload = new CommentData(auth.getEmail(),commentText.getText().toString(),simpleDateFormat.format(now));
                dateRef.child(key).push().setValue(upload).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                      if(task.isSuccessful()){
                          Toast.makeText(getActivity(), "게시완료", Toast.LENGTH_SHORT).show();
                          commentText.setText("");
                      }
                    }
                });
            }
            else{
                Toast.makeText(getActivity(), "comment가 비었습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
