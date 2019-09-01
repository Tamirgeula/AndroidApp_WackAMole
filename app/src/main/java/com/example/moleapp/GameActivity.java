package com.example.moleapp;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class GameActivity extends AppCompatActivity {

    private final String SCORE_THREAD="ScoreThread;",MOLES_THREAD="MolesThread",TIME_THREAD="TimeThread";
    private final String [] THREADS_NAME={SCORE_THREAD,MOLES_THREAD,TIME_THREAD};




    private TextView playerNameTextView;
    private GridView boardGridView;
    private EditText timeEditText,hitsEditText,missesEditText;

    private Player player;
    private ImageViewAdapter imgAdapter;
    private Queue<Slot> jumpingMoles=new LinkedList<>();


    private Timer [] timers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        player=new Player( getIntent().getStringExtra("playerName"));

        initUI_components();

        initThreads();









    }

    private TimerTask getTask(String taskStr)  {
        TimerTask task=null;
                task=new TimerTask()  {
                    @Override
                    public void run() {
                        switch (taskStr) {
                            case (SCORE_THREAD): {
                                if(!updateUIScore())
                                    terminateAllThreads();

                            }
                            break;
                            case (MOLES_THREAD):{
                                generateUIMole();
                            }
                            break;
                            case (TIME_THREAD):{
                               if(!updateUITimer())
                                   terminateAllThreads();
                            }
                            break;

                        }
                    }


                };
        return task;



    }


    private boolean updateUITimer(){

        String timeLeft = timeEditText.getText().toString();

        if (timeLeft.compareTo("00:00") == 0) {
            player.setRes(Player.status.OUT_OF_TIME);
            return false;

        }

        String [] secsAndCentiSecs=timeLeft.split(":");
        int seconds= Integer.parseInt(secsAndCentiSecs[0]);

        int centiSeconds=Integer.parseInt(secsAndCentiSecs[1]);

        if(centiSeconds!=0)
            centiSeconds--;
        else{
            centiSeconds=99;
            seconds--;
        }


        String newTime=null;



        if(seconds<10)
             newTime=String.format("0%d:%d",seconds,centiSeconds);

        if(centiSeconds<10)
            newTime=String.format("%d:0%d",seconds,centiSeconds);

        if(seconds<10 && centiSeconds<10)
            newTime=String.format("0%d:0%d",seconds,centiSeconds);
        if(seconds>=10 && centiSeconds>=10)
            newTime=String.format("%d:%d",seconds,centiSeconds);

        timeEditText.setText(newTime);

        return true;



    }
    private void generateUIMole(){
        Random rand = new Random();
        int moleCellIndex = rand.nextInt(9);

        AnimationDrawable moleAnim= ImageViewAdapter.getMolesAnim()[moleCellIndex];
        Slot cell = (Slot) ImageViewAdapter.getBoard()[moleCellIndex].getTag();


        moleAnim.start();

        ImageViewAdapter.getBoard()[cell.getPos()].postDelayed(new Runnable() {
            @Override
            public void run() {
                if (moleAnim.isRunning())
                    ImageViewAdapter.resetAnimation(cell.getPos());
                jumpingMoles.add(cell);
                }
            },1200);


    }
    private boolean updateUIScore() {

            while (jumpingMoles.isEmpty()){};

            Slot temp = jumpingMoles.remove();

            if(player.getHits()!=Player.WIN_HITS && player.getMisses()!=Player.LOST_MISSES) {//case game on


                if (temp.isHit()) {
                    player.hit();
                    hitsEditText.setText(Integer.toString(player.getHits()));
                    temp.setHit(false);
                } else {
                    player.miss();
                    missesEditText.setText(Integer.toString(player.getMisses()));
                }
                return true;
            }
            else {//case end game because score
                if(player.getHits()==Player.WIN_HITS)
                    player.setRes(Player.status.WIN);
                if(player.getMisses()==Player.LOST_MISSES)
                    player.setRes(Player.status.LOSE);

            }
            return false;
    }

    private void initThreads(){

        timers=new Timer[THREADS_NAME.length];
        TimerTask [] tasks=new TimerTask[THREADS_NAME.length];
        for (int i=0;i<timers.length;i++) {
            timers[i] = new Timer( THREADS_NAME[i] );
            tasks[i]=getTask( THREADS_NAME[i]);
        }
        double hopeMoleFreq = ((double) (Player.PLAYER_INIT_TIME) / ImageViewAdapter.HOPING_MOLES) * 1000;//per sec*1000=perMilli sec


        timers[0].scheduleAtFixedRate( tasks[0],1,1  );
        timers[1].scheduleAtFixedRate( tasks[1],(long)hopeMoleFreq,(long) hopeMoleFreq );
        timers[2].scheduleAtFixedRate( tasks[2],10,10 );//10 millisecs=1 hundred of a sec to update UI
    }


    private void terminateAllThreads(){
        for (int i = 0; i < timers.length ; i++) {
            timers[i].cancel();
        }
        Intent endGame=new Intent( GameActivity.this,ScoreActivity.class);

        endGame.putExtra("playerName",player.getName());
        endGame.putExtra("playerStatus",player.getRes().toString());
        startActivity(endGame);
    }





    private void initUI_components(){
        playerNameTextView=(TextView)findViewById(R.id.helloPlayerNameTextView);
        boardGridView=(GridView) findViewById(R.id.boardGridView);
        timeEditText =(EditText)findViewById(R.id.timeEditText);
        hitsEditText=(EditText)findViewById(R.id.hitsEditText);
        missesEditText=(EditText)findViewById(R.id.missesEditText);

        timeEditText.setText(Integer.toString(Player.PLAYER_INIT_TIME)+":00");
        hitsEditText.setText(Integer.toString(player.getHits()));
        missesEditText.setText(Integer.toString(player.getMisses()));
        playerNameTextView.setText("Hello"+" "+player.getName());

        imgAdapter=new ImageViewAdapter( this );
        boardGridView.setAdapter( imgAdapter );
    }


}
