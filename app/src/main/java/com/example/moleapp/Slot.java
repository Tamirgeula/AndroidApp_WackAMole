package com.example.moleapp;

public class Slot {


    private int pos;
    private boolean hit=false;


    public  Slot(int pos){
        this.pos=pos;

    }


    public boolean isHit() {
        return hit;
    }

    public void setHit(boolean hit) {
        this.hit = hit;
    }



    public int getPos() {
        return pos;
    }

}
