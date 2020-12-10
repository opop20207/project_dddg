package com.dddg.project_dddg.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.dddg.project_dddg.FragmentFrame;
import com.dddg.project_dddg.MainActivity;
import com.dddg.project_dddg.R;
import com.dddg.project_dddg.UserData;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FirebaseUiActivity extends AppCompatActivity {
    DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("User");
    private static final int RC_SIGN_IN = 1000;
    FirebaseUser user_f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_firebase_ui);

        signin();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RC_SIGN_IN){
            IdpResponse response = IdpResponse.fromResultIntent(data);
            if(resultCode == RESULT_OK){//로그인 성공시 맵 화면으로 넘어감
                user_f = FirebaseAuth.getInstance().getCurrentUser();
                userRef.child(user_f.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        UserData user = snapshot.getValue(UserData.class);
                        if(user==null){ // 데이터 세팅
                            user = new UserData();
                            userRef.child(user_f.getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                       toFragmentHome();
                                    }
                                }
                            });
                        }
                        else toFragmentHome();
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
            else{
                finish();
            }
        }
    }
    private void toFragmentHome(){
        Intent i = new Intent(this, FragmentFrame.class);
        Toast.makeText(this,"로그인 성공",Toast.LENGTH_SHORT).show();
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        finish();
    }
    private void signin(){//로그인 창 화면
        startActivityForResult(
                AuthUI.getInstance().createSignInIntentBuilder()
                        .setAvailableProviders(getSelectedProviders())
                        .setTheme(R.style.LoginTheme)
                        .setLogo(R.drawable.penguin)
                        .setTosAndPrivacyPolicyUrls("", "")
                        .setIsSmartLockEnabled(true)
                        .build(),RC_SIGN_IN
        );
    }

    private int getSelectedTheme(){
        return R.style.AppTheme;
    } //로그인 화면 별 테마 구성

    private int getSelectedLogo(){
        return R.drawable.riot_image;
    }// 로그인 화면 별 로고 구성

    private List<AuthUI.IdpConfig> getSelectedProviders(){//로그인 버튼 추가
        List<AuthUI.IdpConfig> selectedProviders = new ArrayList<>();
        selectedProviders.add(new AuthUI.IdpConfig.GoogleBuilder().build());
        //selectedProviders.add(new AuthUI.IdpConfig.FacebookBuilder().build());
        selectedProviders.add(new AuthUI.IdpConfig.EmailBuilder().build());
        return selectedProviders;
    }
}