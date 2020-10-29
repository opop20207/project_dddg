package com.dddg.project_dddg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.dddg.project_dddg.auth.FirebaseUiActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Handler h = new Handler();
        h.postDelayed(new splashHandler(),2000);
    }

    private class splashHandler implements Runnable{
        public void run(){
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if(user!=null){
                Intent i = new Intent(MainActivity.this, FragmentFrame.class);
                startActivity(i);
                finish();
            }else{
                startActivity(new Intent(getApplication(), FirebaseUiActivity.class)); //로딩이 끝난 후, ChoiceFunction 이동
                MainActivity.this.finish(); // 로딩페이지 Activity stack에서 제거
            }
        }
    }

    @Override
    public void onBackPressed() {
        //초반 플래시 화면에서 넘어갈때 뒤로가기 버튼 못누르게 함
    }
}