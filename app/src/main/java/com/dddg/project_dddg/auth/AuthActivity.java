package com.dddg.project_dddg.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.dddg.project_dddg.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AuthActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        Button loginbtn = (Button)findViewById(R.id.loginbtn);
        loginbtn.setOnClickListener(this);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


    }

    @Override
    public void onClick(View view){
        switch(view.getId()){
            case R.id.loginbtn:
                Intent i = new Intent(this, FirebaseUiActivity.class);
                startActivity(i);
                finish();
                break;
            default:
                break;
        }
    }
}