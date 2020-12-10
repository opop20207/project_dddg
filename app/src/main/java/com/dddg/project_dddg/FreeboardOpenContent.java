package com.dddg.project_dddg;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.util.Log;
import android.util.Pair;
import android.view.Gravity;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dddg.project_dddg.adapter.CommentRVAdapter;
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
import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

public class FreeboardOpenContent extends AppCompatActivity {
    String contentKey;
    DatabaseReference dataRef = FirebaseDatabase.getInstance().getReference("Freeboard");
    FreeboardData freeboardData;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    CommentRVAdapter adapter;
    TextView email,date,title,context;
    ArrayList<CommentData> commentData = new ArrayList<>();
    CheckBox anonyCheck;
    EditText commentEditText;
    ConstraintLayout constraintLayout;
    Button uploadComment;
    ImageButton cancel;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd");
    FirebaseUser auth = FirebaseAuth.getInstance().getCurrentUser();
    DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("User");
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.community_freeboard_open_content);
        //기본 세팅
        email = findViewById(R.id.freeboard_open_email);
        date = findViewById(R.id.freeboard_open_date);
        title = findViewById(R.id.freeboard_open_title);
        context = findViewById(R.id.freeboard_open_context);
        anonyCheck = findViewById(R.id.freeboard_open_anonycheck);
        cancel = findViewById(R.id.freeboard_open_cancel_button);
        commentEditText = findViewById(R.id.freeboard_open_edittext_comment);
        uploadComment = findViewById(R.id.freeboard_open_upload_comment);
        constraintLayout = findViewById(R.id.freeboard_open_comment_layout);
        //id finding



        recyclerView = findViewById(R.id.freeboard_open_recyclerview);
        recyclerView.setHasFixedSize(true);

        linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        adapter = new CommentRVAdapter(commentData);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        contentKey = this.getIntent().getStringExtra("key"); //child 경로
        anonyCheck.setChecked(true); //기본적으로 익명칸 체크
        constraintLayout.setVisibility(View.INVISIBLE); // 기본적으로는 안보임
        Transition slide = new Slide(Gravity.BOTTOM);
        slide.setDuration(1000);

        ///////data 로딩////////
        dataRef.child(contentKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                commentData.clear();
                freeboardData = snapshot.getValue(FreeboardData.class);
                if(freeboardData.anonymouse) email.setText("익명");
                else email.setText(freeboardData.getEmail());
                date.setText(freeboardData.getDate());
                title.setText(freeboardData.title); //원본필요
                context.setText(freeboardData.context);//원본필요
                commentData.addAll(freeboardData.getComment());
                adapter.notifyDataSetChanged();

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                slide.addTarget(constraintLayout);
                TransitionManager.beginDelayedTransition(constraintLayout,slide);
                constraintLayout.setVisibility(View.VISIBLE);
                slide.removeTarget(constraintLayout);
            }
        },100);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        uploadComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uploadText = commentEditText.getText().toString();
                if(uploadText.equals("")){
                    Toast.makeText(FreeboardOpenContent.this, "칸을 입력해주세요", Toast.LENGTH_LONG).show();
                }
                else{
                    Date now = new Date();
                    CommentData upload = new CommentData(auth.getEmail(),commentEditText.getText().toString(),simpleDateFormat.format(now),anonyCheck.isChecked());
                    freeboardData.addComment(upload);
                    dataRef.child(contentKey).updateChildren(freeboardData.toMap()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                userRef.child(auth.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        UserData userData = snapshot.getValue(UserData.class);
                                        userData.commentAdd(new Pair<CommentData,String>(upload,contentKey));
                                        userRef.child(auth.getUid()).updateChildren(userData.toMap()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful()){
                                                    Toast.makeText(FreeboardOpenContent.this, "댓글완료", Toast.LENGTH_SHORT).show();
                                                    commentEditText.setText("");
                                                }
                                            }
                                        });
                                    }
                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });

                            }
                        }
                    });
                }
            }
        });





    }
}
