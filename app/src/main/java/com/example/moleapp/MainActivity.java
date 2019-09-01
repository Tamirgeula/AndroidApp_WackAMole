package com.example.moleapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {


    private EditText playerNameEditText;
    private Button StartGameButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StartGameButton=findViewById(R.id.StartGameButton);

        Intent startGame=new Intent( MainActivity.this,GameActivity.class);


        StartGameButton.setOnClickListener((View v) -> {
            playerNameEditText=findViewById(R.id.PlayerNameEditText);
            String playerName=playerNameEditText.getText().toString();

            if(!playerName.isEmpty()) {

                startGame.putExtra("playerName",playerName);
                startActivity(startGame);

            }
            else {

                Animation shake = AnimationUtils.loadAnimation(MainActivity.this,R.anim.left_right_shake);
                playerNameEditText.startAnimation(shake);
            }

        });
    }


}
