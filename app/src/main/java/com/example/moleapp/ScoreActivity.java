package com.example.moleapp;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ScoreActivity extends AppCompatActivity {

    private Player.status res;

    private TextView welcomeMsgTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        res=Player.status.valueOf(getIntent().getStringExtra("playerStatus"));
        welcomeMsgTextView=(TextView)findViewById(R.id.welcomeMsgTextView);
        if(res==Player.status.WIN){
            welcomeMsgTextView.setText("Congragulation! you won!");
        }
        if(res==Player.status.LOSE){
            welcomeMsgTextView.setText("Sorry! you lost!");
        }
        if(res==Player.status.OUT_OF_TIME){
            welcomeMsgTextView.setText("Sorry, you lost, out of time!");
        }
    }
}
