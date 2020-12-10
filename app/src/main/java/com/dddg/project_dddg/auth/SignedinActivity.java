package com.dddg.project_dddg.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.dddg.project_dddg.MainActivity;
import com.dddg.project_dddg.R;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.UserInfo;

public class SignedinActivity extends AppCompatActivity implements View.OnClickListener {

    private IdpResponse mIdpResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signedin);
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if(currentUser == null){
            finish();
            return;
        }

        mIdpResponse = IdpResponse.fromResultIntent(getIntent());

        setContentView(R.layout.activity_signedin);
        populateProfile();//로그인된 계정정보 확인

        Button signoutbtn=(Button)findViewById(R.id.signout);
        signoutbtn.setOnClickListener(this);

        Button deleteuser = (Button)findViewById(R.id.delete_account);
        deleteuser.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.signout://로그아웃 버튼
                signOut();
                Intent i = new Intent(this, FirebaseUiActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                Toast.makeText(this,"로그아웃",Toast.LENGTH_SHORT).show();
                break;
            case R.id.delete_account://계정 삭제 버튼
                deleteAccountClicked();
                break;
            default:
                break;
        }
    }

    public void out(){ //계정 삭제시 로그인 화면으로 돌아가는 함수
        Intent i = new Intent(this, FirebaseUiActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        Toast.makeText(this,"계정 삭제",Toast.LENGTH_SHORT).show();
    }

    private void signOut() {//로그아웃
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            finish();
                        } else {

                        }
                    }
                });
    }

    private void deleteAccountClicked(){ //계정 삭제
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setMessage("정말 계정을 삭제하시겠습니까?")
                .setPositiveButton("네", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteAccount();
                    }
                })
                .setNegativeButton("아니요",null)
                .create();
        dialog.show();
    }

    private void deleteAccount(){ //계정 삭제 함수
        AuthUI.getInstance()
                .delete(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            out();
                        }
                        else{

                        }
                    }
                });
    }

    private void populateProfile(){//로그인된 계정정보 확인
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        StringBuilder providerList = new StringBuilder(100);
        providerList.append("Providers used: ");
        if(user.getProviderData()==null||user.getProviderData().isEmpty()){
            providerList.append("none");
        }
        else{
            for(UserInfo profile : user.getProviderData()){
                String providerId = profile.getProviderId();
                if(GoogleAuthProvider.PROVIDER_ID.equals(providerId)){
                    providerList.append("Google");
                }
                else if (FacebookAuthProvider.PROVIDER_ID.equals(providerId)){
                    providerList.append("Facebook");
                }
                else if(EmailAuthProvider.PROVIDER_ID.equals(providerId)){
                    providerList.append("Email");
                }
                else {
                    providerList.append(providerId);
                }
            }
        }
    }
}