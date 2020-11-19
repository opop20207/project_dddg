package com.dddg.project_dddg;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.ComponentActivity;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.dddg.project_dddg.auth.FirebaseUiActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class FreeboardNewContent extends AppCompatActivity {

    ImageButton cancel_btn;
    Button submit_btn;
    EditText title_edit;
    EditText context_edit;
    CheckBox anony_chk;
    FirebaseUser user;
    DatabaseReference dataRef = FirebaseDatabase.getInstance().getReference("Freeboard");
    Date now;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.community_freeboard_new_content);
        cancel_btn = findViewById(R.id.freeboard_cancel_button);
        submit_btn = findViewById(R.id.freeboard_submit_button);
        title_edit = findViewById(R.id.freeboard_title_editText);
        context_edit = findViewById(R.id.freeboard_context_editText);
        anony_chk = findViewById(R.id.freeboard_anonymous_check);
        anony_chk.setChecked(true);
        user = FirebaseAuth.getInstance().getCurrentUser();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
        //기본 세팅
        //뒤로가기
        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //완료 눌렀을때
        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean anony = false;
                String title = title_edit.getText().toString();
                String context = context_edit.getText().toString();
                if(!title.isEmpty()&&!context.isEmpty()) {
                    if (anony_chk.isChecked()) anony = true;
                    FreeboardData freeboardData = new FreeboardData(
                            title
                            ,context
                            ,dateFormat.format(new Date())
                            ,user.getEmail()
                            ,new ArrayList<CommentData>()
                            ,anony);
                    String key = dataRef.push().getKey();
                    dataRef.child(key).setValue(freeboardData).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                finish();
                            }
                        }
                    });
                }
                else{
                    Toast.makeText(FreeboardNewContent.this, "제목과 내용을 작성해 주세요", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
