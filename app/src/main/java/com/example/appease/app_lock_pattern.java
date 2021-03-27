package com.example.appease;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.andrognito.patternlockview.utils.PatternLockUtils;
import com.example.appease.model.password;
import com.shuhart.stepview.StepView;

import java.util.List;

public class app_lock_pattern extends AppCompatActivity {
    StepView stepView;
    LinearLayout linearLayout;
    RelativeLayout relativeLayout;
    password Mpassword;
    String userPassword;
    TextView stateText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_lock_pattern);


        stepView = findViewById(R.id.stepView);
        linearLayout = findViewById(R.id.LL);
        relativeLayout = findViewById(R.id.main_layout);
        Mpassword = new password(this);
        stateText = findViewById(R.id.state_text);
        stateText.setText(Mpassword.FIRST_USE);
        if(Mpassword.getPASSWORD_KEY()==null){
            linearLayout.setVisibility(View.GONE);
            stepView.setVisibility(View.VISIBLE);
            stepView.setStepsNumber(2);
            stepView.go(0,true);
        } else {
            linearLayout.setVisibility(View.VISIBLE);
            stepView.setVisibility(View.GONE);
            int BackgroundColor = ResourcesCompat.getColor(getResources(),R.color.blue_400,null);
            relativeLayout.setBackgroundColor(BackgroundColor);
            stateText.setTextColor(Color.WHITE);
        }

        setUpPatternListener();
    }

    private void setUpPatternListener() {
        final PatternLockView patternLockView = findViewById(R.id.patternView);
        patternLockView.addPatternLockListener(new PatternLockViewListener() {
            @Override
            public void onStarted() {

            }

            @Override
            public void onProgress(List<PatternLockView.Dot> progressPattern) {

            }

            @Override
            public void onComplete(List<PatternLockView.Dot> pattern) {
                String password = PatternLockUtils.patternToString(patternLockView, pattern);
                if(password.length()<4){
                    stateText.setText(Mpassword.SCHEMA_FAILED);
                    patternLockView.clearPattern();
                    return;
                }
                if(Mpassword.getPASSWORD_KEY() ==null){
                    if(Mpassword.isFirst){
                        userPassword = password;
                        Mpassword.setFirst(false);
                        stateText.setText(Mpassword.CONFIRM_PATTERN);
                        stepView.go(1,true);
                    } else {
                        if(userPassword.equals(password)){
                            Mpassword.setPASSWORD_KEY(password);
                            stateText.setText(Mpassword.PATTERN_SET);
                            stepView.done(true);
                            goToMainActivity();
                        } else {
                            stateText.setText(Mpassword.PATTERN_SET);
                        }
                    }
                } else {
                    if(Mpassword.isCorrect(password)){
                        stateText.setText(Mpassword.PATTERN_SET);
                        goToMainActivity();
                    } else {
                        stateText.setText(Mpassword.INCORRECT_PATTERN);
                    }
                }
                patternLockView.clearPattern();


            }

            @Override
            public void onCleared() {

            }
        });

    }

    private void goToMainActivity() {
        Intent i = new Intent(app_lock_pattern.this, MainActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public void onBackPressed(){
        if(Mpassword.getPASSWORD_KEY() == null && !Mpassword.isFirst){
            stepView.go(0, true);
            Mpassword.setFirst(true);
            stateText.setText(Mpassword.FIRST_USE);
        } else {
            finish();
            super.onBackPressed();
        }
    }
}