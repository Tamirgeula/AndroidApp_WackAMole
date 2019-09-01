package com.example.moleapp;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class ImageViewAdapter extends BaseAdapter {
    public static final int ROWS=3;
    public static final int COLS=3;
    public static final int HOPING_MOLES=Player.WIN_HITS+10;

    private static AnimationDrawable[] molesAnim;

    private Context c;



    private static ImageView [] board ;

    public ImageViewAdapter(Context c){
        this.c=c;
        this.board=new ImageView[ImageViewAdapter.COLS*ImageViewAdapter.ROWS];
        molesAnim=new AnimationDrawable[ROWS*COLS];
    }

    @Override
    public int getCount() {
        return board.length;
    }

    @Override
    public Object getItem(int pos) {
        return board[pos];
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup parent) {
        board[pos]=new ImageView(c);

        board[pos].setBackgroundResource(R.drawable.mole_dig_in_and_out_animation);
        molesAnim[pos]=(AnimationDrawable)board[pos].getBackground();
        board[pos].setScaleType(ImageView.ScaleType.CENTER_CROP);
        board[pos].setPadding(0,3,20,0);
        board[pos].setLayoutParams(new GridView.LayoutParams(parent.getWidth()/COLS,parent.getHeight()/ROWS));
        board[pos].setTag(new Slot(pos));
        board[pos].setOnClickListener((View v) -> {
            if (molesAnim[pos].isRunning()) {

                resetAnimation(pos);
                Slot cell=(Slot)(board[pos].getTag());
                cell.setHit(true);
                board[pos].setTag(cell);
            }


        });



        return board[pos];
    }

    public static void resetAnimation(int pos){

        molesAnim[pos].stop();

        board[pos].setBackground(null);

        //board[pos].setImageResource(R.drawable.boom);
        board[pos].setImageResource(0);
        //                board[pos].setBackgroundResource(R.drawable.boom_animation);
//                molesAnim[pos]=(AnimationDrawable)board[pos].getBackground();
//                molesAnim[pos].start();
//                while (molesAnim[pos].isRunning()){};

        board[pos].setBackgroundResource(R.drawable.mole_dig_in_and_out_animation);

        molesAnim[pos]=(AnimationDrawable) board[pos].getBackground();

    }

    public static AnimationDrawable[] getMolesAnim() {
        return molesAnim;
    }

    public static ImageView[] getBoard() {
        return board;
    }

}

