package com.dddg.project_dddg.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.dddg.project_dddg.FragmentFrame;
import com.dddg.project_dddg.MainActivity;
import com.dddg.project_dddg.R;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;

import java.util.ArrayList;
import java.util.List;

public class FirebaseUiActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase_ui);

        signin();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RC_SIGN_IN){
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if(resultCode == RESULT_OK){//로그인 성공시 맵 화면으로 넘어감
                Intent i = new Intent(this, FragmentFrame.class);
                Toast.makeText(this,"로그인 성공",Toast.LENGTH_SHORT).show();
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();
            }
            else{// 실패시 다시 첫 화면으로 돌아감
                Intent i = new Intent(this, AuthActivity.class);
                Toast.makeText(this,"로그인 실패\n다시 로그인 해주세요",Toast.LENGTH_SHORT).show();
                startActivity(i);
                finish();
            }
        }
    }

    private void signin(){//로그인 창 화면
        startActivityForResult(
                AuthUI.getInstance().createSignInIntentBuilder()
                        .setTheme(getSelectedTheme())
                        .setLogo(getSelectedLogo())
                        .setAvailableProviders(getSelectedProviders())
                        .setTosAndPrivacyPolicyUrls("", "")
                        .setIsSmartLockEnabled(true)
                        .build(),RC_SIGN_IN
        );
    }

    private int getSelectedTheme(){
        return AuthUI.getDefaultTheme();
    } //로그인 화면 별 테마 구성

    private int getSelectedLogo(){
        return R.mipmap.ic_launcher;
    }// 로그인 화면 별 로고 구성

    private List<AuthUI.IdpConfig> getSelectedProviders(){//로그인 버튼 추가
        List<AuthUI.IdpConfig> selectedProviders = new ArrayList<>();
        selectedProviders.add(new AuthUI.IdpConfig.GoogleBuilder().build());
        //selectedProviders.add(new AuthUI.IdpConfig.FacebookBuilder().build());
        selectedProviders.add(new AuthUI.IdpConfig.EmailBuilder().build());
        return selectedProviders;
    }
}